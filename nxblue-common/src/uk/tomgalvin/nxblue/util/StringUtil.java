package uk.tomgalvin.nxblue.util;

import java.util.ArrayList;

/**
 * Utility class for nonexistent string manipulation libraries in the
 * LeJOS JVM implementation. These are only re-implemented here, because
 * they are not present in the {@link String} class in LeJOS, and we cannot
 * use extension methods in Java as you can in C#.
 * 
 * @author Tom Galvin
 */
public abstract class StringUtil {
	/**
	 * Efficient implementation of {@link String#startsWith} for JRE
	 * implementations that don't support it. Checks whether {@code input}
	 * ends in the string {@code starter}.
	 * 
	 * @param input The input string to check.
	 * @param starter The starting sequence to check.
	 * @return Whether {@code input} starts with {@code starter}.
	 */
	public static boolean startsWith(String input, String starter) {
		int inputLength = input.length(),
		    starterLength = starter.length();
		
		if(starterLength > inputLength) {
			return false;
		} else {
			for(int i = 0; i < starterLength; i++) {
				if(input.charAt(i) != starter.charAt(i)) {
					return false;
				}
			}
			
			return true;
		}
	}
	
	/**
	 * Efficient implementation of {@link String#endsWith} for JRE
	 * implementations that don't support it. Checks whether {@code input}
	 * ends in the string {@code ending}.
	 * 
	 * @param input The input string to check.
	 * @param ending The ending sequence to check.
	 * @return Whether {@code input} ends in {@code ending}.
	 */
	public static boolean endsWith(String input, String ending) {
		int inputLength = input.length(),
		    endingLength = ending.length();
		
		if(endingLength > inputLength) {
			return false;
		} else {
			for(int i = 0; i < endingLength; i++) {
				if(input.charAt(inputLength - endingLength + i) != ending.charAt(i)) {
					return false;
				}
			}
			
			return true;
		}
	}
	
	/**
	 * Implementation of a non regex-based string splitting utility. Returns
	 * an array of each substring within {@code input} that is delimited
	 * (separated) by the string {@code delimiter}, optionally removing empty
	 * substrings. For example,
	 * <pre><code>
	 * split("1,2,,3,4,5,", ",", true) = ["1", "2", "3", "4", "5"]
	 * split("1,2,,3,4,5,", ",", false) = ["1", "2", "", "3", "4", "5", ""]
	 * </code></pre>
	 * 
	 * @param input The input string to split.
	 * @param delimiter The splitting delimiter.
	 * @param removeEmpty Whether to remove empty substrings from the result.
	 * @return An array of substrings separated by the given delimiter.
	 */
	public static String[] split(String input, String delimiter, boolean removeEmpty) {
		if(input.length() > 0) {
			ArrayList<String> parts = new ArrayList<String>();
			int beginIndex = 0, endIndex;
			while((endIndex = input.indexOf(delimiter, beginIndex)) != -1) {
				parts.add(input.substring(beginIndex, endIndex));
				beginIndex = endIndex + delimiter.length();
			}
			
			parts.add(input.substring(beginIndex));
			
			String[] partsArray = new String[parts.size()];
			for(int i = 0; i < partsArray.length; i++) {
				partsArray[i] = parts.get(i);
			}
			return parts.toArray(partsArray);
		} else {
			return new String[0];
		}
	}
}
