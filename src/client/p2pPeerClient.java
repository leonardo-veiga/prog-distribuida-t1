package client;

import fileApi.FileStruct;
import fileApi.MapFiles;
import rmi.Peer;
import rmi.ResourceInterface;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class p2pPeerClient extends Thread {

	protected String host, server_host;
	protected int port, server_port;
	protected ResourceInterface resource;
	protected List<FileStruct> files;
//	protected static MapFiles mapFiles;
//	protected static Sender fileSender = new Sender();

//	public static void main(String[] args) {
//		String[] args2 = { "localhost", "9876", "SERVER" };
//		ArrayList<FileStruct> files = mapFiles.searchFiles("enun");
//		mapFiles.print(files);
//		System.out.println("fim");
//		fileSender.sendFile(files.get(0));
//	}

	public p2pPeerClient(String[] args) throws IOException {
		this.host = args[0];
		this.port = Integer.parseInt(args[1]);

		this.server_host = args[2];
		this.server_port = Integer.parseInt(args[3]);

		MapFiles mapFiles = new MapFiles();
		this.files = mapFiles.searchFiles(args[4]);

		try {
			resource = (ResourceInterface) Naming.lookup("rmi://" + server_host + ":" + server_port + "/Resource");
		} catch (NotBoundException e) {
			System.err.println ("Resource failed.");
			e.printStackTrace();
		}
	}

	public void run() {
		this.acceptedCommands("ALIVE");

		long lastRun = System.nanoTime();
		// Looping de controle de recursos
		while(true) {
			if(this.cycleCheck(lastRun)) {
//				System.out.println("passou 10 segundos!");
				lastRun = System.nanoTime();
				this.acceptedCommands("ALIVE");
			}

			String userInput = this.getUserInput();



		}
	}

	private boolean cycleCheck(long lastRun) {
		long now = System.nanoTime();
		long elapsedTime = now - lastRun;
		long convertedTime = TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);

		return convertedTime > 10;
	}

	private String getUserInput() {
		Scanner scanner = new Scanner(System.in);  // Create a Scanner object
		System.out.print("Entre um comando: ");
		String command = scanner.nextLine();  // Read user input

		return command;
	}

	private String listPeers(List<Peer> peers) {
		String response = "";
		for (Peer p : peers) {
			response += p.toString() + "; ";
		}
		return response;
	}

	private void acceptedCommands(String input) {
		String[] commands = input.split(" ");
		try {
			switch (commands[0].toUpperCase()) {
				case "REGISTER":
					String response = resource.registerPeer(this.host,this.port, this.files);
					System.out.println("Registro: " + response);
					break;
				case "ALIVE":
					String keepAlive = resource.keepAlive(this.host, this.port);
					System.out.println(keepAlive);
					break;
				case "LIST":
					List<Peer> peers = resource.listPeers();
					System.out.println(this.listPeers(peers));
					break;
				case "FIND":
					Peer peer = resource.findPeerByFileName(commands[1]);
					System.out.println("Peer com o arquivo solicitado: " + peer);
					break;
				case "SEND":
					break;
				default:
					System.out.println("Não faz nada!");
					break;
			}
		} catch (Exception e) {
			System.err.println("Erro na comunicação com o servidor!");
			e.printStackTrace();
		}
	}
}
