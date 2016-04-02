package uk.tomgalvin.nxblue;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lejos.nxt.comm.BTConnection;
import uk.tomgalvin.nxblue.CommException;
import uk.tomgalvin.nxblue.Command;
import uk.tomgalvin.nxblue.Socket;
import uk.tomgalvin.nxblue.SocketListener;

/**
 * A bluetooth implementation of a socket to facilitate computer-robot
 * communications, on the robot side.
 * 
 * @author Tom Galvin
 */
public class BluetoothSocket implements Socket {
	private ArrayList<SocketListener> listeners;
	private BTConnection connection;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private Thread receiveThread;
	private volatile boolean open;
	
	/**
	 * Creates a new BluetoothSocket.
	 * 
	 * @param connection The connection object containing the input and
	 * output streams to send and receive data to and from the robot.
	 */
	public BluetoothSocket(BTConnection connection) {
		this.connection = connection;
		this.listeners = new ArrayList<SocketListener>();
	}
	
	/**
	 * Opens the connection to the remote NXT brick.
	 * @throws NXTCommException When someth
	 */
	public void open() throws CommException {
		this.inputStream = new DataInputStream(this.connection.openDataInputStream());
		this.outputStream = new DataOutputStream(this.connection.openDataOutputStream());

		open = true;
		receiveThread = new Thread(new ReceiveThreadBody());
		receiveThread.start();
	}
	
	@Override
	public boolean isOpen() {
		return open;
	}
	
	@Override
	public void close() {
		if(this.open) {
			this.open = false;
			if(this.receiveThread.isAlive()) {
				this.receiveThread.interrupt();
			}
			
			try {
				this.connection.close();
				this.connection.closeStream();
			} catch(Exception e) {
				// stream already closed so don't bother
			}
			
			onDisconnect();
		}
	}
	
	@Override
	public void addListener(SocketListener listener) {
		synchronized(listeners) {
			if(!listeners.contains(listener)) {
				listeners.add(listener);
			} else {
				throw new IllegalArgumentException(
						"The given listener is already listening to this socket.");
			}
		}
	}

	@Override
	public void removeListener(SocketListener listener) {
		if(listeners.contains(listener)) {
			synchronized (listeners) {
				listeners.remove(listener);
			}
		} else {
			throw new IllegalArgumentException(
					"The given listener is not listening to this socket."
					);
		}
	}
	

	
	/**
	 * Invoked internally when the socket connects.
	 */
	protected void onConnect() {
		synchronized (listeners) {
			for(SocketListener listener : listeners) {
				listener.onConnect(this);
			}
		}
	}
	
	/**
	 * Invoked internally when a command is received from the remote end
	 * point.
	 * 
	 * @param command The command that was received.
	 */
	protected void onCommandReceived(Command command) {
		synchronized (listeners) {
			for(SocketListener listener : listeners) {
				listener.onCommandReceived(this, command);
			}	
		}
	}
	
	/**
	 * Invoked internally when the socket disconnects.
	 */
	protected void onDisconnect() {
		synchronized (listeners) {
			for(SocketListener listener : listeners) {
				listener.onDisconnect(this);
			}
		}
	}

	@Override
	public void sendCommand(Command command) {
		if(isOpen()) {
			try {
				String commandString = command.toString();
				outputStream.writeUTF(commandString);
				outputStream.flush();
			} catch(IOException e) {
				close();
			}
		} else {
			throw new CommException("Socket not open.");
		}
	}
	
	/**
	 * Controls the thread for asynchronously receiving data.
	 * 
	 * @author Tom Galvin
	 */
	private class ReceiveThreadBody implements Runnable {
		@Override
		public void run() {
			onConnect();
			
			try {
				while(open) {
					String commandString = inputStream.readUTF();
					Command command = Command.fromString(commandString);
					onCommandReceived(command);
				}
			} catch(IOException e) {
				/*
				 * IOException means the socket has been closed, so just
				 * gracefully exit the thread.
				 */
			} finally {
				close();
			}
		}
	}
}
