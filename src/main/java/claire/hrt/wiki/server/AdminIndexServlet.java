/**
 * 
 */
package claire.hrt.wiki.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import claire.hrt.wiki.data.Session;
import claire.hrt.wiki.data.enumerate.UserRole;

/**
 * @author Claire
 */
public class AdminIndexServlet extends AbstractStateServlet {

	private static final long serialVersionUID = -6986577362318872849L;
	
	@Override
    protected void get(HttpServletRequest request, Session session, HttpServletResponse response) throws ServletException, IOException {
		if(!session.isAuthenticated() || !session.getUser().getPermissions().hasAtLeast(UserRole.EDITOR)) {
			response.sendRedirect("/admin/login.jsp");
			return;
		}
		request.getRequestDispatcher("/WEB-INF/admin/index.jsp").forward(request, response);
    }

}
