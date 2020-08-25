/**
 * 
 */
package claire.hrt.wiki.server.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import claire.hrt.wiki.commons.Null;
import claire.hrt.wiki.data.Session;
import claire.hrt.wiki.data.WebState;
import claire.hrt.wiki.data.enumerate.LoginReturn;
import claire.hrt.wiki.data.enumerate.UserRole;
import claire.hrt.wiki.data.except.NoSuchKeyException;
import claire.hrt.wiki.server.AbstractStateServlet;
import claire.hrt.wiki.server.validate.CharacterValidator;
import claire.hrt.wiki.server.validate.Field;
import claire.hrt.wiki.server.validate.LengthValidator;
import claire.hrt.wiki.server.validate.RequestValidator;

/**
 * @author Claire
 */
public class ChangePasswordServlet extends AbstractStateServlet {

	private static final long serialVersionUID = -6986577362318872849L;
	
	private static final RequestValidator validator = new RequestValidator(
			new Field("curpassword", true, new LengthValidator(8, 64), new CharacterValidator(LoginServlet.PASSWORD_ALLOWED)),
			new Field("password", true, new LengthValidator(8, 64), new CharacterValidator(LoginServlet.PASSWORD_ALLOWED)),
			new Field("confirmpassword", true, new LengthValidator(8, 64), new CharacterValidator(LoginServlet.PASSWORD_ALLOWED)) 
		);
	
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
	
	@Override
    protected void post(HttpServletRequest request, Session session, HttpServletResponse response) throws ServletException, IOException {
		if(!session.isAuthenticated() || !session.getUser().getPermissions().hasAtLeast(UserRole.EDITOR)) {
			response.sendRedirect("/admin/login.jsp");
			return;
		}
		String err = validator.validate(request);
		if(err.length() > 0) {
			request.setAttribute("failure_reason", err);
			this.get(request, session, response);
			return;
		}
		if(!request.getParameter("password").equals(request.getParameter("confirmpassword"))) {
			request.setAttribute("failure_reason", "Passwords do not match");
			this.get(request, session, response);
			return;
		}
		LoginReturn login = WebState.INST.auth.read(session.getUser().getUsername(), Null.nonNull(request.getParameter("curpassword").toCharArray()));
		if(login == LoginReturn.LOGIN_SUCCESS) {
			try {
				WebState.INST.auth.overwrite(session.getUser().getUsername(), Null.nonNull(request.getParameter("password").toCharArray()));
			} catch (@SuppressWarnings("unused") NoSuchKeyException e) { /**/ }
			request.setAttribute("success", "true");
			this.get(request, session, response);
		} else {
			request.setAttribute("failure_reason", "No such user");
			this.get(request, session, response);
		}
	}

}
