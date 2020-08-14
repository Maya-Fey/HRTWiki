package claire.hrt.wiki.data;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import claire.hrt.wiki.commons.Null;
import claire.hrt.wiki.data.except.NoSuchKeyException;
import claire.hrt.wiki.data.virtual.DataContext;
import claire.hrt.wiki.data.virtual.SessionTable;

/**
 * @author Claire
 */
public class SessionTableImpl implements SessionTable {

	private final SecureRandom random;
	private final byte[] buf = new byte[16];
	
	private final Map<String, Session> activeSessions = new HashMap<>();
	
	private boolean initialized = false;
	
	/**
	 * Instantiates with the relevant crypto objects
	 */
	public SessionTableImpl()
	{
		try {
			this.random = Null.nonNull(SecureRandom.getInstance("SHA1PRNG"));
		} catch (NoSuchAlgorithmException e) {
			throw new Error(e);
		}
	}
	
	@Override
	public Session newSession() {
		String deriv = null;
		do {
			this.random.nextBytes(this.buf);
			deriv = new String(DataHelper.toHex(this.buf));
			
		} while(this.activeSessions.containsKey(deriv));
		return new Session(deriv);
	}

	@Override
	public boolean isActiveSession(String id) 
	{
		return this.activeSessions.containsKey(id);
	}

	@Override
	public Session getSessionByID(String id) throws NoSuchKeyException {
		if(!this.isActiveSession(id)) {
			throw new NoSuchKeyException();
		}
		return this.activeSessions.get(id);
	}
	
	@Override
	public void initialize(DataContext context) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInitialized() 
	{
		return this.initialized;
	}

	@Override
	public void flush(DataContext context) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
