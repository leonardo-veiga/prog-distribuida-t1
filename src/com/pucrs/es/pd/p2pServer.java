package com.pucrs.es.pd;

import java.io.*;
import java.net.*;
import java.util.*;

public class p2pServer extends Thread {

	protected DatagramSocket socket = null;
	protected DatagramPacket packet = null;
	protected InetAddress addr = null;
	protected byte[] resource = new byte[1024];
	protected byte[] response = new byte[1024];
	protected int port;

	public p2pServer(String[] args) throws IOException {
		port = Integer.parseInt(args[2]) + 1;
		// cria um socket datagrama
		socket = new DatagramSocket(port);
	}

	public void run() {
		String content = null;
//		InetAddress addr;
//		int port;
//		byte[] resource = new byte[1024];
//		byte[] response = new byte[1024];
//		DatagramSocket socket = new DatagramSocket(9000);
//		DatagramPacket packet;
		
		List<String> resourceList = new ArrayList<>();
		List<InetAddress> resourceAddr = new ArrayList<>();
		List<Integer> resourcePort = new ArrayList<>();
		List<Integer> timeoutVal = new ArrayList<>();
		
		while (true) {
			try {
				// recebe datagrama
				packet = new DatagramPacket(resource, resource.length);
				socket.setSoTimeout(500);
				socket.receive(packet);
				System.out.print("Recebi!");
								
				// processa o que foi recebido, adicionando a uma lista
				content = new String(packet.getData(), 0, packet.getLength());
				addr = packet.getAddress();
				port = packet.getPort();
				String vars[] = content.split("\\s");
				
				if (vars[0].equals("create") && vars.length > 1) {
					int j;
					
					for (j = 0; j < resourceList.size(); j++) {
						if (resourceList.get(j).equals(vars[1]))
							break;
					}
					
					if (j == resourceList.size()) {
						resourceList.add(vars[1]);
						resourceAddr.add(addr);
						resourcePort.add(port);
						timeoutVal.add(15);		/* 500ms * 15 = 7.5s (enough for 5s heartbeat) */
						
						response = "OK".getBytes();
					} else {
						response = "NOT OK".getBytes();
					}
					
					packet = new DatagramPacket(response, response.length, addr, port);
					socket.send(packet);
				}
				
				if (vars[0].equals("list") && vars.length > 1) {
					for (int j = 0; j < resourceList.size(); j++) {
						if (resourceList.get(j).equals(vars[1])) {
							for (int i = 0; i < resourceList.size(); i++) {
								String data = new String(resourceList.get(i) + " " + resourceAddr.get(i).toString() + " " + resourcePort.get(i).toString());
								response = data.getBytes();
								
								packet = new DatagramPacket(response, response.length, addr, port);
								socket.send(packet);
							}
							break;
						}
					}
				}
				
				if (vars[0].equals("heartbeat") && vars.length > 1) {
					System.out.print("\nheartbeat: " + vars[1]);
					for (int i = 0; i < resourceList.size(); i++) {
						if (resourceList.get(i).equals(vars[1]))
							timeoutVal.set(i, 15);
					}
				}
			} catch (IOException e) {
				// decrementa os contadores de timeout a cada 500ms (em função do receive com timeout)
				for (int i = 0; i < timeoutVal.size(); i++) {
					timeoutVal.set(i, timeoutVal.get(i) - 1);
					if (timeoutVal.get(i) == 0) {
						System.out.println("\nuser " + resourceList.get(i) + " is dead.");
						resourceList.remove(i);
						resourceAddr.remove(i);
						resourcePort.remove(i);
						timeoutVal.remove(i);
					}
				}
				System.out.print(".");
			}
		}
	}
}
