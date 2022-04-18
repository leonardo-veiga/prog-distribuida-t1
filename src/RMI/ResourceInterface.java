package RMI;

import FileApi.FileStruct;
import com.pucrs.es.pd.Peer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ResourceInterface extends Remote {
	String registerPeer(String host, int port, List<FileStruct> files) throws RemoteException;
	String keepAline(String host, int port) throws RemoteException;
	List<Peer> listPeers() throws RemoteException;
}

