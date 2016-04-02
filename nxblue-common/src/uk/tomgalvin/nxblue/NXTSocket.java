package uk.tomgalvin.nxblue;


/**
 * An interface representing an NXT NXT being communicated with across a
 * network.
 * 
 * @author Tom Galvin
 */
public interface NXTSocket extends Socket {
	/**
	 * Get the name of the remote NXT.
	 * 
	 * @return The name of the remote NXT.
	 */
	public String getName();
	
	/**
	 * Get the ID of the remote NXT. This comes in the form of a 12-digit
	 * hexadecimal identifier, such as {@code 0016530A971B}, and is roughly
	 * equivalent to the MAC address of the NXT.
	 * 
	 * @return The ID of the remote NXT.
	 */
	public String getMacAddress();
}
