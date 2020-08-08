/**
 * 
 */
package claire.hrt.wiki.data;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import claire.hrt.wiki.data.virtual.DataContext;
import claire.hrt.wiki.data.virtual.UserTable;

/**
 * @author Claire
 */
public class UserTableImpl implements UserTable {
	
	private static final int ITERATION_COUNT = 10000;
	private static final int SALT_SIZE = 8;
	
	private final SecureRandom random;
	
	private final Map<String, byte[]> salts = new HashMap<>();
	private final Map<String, String> passwords = new HashMap<>();
	
	
	private final SecretKeyFactory factory;
	
	public UserTableImpl()
	{
		try {
			this.factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			this.random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			throw new Error(e);
		}
	}

	@Override
	public void write(String username, String password) 
	{
		if(passwords.containsKey(username)) {
			//DuplicateKeyException
		}
		char[] prekey = (username + password).toCharArray();
		byte[] salt = this.generateSalt();
		SecretKey key;
		try {
			key = this.factory.generateSecret(new PBEKeySpec(prekey, salt, ITERATION_COUNT));
		} catch (InvalidKeySpecException e) { throw new Error(e); }
		byte[] str = key.getEncoded();
		salts.put(username, salt);
		passwords.put(username, toHex(str));
	}

	@Override
	public boolean read(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private byte[] generateSalt()
	{
		byte[] salt = new byte[SALT_SIZE];
		random.nextBytes(salt);
		return salt;
	}
	
	@Override
	public void flush(DataContext context) {
		// TODO Auto-generated method stub

	}

}
