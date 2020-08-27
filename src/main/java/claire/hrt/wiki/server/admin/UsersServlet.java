/**
 * 
 */
package claire.hrt.wiki.server.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import claire.hrt.wiki.data.Session;
import claire.hrt.wiki.data.WebState;
import claire.hrt.wiki.data.enumerate.UserRole;
import claire.hrt.wiki.server.AbstractStateServlet;
import claire.hrt.wiki.server.validate.Field;
import claire.hrt.wiki.server.validate.IntegerValidator;
import claire.hrt.wiki.server.validate.MinMaxValidator;
import claire.hrt.wiki.server.validate.RequestValidator;

/**
 * @author Claire
 */
public class UsersServlet extends AbstractStateServlet {

	private static final long serialVersionUID = -6986577362318872849L;
	
	private static final RequestValidator validator = new RequestValidator(
			new Field("current", true, new IntegerValidator(), new MinMaxValidator(0, Integer.MAX_VALUE)),
			new Field("per", true, new IntegerValidator(), new MinMaxValidator(10, 100))
		);
	
	private static final int[] options = new int[] { 25, 50, 75, 100 };
	
	@Override
    protected void get(HttpServletRequest request, Session session, HttpServletResponse response) throws ServletException, IOException {
		if(!session.isAuthenticated() || !session.getUser().getPermissions().hasAtLeast(UserRole.EDITOR)) {
			response.sendRedirect("/admin/login.jsp");
			return;
		}
		int current, per;
		String err = validator.validate(request);
		if(err.length() > 0) {
			current = 0;
			per = 25;
		} else {
			current = Integer.parseInt(request.getParameter("current"));
			per = Integer.parseInt(request.getParameter("per"));
		}
		request.setAttribute("current", current);
		request.setAttribute("per", per);
		request.setAttribute("max", WebState.INST.users.records());
		request.setAttribute("users", WebState.INST.users.getStartingFrom(current, per));
		request.setAttribute("options", options);
		request.setAttribute("sidebar", AdminSidebar.getItems(session.getUser().getPermissions()));
		request.getRequestDispatcher("/WEB-INF/admin/users.jsp").forward(request, response);
    }

}
