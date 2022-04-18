package rmi;

import fileApi.FileStruct;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

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

	public String keepAlive(String host, int port) throws RemoteException {
		Peer peer = findPeer(host, port);
		if (peer != null) {
			peer.setLastPing(System.nanoTime());
			return "Peer kept alive!";
		} else {
			return "Peer não encontrado!";
		}
	}

	public Peer findPeerByFileName(String fileName) throws RemoteException {
		for (Peer p : this.peers) {
			for (FileStruct fs : p.getFiles()) {
				if(fileName.toUpperCase().contains(fs.getName().toUpperCase())) {
					return p;
				}
			}
		}
		return null;
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

