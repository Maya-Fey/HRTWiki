/**
 * 
 */
package claire.hrt.wiki.server.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import claire.hrt.wiki.data.Session;
import claire.hrt.wiki.data.enumerate.UserRole;
import claire.hrt.wiki.server.AbstractStateServlet;

/**
 * @author Claire
 */
public class SettingsServlet extends AbstractStateServlet {

	private static final long serialVersionUID = -6986577362318872849L;
	
	/**
	 * Allowed characters for name
	 */
	public static final String NAME_ALLOWED = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
	/**
	 * Allowed characters for pronouns
	 */
	public static final String PRONOUN_ALLOWED = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz /";
	
	@Override
    protected void get(HttpServletRequest request, Session session, HttpServletResponse response) throws ServletException, IOException {
		if(!session.isAuthenticated() || !session.getUser().getPermissions().hasAtLeast(UserRole.EDITOR)) {
			response.sendRedirect("/admin/login.jsp");
			return;
		}
		request.setAttribute("sidebar", AdminSidebar.getItems(session.getUser().getPermissions()));
		request.setAttribute("name_allowedchars", NAME_ALLOWED);
		request.setAttribute("pronoun_allowedchars", PRONOUN_ALLOWED);
		request.setAttribute("displayName", session.getUser().getDisplayName());
		request.setAttribute("pronouns", session.getUser().getPronouns());
		request.setAttribute("role", session.getUser().getPermissions().toString());
		request.getRequestDispatcher("/WEB-INF/admin/settings.jsp").forward(request, response);
    }

}
