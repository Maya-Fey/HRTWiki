/**
 * 
 */
package claire.hrt.wiki.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import claire.hrt.wiki.commons.Null;
import claire.hrt.wiki.data.except.DuplicateKeyException;
import claire.hrt.wiki.data.except.NoSuchKeyException;
import claire.hrt.wiki.data.virtual.DataContext;
import claire.hrt.wiki.data.virtual.UserTable;

/**
 * An implementation of the UserTable
 * 
 * @author Claire
 */
public class UserTableImpl implements UserTable {

	private final SortedMap<String, User> users = new TreeMap<>();
	
	private boolean initialized;

	@Override
	public User getUserByName(String username) throws NoSuchKeyException {
		if(!this.users.containsKey(username)) {
			throw new NoSuchKeyException(username + " does not exist in the user table.");
		}
		return this.users.get(username);
	}

	@Override
	public void addUser(User user) throws DuplicateKeyException {
		if(this.users.containsKey(user.getUsername())) {
			throw new DuplicateKeyException(user.getUsername() + " is already inside the user table");
		}
		this.users.put(user.getUsername(), user);
	}

	@Override
	public void updateUser(User user) throws NoSuchKeyException {
		if(!this.users.containsKey(user.getUsername())) {
			throw new NoSuchKeyException(user.getUsername() + " does not exist in the user table.");
		}
		this.users.put(user.getUsername(), user);
	}
	
	@Override
	public void initialize(DataContext context) throws IOException {
		try(BufferedReader reader2 = new BufferedReader(new InputStreamReader(context.getReadStream("userdata", "users.dat"))))
		{
			reader2.lines().forEach((s) -> {
				try {
					this.addUser(new User(Null.nonNull(s)));
				} catch (DuplicateKeyException e) {
					throw new Error(e);
				}
			});
		} catch(Error e) {
			throw new IOException(e);
		}
		this.initialized = true;
	}

	@Override
	public boolean isInitialized() {
		return this.initialized;
	}

	@Override
	public void flush(DataContext context) throws IOException {
		try(PrintWriter writer = new PrintWriter(context.getWriteStream("userdata", "users.dat")))
		{
			for(User user : this.users.values()) {
				writer.println(user.persist());
			}
		}
	}

	@Override
	public List<User> getStartingFrom(int starting, int limit) {
		//Holy shit just kill me. How do you not return a List if your elements are ordered.
		//Technically this saves memory as we're not storing two lists. Technically.
		Iterator<User> iter = this.users.values().iterator();
		while(starting-- > 0) iter.next();
		List<User> ret = new ArrayList<>(limit - Math.max(0, (starting + limit) - this.users.size()));
		while(iter.hasNext() && limit-- > 0) ret.add(iter.next());
		return Null.nonNull(Collections.unmodifiableList(ret));
		
	}

	@Override
	public int records() {
		return this.users.size();
	}

}
