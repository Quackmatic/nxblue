package uk.tomgalvin.nxblue;


/**
 * An interface representing an NXT robot being communicated with across a
 * network.
 * 
 * @author Tom Galvin
 */
public interface RobotSocket extends Socket {
	/**
	 * Get the name of the remote robot.
	 * 
	 * @return The name of the remote robot.
	 */
	public String getRobotName();
	
	/**
	 * Get the ID of the remote robot. This comes in the form of a 12-digit
	 * hexadecimal identifier, such as {@code 0016530A971B}, and is roughly
	 * equivalent to the MAC address of the robot.
	 * 
	 * @return The ID of the remote robot.
	 */
	public String getRobotID();
}
