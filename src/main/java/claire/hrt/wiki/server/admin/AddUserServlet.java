/**
 * 
 */
package claire.hrt.wiki.server.admin;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import claire.hrt.wiki.commons.Null;
import claire.hrt.wiki.data.Session;
import claire.hrt.wiki.data.User;
import claire.hrt.wiki.data.WebState;
import claire.hrt.wiki.data.enumerate.UserRole;
import claire.hrt.wiki.data.except.DuplicateKeyException;
import claire.hrt.wiki.server.AbstractStateServlet;
import claire.hrt.wiki.server.validate.CharacterValidator;
import claire.hrt.wiki.server.validate.Field;
import claire.hrt.wiki.server.validate.IntegerValidator;
import claire.hrt.wiki.server.validate.LengthValidator;
import claire.hrt.wiki.server.validate.RequestValidator;

/**
 * @author Claire
 */
public class AddUserServlet extends AbstractStateServlet {

	private static final long serialVersionUID = -6986577362318872849L;
	
	private static final RequestValidator validator = new RequestValidator(
			new Field("username", true, new LengthValidator(1, 32), new CharacterValidator(LoginServlet.USERNAME_ALLOWED)),
			new Field("name", true, new LengthValidator(2, 64), new CharacterValidator(SettingsServlet.NAME_ALLOWED)),
			new Field("pronouns", true, new LengthValidator(3, 20), new CharacterValidator(SettingsServlet.PRONOUN_ALLOWED)),
			new Field("password", true, new LengthValidator(8, 64), new CharacterValidator(LoginServlet.PASSWORD_ALLOWED)),
			new Field("role", true, new IntegerValidator())
		);
	
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
		String username = Null.nonNull(request.getParameter("username"));
		String name = Null.nonNull(request.getParameter("name"));
		String pronouns = Null.nonNull(request.getParameter("pronouns"));
		char[] password = Null.nonNull(request.getParameter("password").toCharArray());
		UserRole role = UserRole.fromInt(Integer.parseInt(request.getParameter("role")));
		if(!session.getUser().getPermissions().hasMoreThan(role)) {
			request.setAttribute("failure_reason", "You don't have permissions to create users of that role");
			this.get(request, session, response);
			return;
		}
		if(WebState.INST.auth.exists(username)) {
			request.setAttribute("failure_reason", "A user with that username already exists");
			this.get(request, session, response);
			return;
		}
		User user = new User(username, name, pronouns, role, new HashMap<>());
		try {
			WebState.INST.auth.write(username, password);
			WebState.INST.users.addUser(user);
			request.setAttribute("success", "true");
		} catch (@SuppressWarnings("unused") DuplicateKeyException e) { /***/ }
		this.get(request, session, response);
	}
	
}
