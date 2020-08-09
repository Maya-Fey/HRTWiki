/**
 * 
 */
package claire.hrt.wiki.data;

import claire.hrt.wiki.commons.except.PreconditionViolationException;

/**
 * Helper class for converting between raw datatypes
 * 
 * @author Claire
 */
public final class DataHelper {
	
	public static final char[] HEX = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
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

}
