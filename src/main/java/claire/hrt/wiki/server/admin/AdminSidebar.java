/**
 * 
 */
package claire.hrt.wiki.server.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.jdt.annotation.NonNull;

import claire.hrt.wiki.commons.Null;
import claire.hrt.wiki.data.enumerate.UserRole;

/**
 * An object representing the administration sidebar
 * 
 * @author Claire
 */
public class AdminSidebar {
	
	private static final Item[] SIDEBAR_ITEMS = {
		new Item(Category.USERSETTINGS, UserRole.BANNED, "Home", "/admin/index.jsp"),
		new Item(Category.USERSETTINGS, UserRole.EDITOR, "User Settings", "/admin/settings.jsp"),
		new Item(Category.USERSETTINGS, UserRole.EDITOR, "Change Password", "/admin/password.jsp"),
		new Item(Category.USERSETTINGS, UserRole.BANNED, "Logout", "/admin/logout.jsp"),
		new Item(Category.USERADMIN, UserRole.ADMIN, "Add User", "/admin/adduser.jsp"),
		new Item(Category.USERADMIN, UserRole.ADMIN, "Users", "/admin/users.jsp")
	};
	
	private static final Map<UserRole, Map<Category, List<Item>>> CACHE = new HashMap<>();
	
	/**
	 * @param role The role of a user
	 * @return All the admin side bar items this user has the permissions to see
	 */
	public static Map<Category, List<Item>> getItems(UserRole role)
	{
		if(CACHE.containsKey(role))
			return CACHE.get(role);
		
		@NonNull SortedMap<Category, List<Item>> ret = new TreeMap<>();
		
		for(Item item : SIDEBAR_ITEMS)
		{
			if(role.hasAtLeast(item.getPerms())) {
				if(!ret.containsKey(item.getCategory()))
					ret.put(item.getCategory(), new ArrayList<>());
				ret.get(item.getCategory()).add(item);
			}
		}
		
		Map<Category, List<Item>> retfinal = Null.nonNull(Collections.unmodifiableMap(ret));
		CACHE.put(role, retfinal);
		return retfinal;
	}
	
	/**
	 * An item on the sidebar
	 * s
	 * @author Claire
	 */
	public static class Item {
		
		private final Category category;
		private final UserRole perms;
		private final String name;
		private final String path;
		
		/**
		 * @param category
		 * @param perms
		 * @param name
		 * @param path
		 */
		public Item(Category category, UserRole perms, String name, String path) {
			this.category = category;
			this.perms = perms;
			this.name = name;
			this.path = path;
		}

		/**
		 * @return the category
		 */
		public Category getCategory() {
			return this.category;
		}

		/**
		 * @return the perms
		 */
		public UserRole getPerms() {
			return this.perms;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return this.name;
		}

		/**
		 * @return the path
		 */
		public String getPath() {
			return this.path;
		}
		
	}
	
	/**
	 * A sidebar category
	 * 
	 * @author Claire
	 */
	public enum Category {
		
		/**
		 * The user settings category. Contains settings, info, login/out
		 */
		USERSETTINGS("User Settings"),
		/**
		 * 
		 */
		USERADMIN("User Administration");
		
		/**
		 * The name of this category
		 */
		String name;
		
		private Category(String name)
		{
			this.name = name;
		}
		
		@Override
		public String toString()
		{
			return this.name;
		}
		
	}

}
