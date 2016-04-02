package uk.tomgalvin.nxblue;

import lejos.nxt.comm.Bluetooth;
import uk.tomgalvin.nxblue.Socket;

/**
 * Allows you to wait for connections on the robot side.
 * 
 * @author Tom Galvin
 */
public class BluetoothManager {
	/**
	 * Create a new bluetooth manager.
	 */
	public BluetoothManager() {
		
	}
	
	/**
	 * Waits for a connection, and returns a (not-yet-opened) socket that
	 * manages that connection. Note that you must open the socket yourself
	 * after calling this method; for example:<br/>
	 * 
	 * <code>
	 * Socket socket = manager.waitForConnection();<br/>
	 * socket.addListener(this);<br/>
	 * socket.open();
	 * </code>
	 * 
	 * @return The socket managing a new connection from a computer via
	 * Bluetooth.
	 */
	public Socket waitForConnection() {
		return new BluetoothSocket(Bluetooth.waitForConnection());
	}
}
