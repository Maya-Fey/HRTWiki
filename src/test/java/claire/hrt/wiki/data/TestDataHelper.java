/**
 * 
 */
package claire.hrt.wiki.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.jupiter.api.Test;

import claire.hrt.wiki.commons.Null;

/**
 * @author Claire
 */
public final class TestDataHelper {
	
	/**
	 * Tests that the hex converter is reversible
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testHexConversionReversability()
	{
		Random rand = new Random();
		byte[] bytes = new byte[256];
		rand.nextBytes(bytes);
		char[] hex = DataHelper.toHex(bytes);
		byte[] bytes2 = DataHelper.fromHex(hex);
		assertTrue(Arrays.equals(bytes, bytes2));
	}
	
	private static final String ALL_POSSIBLE = "000102030405060708090A0B0C0D0E0F101112131415161718191A1B1C1D1E1F202122232425262728292A2B2C2D2E2F303132333435363738393A3B3C3D3E3F404142434445464748494A4B4C4D4E4F505152535455565758595A5B5C5D5E5F606162636465666768696A6B6C6D6E6F707172737475767778797A7B7C7D7E7F808182838485868788898A8B8C8D8E8F909192939495969798999A9B9C9D9E9FA0A1A2A3A4A5A6A7A8A9AAABACADAEAFB0B1B2B3B4B5B6B7B8B9BABBBCBDBEBFC0C1C2C3C4C5C6C7C8C9CACBCCCDCECFD0D1D2D3D4D5D6D7D8D9DADBDCDDDEDFE0E1E2E3E4E5E6E7E8E9EAEBECEDEEEFF0F1F2F3F4F5F6F7F8F9FAFBFCFDFEFF";
	
	/**
	 * Tests every possible byte conversion
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testToHex()
	{
		byte[] bytes = new byte[256];
		for(int i = 0; i < bytes.length; i++)
			bytes[i] = (byte) i;
		char[] s = DataHelper.toHex(bytes);
		assertEquals(ALL_POSSIBLE, new String(s));
	}
	
	/**
	 * Tests every possible byte conversion
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testFromHex()
	{
		byte[] bytes = new byte[256];
		for(int i = 0; i < bytes.length; i++)
			bytes[i] = (byte) i;
		byte[] bytes2 = DataHelper.fromHex(Null.nonNull(ALL_POSSIBLE.toCharArray()));
		assertTrue(Arrays.equals(bytes, bytes2));
	}
	
	/**
	 * Tests every possible byte conversion in lowercase
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testFromHexLowercase()
	{
		byte[] bytes = new byte[256];
		for(int i = 0; i < bytes.length; i++)
			bytes[i] = (byte) i;
		byte[] bytes2 = DataHelper.fromHex(Null.nonNull(ALL_POSSIBLE.toLowerCase().toCharArray()));
		assertTrue(Arrays.equals(bytes, bytes2));
	}
	
	/**
	 * Test array combining
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testCombine()
	{
		String[] split = ALL_POSSIBLE.split("1");
		for(int i = 1; i < split.length; i++)
			split[i] = "1" + split[i];
		char[][] arrs = new char[split.length][];
		for(int i = 0; i < split.length; i++)
			arrs[i] = split[i].toCharArray();
		assertEquals(ALL_POSSIBLE, new String(DataHelper.addArrs(arrs)));
	}
	
	/**
	 * Test conversion of maps to strings and back
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testMapPersist()
	{
		Map<String, String> map = new HashMap<>();
		map.put("isGay", "true");
		map.put("containsComma", "true,");
		map.put("containsEscape", "true\\");
		map.put("containsEscapedComma", "true\\,");
		map.put("containsEquals", "=true");
		String stringrep = DataHelper.mapToString(map);
		Map<String, String> reconstituted = DataHelper.mapFromString(stringrep);
		assertEquals(map, reconstituted);
	}
	
	/**
	 * Test conversion of maps to strings and back on nested maps
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testNestedMapPersist()
	{
		Map<String, String> map = new HashMap<>();
		map.put("isGay", "true");
		map.put("containsComma", "true,");
		map.put("containsEscape", "true\\");
		map.put("containsEscapedComma", "true\\,");
		map.put("containsEquals", "=true");
		String stringrep = DataHelper.mapToString(map);
		Map<String, String> map2 = new HashMap<>();
		map2.put(stringrep, stringrep);
		map2.put("Hello", stringrep);
		String stringrep2 = DataHelper.mapToString(map2);
		Map<String, String> reconstituted = DataHelper.mapFromString(stringrep2);
		assertEquals(map, DataHelper.mapFromString(reconstituted.get("Hello")));
	}

}
