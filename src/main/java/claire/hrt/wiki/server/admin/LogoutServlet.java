/**
 * 
 */
package claire.hrt.wiki.server.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import claire.hrt.wiki.data.Session;
import claire.hrt.wiki.server.AbstractStateServlet;

/**
 * @author Claire
 */
public class LogoutServlet extends AbstractStateServlet {

	private static final long serialVersionUID = -244596056631786168L;

	@Override
    protected void get(HttpServletRequest request, Session session, HttpServletResponse response) throws ServletException, IOException {
		if(session.isAuthenticated()) 
			session.unauthenticate();
		response.sendRedirect("/admin/login.jsp");
    }
	
	@Override
    protected void post(HttpServletRequest request, Session session, HttpServletResponse response) throws ServletException, IOException {
		this.get(request, session, response);
	}

}
