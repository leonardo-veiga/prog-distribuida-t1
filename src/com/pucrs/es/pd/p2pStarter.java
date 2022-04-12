package com.pucrs.es.pd;

import java.io.*;

public class p2pStarter {

	protected static final String SERVER = "SERVER";
	protected static final String PEER = "PEER";

	public static void main(String[] args) throws IOException {
		if (args.length != 4) {
			System.out.println("Uso: java p2pStarter <server> \"<message>\" <localport> <mode>");
			System.out.println("<message> is:");
			System.out.println("create nickname");
			System.out.println("list nickname");
			System.out.println("wait");
			return;
		} else {
			if (SERVER.equalsIgnoreCase(args[3])) {
				new p2pServer(args).start();
			} else if (PEER.equalsIgnoreCase(args[3])) {
				new p2pPeerThread(args).start();
				new p2pPeerHeartbeat(args).start();
				new p2pPeerClient(args).start();
			} else {
				System.out.println("Modo invalido!");
				System.out.println("Modos aceitos: SERVER ou PEER");
			}
		}
	}
}
