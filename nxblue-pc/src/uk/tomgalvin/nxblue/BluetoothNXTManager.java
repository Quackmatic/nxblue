package uk.tomgalvin.nxblue;

import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

import java.io.IOException;
import java.util.ArrayList;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;

/**
 * Allows you to find and initiate connections to NXTs from the computer.
 * 
 * @author Tom Galvin
 */
public class BluetoothNXTManager {
	/**
	 * Create a new bluetooth NXT manager.
	 */
	public BluetoothNXTManager() {
	}
	
	/**
	 * Creates an {@link NXTComm} object for use with Bluetooth communication.
	 * 
	 * @return A new NXTComm object, set to use Bluetooth as the protocol.
	 */
	private NXTComm createNXTComm() throws CommException {
		try {
            return NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
		} catch(NXTCommException e) {
			throw new CommException(e.getMessage(), e);
		}
	}

	/**
	 * Gets the {@link NXTInfo} corresponding to an NXT with a known name and
	 * MAC address.
	 * 
	 * @param name The name of the NXT (eg. {@code "Ultron"}).
	 * @param mac The MAC address of the NXT (eg. {@code "00:16:53:1B:59:4D"} or
	 * {@code "0016531b594d"}).
	 * @return The {@link NXTInfo} corresponding to the NXT with the given
	 *         name and MAC address.
	 */
	public NXTInfo getNXT(String name, String mac) {
		return new NXTInfo(NXTCommFactory.BLUETOOTH, name, mac.replace(":", "").toUpperCase().trim());
	}

	/**
	 * Get all the NXTs in the area whose name contain the string {@code name}.
	 * <br/>
	 * Note that the implementation of the bluetooth searching provided by the
	 * LeJOS API (and I assume, in turn, by BlueCove) is very flaky. Often,
	 * this method will take a long time to complete a search, and may not even
	 * return a NXT in the vicinity (which can be connected to if the MAC
	 * address and NXT name are hard-coded). Additionally, the NXT and the
	 * connecting PC must have been bluetooth-paired in advance beforehand,
	 * eliminating many of the potential benefits of scanning for nearby NXT
	 * bricks. Hence, until that is fixed, it is recommended that the name and
	 * MAC address of the desired NXT are determined beforehand and inserted
	 * into your application manually.
	 * 
	 * @param name A filter with which to search for NXTs.
	 * @return A list of all of the NXTs in the local vicinity.
	 * @throws CommException
	 *             When something goes wrong with the NXT connection.
	 */
	public NXTInfo[] getAllNXTs(String name) throws CommException {
		try {
			NXTComm comm = createNXTComm();
			NXTInfo[] nxts = comm.search(null);

			ArrayList<NXTInfo> names = new ArrayList<NXTInfo>();
			for (int i = 0; i < nxts.length; i++) {
				if (nxts[i].name.contains(name)) {
					names.add(nxts[i]);
				}
			}
			comm.close();
			return names.toArray(new NXTInfo[0]);
		} catch (NXTCommException e) {
			throw new CommException(e);
		} catch(IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * Get all the NXTs in the area.
	 * 
	 * @return A list of all of the NXTs in the local vicinity.
	 * @throws CommException
	 *             When something goes wrong with the NXT connection.
	 */
	public NXTInfo[] getAllNXTs() throws CommException {
		return getAllNXTs("");
	}

	/**
	 * Create a connection to the NXT with the given name.
	 * 
	 * @param info
	 *            The identity of the NXT to connect to.
	 * @return A socket with which to communicate with the NXT.
	 * @throws CommException
	 *             When something goes wrong during initiation of the
	 *             connection.
	 */
	public NXTSocket createConnection(NXTInfo info) {
		NXTComm comm = createNXTComm();
		BluetoothNXTSocket socket = new BluetoothNXTSocket(info, comm);
		return socket;
	}
}
