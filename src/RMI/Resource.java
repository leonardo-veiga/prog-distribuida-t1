package RMI;

import FileApi.FileStruct;
import com.pucrs.es.pd.Peer;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Resource extends UnicastRemoteObject implements ResourceInterface {

	private List<Peer> peers;

	public Resource(List<Peer> peers) throws RemoteException {
		this.peers = peers;
	}

	public String registerPeer(String host, int port, List<FileStruct> files) throws RemoteException {
		Peer peer = new Peer(host, port, files);
		this.peers.add(peer);

		return "Peer Added!";
	}

	public List<Peer> listPeers() throws RemoteException {
		return this.peers;
	}

	public String keepAline(String host, int port) throws RemoteException {
		Peer peer = findPeer(host, port);
		if (peer != null) {
			peer.setLastPing(System.nanoTime());
			return "Peer kept alive!";
		} else {
			return "Peer não encontrado!";
		}
	}

	private Peer findPeer(String host, int port) {
		for (Peer p : this.peers) {
			if(host.equalsIgnoreCase(p.getHost()) && port == p.getPort()) {
				return p;
			}
		}
		return null;
	}

//	public int testResource(String testValue) throws RemoteException {
//		try {
//			System.out.println(RemoteServer.getClientHost() + "Chamou o backend!");
//		} catch (ServerNotActiveException e) {
//			e.printStackTrace();
//		}
//		return testValue.hashCode();
//	}

}

