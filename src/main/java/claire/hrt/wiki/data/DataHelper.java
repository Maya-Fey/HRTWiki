/**
 * 
 */
package claire.hrt.wiki.data;

import java.util.HashMap;
import java.util.Map;

import claire.hrt.wiki.commons.Null;
import claire.hrt.wiki.commons.except.PreconditionViolationException;

/**
 * Helper class for converting between raw datatypes
 * 
 * @author Claire
 */
public final class DataHelper {
	
	private static final char[] HEX = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	
	/**
	 * Converts a series of bytes into hexademical representation 
	 * @param bytes The byte array to convert
	 * @return An array of chars, each representing one nibble/hexadecimal character. Hex
	 * will be in upper case [0-9A-F]
	 */
	public static final char[] toHex(byte[] bytes)
	{
		char[] chars = new char[bytes.length * 2];
		for(int i = 0; i < bytes.length; i++) {
			chars[i * 2 + 0] = HEX[(bytes[i] & 0xF0) >>> 4]; 
			chars[i * 2 + 1] = HEX[(bytes[i] & 0x0F)]; 
		}
		return chars;
	}
	
	/**
	 * @param c A hexadecimal character of any case, in range [0-9A-Fa-f]
	 * @return A nibble 
	 */
	private static final byte charToNibble(char c)
	{
		if(c >= '0' && c <= '9') 
			return (byte) (c - '0');
		else if(c >= 'A' && c <= 'F')
			return (byte) (10 + (c - 'A'));
		else if(c >= 'a' && c <= 'f')
			return (byte) (10 + (c - 'a'));
		else
			throw new PreconditionViolationException("Hexadecimal characters are between 0-9, A-F, or a-f");
	}
	
	/**
	 * Converts a hexadecimal string into bytes
	 * @param str The hexadecimal string as an array of characters, in any case
	 * @return An array of bytes
	 */
	public static final byte[] fromHex(char[] str)
	{
		if((str.length & 1) == 1) 
			throw new PreconditionViolationException("A hex string must have an even number of characters. Consier adding a leading zero.");
		byte[] nBytes = new byte[str.length / 2];
		for(int i = 0; i < nBytes.length; i++) {
			byte b = charToNibble(str[i * 2]);
			b <<= 4;
			b += charToNibble(str[i * 2 + 1]);
			nBytes[i] = b;
		}
		return nBytes;
	}
	
	/**
	 * Adds an array of character arrays into one array, in order, with all
	 * elements copied
	 * 
	 * @param arrs The array of arrays
	 * @return One array containing the union of all the arrays, with the elements
	 * in the order of the sub-arrays in the order that the sub-arrays were passed
	 */
	public static final char[] addArrs(char[] ... arrs) {
		int tl = 0;
		for(char[] arr : arrs)
			tl += arr.length;
		char[] narr = new char[tl];
		int i = 0;
		for(int j = 0; j < arrs.length; j++) {
			System.arraycopy(arrs[j], 0, narr, i, arrs[j].length);
			i += arrs[j].length;
		}
		return narr;
	}
	
	/**
	 * @param string The string to escape
	 * @return A string with all the \ and , characters escaped
	 */
	public static String escapeForMap(String string)
	{
		StringBuilder builder = new StringBuilder("");
		for(int i = 0; i < string.length(); i++)
		{
			if(string.charAt(i) == ',' || string.charAt(i) == '\\' || string.charAt(i) == '=')
				builder.append('\\');
			builder.append(string.charAt(i));
		}
		return Null.nonNull(builder.toString());
	}
	
	/**
	 * @param string The string to unescape
	 * @return A string with all the escape slashes removed
	 */
	public static String unescapeForMap(String string)
	{
		StringBuilder builder = new StringBuilder("");
		boolean escaped = false;
		for(int i = 0; i < string.length(); i++)
		{
			if(escaped) {
				builder.append(string.charAt(i));
				escaped = false;
			} else if(string.charAt(i) == '\\') {
				escaped = true;
			} else {
				builder.append(string.charAt(i));
			}
		}
		return Null.nonNull(builder.toString());
	}
	
	/**
	 * @param map A map from String => String
	 * @return A string representation of the map
	 */
	public static String mapToString(Map<String, String> map)
	{
		StringBuilder builder = new StringBuilder("{ ");
		map.entrySet().forEach((e) -> {
			builder.append('"');
			builder.append(escapeForMap(e.getKey()));
			builder.append("\" => \"");
			builder.append(escapeForMap(e.getValue()));
			builder.append("\", ");
		});
		builder.append(" }");
		return Null.nonNull(builder.toString());
	}
	
	/**
	 * @param string A string representing a map
	 * @return A map that the string represents
	 */
	public static Map<String, String> mapFromString(String string)
	{
		Map<String, String> map = new HashMap<>();
		String[] components = string.split("(?<=([^\\\\]))(\\\\\\\\)*,");
		for(String component : components) {
			String[] pair = component.split(" => ");
			if(pair.length != 2)
				continue;
			pair[0] = pair[0].substring(pair[0].indexOf('"') + 1);
			pair[1] = pair[1].substring(pair[1].indexOf('"') + 1);
			pair[0] = pair[0].substring(0, pair[0].lastIndexOf('"'));
			pair[1] = pair[1].substring(0, pair[1].lastIndexOf('"'));
			map.put(unescapeForMap(Null.nonNull(pair[0])), unescapeForMap(Null.nonNull(pair[1])));
		}
		return map;
	}

}
