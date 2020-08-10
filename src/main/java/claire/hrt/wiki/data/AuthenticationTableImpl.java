/**
 * 
 */
package claire.hrt.wiki.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import claire.hrt.wiki.commons.Null;
import claire.hrt.wiki.data.except.DuplicateKeyException;
import claire.hrt.wiki.data.except.NoSuchKeyException;
import claire.hrt.wiki.data.virtual.AuthenticationTable;
import claire.hrt.wiki.data.virtual.DataContext;

/**
 * @author Claire
 */
public class AuthenticationTableImpl implements AuthenticationTable {
	
	private static final int ITERATION_COUNT = 10000;
	private static final int SALT_SIZE = 8;
	
	private final SecureRandom random;
	private final SecretKeyFactory factory;
	
	private final Map<String, byte[]> salts = new HashMap<>();
	private final Map<String, char[]> passwords = new HashMap<>();
	
	private boolean initialized = false;
	
	/**
	 * Instantiates with the relevant crypto objects
	 */
	public AuthenticationTableImpl()
	{
		try {
			this.factory = Null.nonNull(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1"));
			this.random = Null.nonNull(SecureRandom.getInstance("SHA1PRNG"));
		} catch (NoSuchAlgorithmException e) {
			throw new Error(e);
		}
	}
	
	@Override
	public boolean exists(String username) {
		return this.salts.containsKey(username);
	}

	@Override
	public void write(String username, char[] password) throws DuplicateKeyException 
	{
		if(this.passwords.containsKey(username)) {
			throw new DuplicateKeyException();
		}
		
		unprotectedWrite(username, password);
	}
	
	@Override
	public void overwrite(String username, char[] password) throws NoSuchKeyException {
		if(!this.passwords.containsKey(username)) {
			throw new NoSuchKeyException(username + " is not a valid user in the authentication table");
		}
		
		unprotectedWrite(username, password);
	}

	@Override
	public LoginReturn read(String username, char[] password) {
		if(!this.passwords.containsKey(username)) {
			return LoginReturn.NO_SUCH_USER;
		}
		
		char[] prekey = DataHelper.addArrs(username.toCharArray(), password);
		byte[] salt = this.salts.get(username);
		SecretKey key;
		try {
			key = this.factory.generateSecret(new PBEKeySpec(prekey, salt, ITERATION_COUNT, 256));
		} catch (InvalidKeySpecException e) { throw new Error(e); }
		byte[] str = Null.nonNull(key.getEncoded());
		char[] calculated = DataHelper.toHex(str);
		
		if(Arrays.equals(this.passwords.get(username), calculated))
			return LoginReturn.LOGIN_SUCCESS;
		return LoginReturn.INVALID_PASSWORD;
	}
	
	private byte[] generateSalt()
	{
		byte[] salt = new byte[SALT_SIZE];
		this.random.nextBytes(salt);
		return salt;
	}
	
	private void unprotectedWrite(String username, char[] password)
	{
		char[] prekey = DataHelper.addArrs(username.toCharArray(), password);
		byte[] salt = this.generateSalt(); //Generate new salt, no reaason to reuse
		SecretKey key;
		try {
			key = this.factory.generateSecret(new PBEKeySpec(prekey, salt, ITERATION_COUNT, 256));
		} catch (InvalidKeySpecException e) { throw new Error(e); }
		byte[] str = Null.nonNull(key.getEncoded());
		this.salts.put(username, salt);
		this.passwords.put(username, DataHelper.toHex(str));
	}
	
	@Override
	public void flush(DataContext context) throws IOException 
	{
		try(PrintWriter writer = new PrintWriter(context.getWriteStream("userdata", "auth.dat")))
		{
			for(String username : this.salts.keySet()) {
				writer.println(username + "||" + new String(DataHelper.toHex(this.salts.get(username))) + "||" + new String(this.passwords.get(username)));
			}
		}
	}

	@Override
	public void initialize(DataContext context) throws IOException
	{
		try(BufferedReader reader2 = new BufferedReader(new InputStreamReader(context.getReadStream("", ""))))
		{
			reader2.lines().forEach((s) -> {
				String[] split = s.split("\\|\\|");
				byte[] salt = DataHelper.fromHex(Null.nonNull(split[1].toCharArray()));
				char[] password = Null.nonNull(split[2].toCharArray());
				this.salts.put(Null.nonNull(split[0]), salt);
				this.passwords.put(Null.nonNull(split[0]), password);
			});
		}
	}

	@Override
	public boolean isInitialized() 
	{
		return this.initialized;
	}

}
