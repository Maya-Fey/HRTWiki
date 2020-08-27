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
public class AddUserServlet extends AbstractStateServlet {

	private static final long serialVersionUID = -6986577362318872849L;
	
	@Override
    protected void get(HttpServletRequest request, Session session, HttpServletResponse response) throws ServletException, IOException {
		if(!session.isAuthenticated() || !session.getUser().getPermissions().hasAtLeast(UserRole.EDITOR)) {
			response.sendRedirect("/admin/login.jsp");
			return;
		}
		request.setAttribute("sidebar", AdminSidebar.getItems(session.getUser().getPermissions()));
		request.setAttribute("username_allowedchars", LoginServlet.USERNAME_ALLOWED);
		request.setAttribute("name_allowedchars", SettingsServlet.NAME_ALLOWED);
		request.setAttribute("pronoun_allowedchars", SettingsServlet.PRONOUN_ALLOWED);
		request.setAttribute("password_allowedchars", LoginServlet.PASSWORD_ALLOWED);
		request.setAttribute("userRole", session.getUser().getPermissions());
		request.getRequestDispatcher("/WEB-INF/admin/adduser.jsp").forward(request, response);
    }
	
}
