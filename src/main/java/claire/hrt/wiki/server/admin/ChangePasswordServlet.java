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
public class ChangePasswordServlet extends AbstractStateServlet {

	private static final long serialVersionUID = -6986577362318872849L;
	
	@Override
    protected void get(HttpServletRequest request, Session session, HttpServletResponse response) throws ServletException, IOException {
		if(!session.isAuthenticated() || !session.getUser().getPermissions().hasAtLeast(UserRole.EDITOR)) {
			response.sendRedirect("/admin/login.jsp");
			return;
		}
		request.setAttribute("sidebar", AdminSidebar.getItems(session.getUser().getPermissions()));
		request.setAttribute("password_allowedchars", LoginServlet.PASSWORD_ALLOWED);
		request.getRequestDispatcher("/WEB-INF/admin/password.jsp").forward(request, response);
    }

}