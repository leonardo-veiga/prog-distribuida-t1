package client;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import FileApi.FileStruct;
import FileApi.MapFiles;
import RMI.ResourceInterface;
import com.pucrs.es.pd.Peer;

public class p2pPeerClient extends Thread {

	protected int server_port, port;
	protected String server_host, host;
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
		this.server_port = Integer.parseInt(args[2]);
		this.server_host = args[0];

		this.port = 123;
		this.host = "test";

		MapFiles mapFiles = new MapFiles();
		this.files = mapFiles.searchFiles("enun");

		try {
			resource = (ResourceInterface) Naming.lookup("rmi://" + server_host + ":" + server_port + "/Resource");
		} catch (NotBoundException e) {
			System.err.println ("Resource failed.");
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			String response = resource.registerPeer(this.host,this.port, this.files);
			System.out.println("Resposta: " + response);
			System.out.println(resource.listPeers());
		} catch (Exception e) {
			System.out.println ("Resource failed.");
			e.printStackTrace();
		}

		long lastRun = System.nanoTime();
		// Looping de controle de recursos
		while(true) {
			if(this.cycleCheck(lastRun)) {
				System.out.println("passou 10 segundos!");
				lastRun = System.nanoTime();
				try {
					String keepAlive = resource.keepAline(this.host, this.port);
					System.out.println(keepAlive);
				} catch (Exception e) {
					System.err.println("Erro na comunicação com o servidor!");
					e.printStackTrace();
				}
			}

		}
	}

	private boolean cycleCheck(long lastRun) {
		long now = System.nanoTime();
		long elapsedTime = now - lastRun;
		long convertedTime = TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);

		return convertedTime > 10;
	}
}
