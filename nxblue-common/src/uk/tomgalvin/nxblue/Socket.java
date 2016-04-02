package uk.tomgalvin.nxblue;

/**
 * An interface representing a remote object over a network, to which data can
 * be sent and from which data can be received.
 * 
 * @author Tom Galvin
 */
public interface Socket {
	/**
	 * Open the connection to the remote end-point, if applicable.
	 */
	public void open() throws CommException;
	
	/**
	 * Determines if the current socket is open (ie. available to send and
	 * receive data).
	 * 
	 * @return Whether the socket is open or not.
	 */
	public boolean isOpen();
	
	/**
	 * Add a listener object that should receive events whenever data is
	 * received from the remote end point.
	 * 
	 * @param listener The listener that is to receive events.
	 */
	public void addListener(SocketListener listener);
	
	/**
	 * Remove a listener object that is currently listening for data receival
	 * events on this socket.
	 * 
	 * @param listener The listener that is to no longer receive events.
	 */
	public void removeListener(SocketListener listener);
	
	/**
	 * Send a command to the remote end-point.
	 * 
	 * @param command The command to send to the remote end point.
	 */
	public void sendCommand(Command command);
	
	/**
	 * Closes the connection to the remote end point.
	 */
	public void close();
}
