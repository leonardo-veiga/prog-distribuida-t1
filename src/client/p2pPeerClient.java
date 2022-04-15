package client;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

import FileApi.MapFiles;
import RMI.ResourceInterface;

public class p2pPeerClient extends Thread {

	protected int server_port;
	protected String host;
	protected ResourceInterface testResource;
	protected static MapFiles mapFiles = new MapFiles();

	public static void main(String[] args) {
		String[] args2 = { "localhost", "9876", "SERVER" };
		mapFiles.print();
		System.out.println("fim");
	}


	public p2pPeerClient(String[] args) throws IOException {
		server_port = Integer.parseInt(args[2]);
		host = args[0];

		try {
			testResource = (ResourceInterface) Naming.lookup("rmi://" + host + ":" + server_port + "/Resource");
		} catch (NotBoundException e) {
			System.err.println ("Resource failed.");
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			ResourceInterface testResource = (ResourceInterface) Naming.lookup("rmi://" + host + ":" + server_port + "/Resource");
			int response = testResource.testResource("Testing!");
			System.out.println("Resposta: " + response);
		} catch (Exception e) {
			System.out.println ("NotasClient failed.");
			e.printStackTrace();
		}
	}
}
