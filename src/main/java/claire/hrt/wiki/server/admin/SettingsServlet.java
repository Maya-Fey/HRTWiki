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
import claire.hrt.wiki.data.User;
import claire.hrt.wiki.data.WebState;
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
	
	private static final RequestValidator validator = new RequestValidator(
			new Field("name", true, new LengthValidator(2, 64), new CharacterValidator(NAME_ALLOWED)),
			new Field("pronouns", true, new LengthValidator(3, 20), new CharacterValidator(PRONOUN_ALLOWED))
		);
	
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
		try {
			User user = session.getUser().changeName(Null.nonNull(request.getParameter("name"))).changePronouns(Null.nonNull(request.getParameter("pronouns")));
			WebState.INST.users.updateUser(user);
			session.updateUser(user);
		} catch (@SuppressWarnings("unused") NoSuchKeyException e) { /**/ }
		request.setAttribute("success", "true");
		this.get(request, session, response);
	}
	
}
