/**
 * 
 */
package claire.hrt.wiki.data;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;

import claire.hrt.wiki.commons.Null;
import claire.hrt.wiki.data.enumerate.UserRole;
import claire.hrt.wiki.data.except.DuplicateKeyException;
import claire.hrt.wiki.data.virtual.AuthenticationTable;
import claire.hrt.wiki.data.virtual.DataContext;
import claire.hrt.wiki.data.virtual.SessionTable;
import claire.hrt.wiki.data.virtual.UserTable;

/**
 * @author Claire
 */
public final class WebState {

	/**
	 * The canonical instance of WebState.
	 */
	public static final WebState INST = new WebState();
	
	private final DataContext context;
	private final DataContext backup;
	
	@SuppressWarnings("javadoc") public final AuthenticationTable auth;
	@SuppressWarnings("javadoc") public final UserTable users;
	@SuppressWarnings("javadoc") public final SessionTable sessions;
	
	private WebState()
	{
		this.backup = new DataContextImpl();
		this.context = new DataContextImpl();
		
		//TODO: Add logic to see if there was a previous failed flush!
		
		this.auth = new AuthenticationTableImpl();
		this.users = new UserTableImpl();
		this.sessions = new SessionTableImpl();
		
		try {
			this.auth.initialize(this.context);
			this.users.initialize(this.context);
			this.sessions.initialize(this.context);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		
		if(!this.auth.exists("Claire")) {
			try {
				this.auth.writeRaw("Claire", DataHelper.fromHex(Null.nonNull("3401CCE5C89B3441".toCharArray())), Null.nonNull("4F8A7751412159904BD1BB8CA9AF878BB314295CCC35931E39394A496CF5F82C".toCharArray()));
				this.users.addUser(new User("Claire", "Claire Muriel Fey", "she/her", UserRole.ROOT, new HashMap<>()));
			} catch (@SuppressWarnings("unused") DuplicateKeyException e1) { /**/ }
		}
		
		Runtime.getRuntime().addShutdownHook(this.new WebStateShutdownHook());
	}
	
	/**
	 * Flushes all tables onto persistent storage.
	 * <br>
	 * If there is in error during saving, it will call System.exit();
	 */
	public synchronized void flush()
	{
		try {		
			this.auth.flush(this.backup);
			this.users.flush(this.backup);
			this.sessions.flush(this.backup);
		} catch(IOException e) {
			//TODO: Add proper logging!
			System.out.println(e);
			System.out.println("Error: Could not write to backup on flush(). Shutting down.");
			Runtime.getRuntime().exit(0);
		}
		
		try {		
			this.auth.flush(this.context);
			this.users.flush(this.context);
			this.sessions.flush(this.context);
		} catch(IOException e) {
			//TODO: Add proper logging!
			System.out.println(e);
			System.out.println("Error: Backup was saved successfully but flush to main memory failed");
			Runtime.getRuntime().exit(0);
		}
	}
	
	/**
	 * A shutdown hook that flushes the web state before JVM shutdown
	 * 
	 * @author Claire
	 */
	protected final class WebStateShutdownHook extends Thread {
		
		@Override
		public void run()
		{
			WebState.this.flush();
		}
		
	}
	
}
