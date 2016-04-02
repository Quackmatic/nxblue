package uk.tomgalvin.nxblue;


/**
 * Represents an object that can receive events from a socket whenever
 * data is received across some form of network.
 * 
 * @author Tom Galvin
 */
public interface SocketListener {
	/**
	 * Invoked when a socket is connected.
	 * 
	 * @param socket The socket which connected.
	 */
	public void onConnect(Socket socket);
	
	/**
	 * Invoked when a command is received across the network. The received data
	 * is transferred as a {@link Command} object.
	 * 
	 * @param socket The socket from which the data was received.
	 * @param data The command received across the network.
	 */
	public void onCommandReceived(Socket socket, Command command);
	
	/**
	 * Invoked when a socket is disconnected.
	 * 
	 * @param socket The socket which disconnected.
	 */
	public void onDisconnect(Socket socket);
}
