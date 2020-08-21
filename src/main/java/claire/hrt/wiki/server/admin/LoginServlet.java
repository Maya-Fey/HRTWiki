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
import claire.hrt.wiki.data.except.NoSuchKeyException;
import claire.hrt.wiki.server.AbstractStateServlet;
import claire.hrt.wiki.server.validate.CharacterValidator;
import claire.hrt.wiki.server.validate.Field;
import claire.hrt.wiki.server.validate.LengthValidator;
import claire.hrt.wiki.server.validate.RequestValidator;

/**
 * @author Claire
 */
public class LoginServlet extends AbstractStateServlet {

	private static final long serialVersionUID = -6986577362318872849L;
	
	private static final String USERNAME_ALLOWED = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";
	private static final String PASSWORD_ALLOWED = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~!@#$%^&*()-=_+";
	
	private static final RequestValidator validator = new RequestValidator(
				new Field("username", true, new LengthValidator(1, 32), new CharacterValidator(USERNAME_ALLOWED)),
				new Field("password", true, new LengthValidator(8, 64), new CharacterValidator(PASSWORD_ALLOWED)) 
			);
	
	@Override
    protected void get(HttpServletRequest request, Session session, HttpServletResponse response) throws ServletException, IOException {
		if(session.isAuthenticated()) {
			response.sendRedirect("/admin/index.jsp");
			return;
		}
		request.setAttribute("username_allowedchars", USERNAME_ALLOWED);
		request.setAttribute("password_allowedchars", PASSWORD_ALLOWED);
		request.getRequestDispatcher("/WEB-INF/admin/login.jsp").forward(request, response);
    }
	
	@Override
    protected void post(HttpServletRequest request, Session session, HttpServletResponse response) throws ServletException, IOException {
		if(session.isAuthenticated()) {
			response.sendRedirect("/admin/index.jsp");
			return;
		}
		
		String err = validator.validate(request);
		if(err.length() > 0) {
			request.setAttribute("failure_reason", err);
			this.get(request, session, response);
			return;
		}
		
        LoginReturn result = WebState.INST.auth.read(Null.nonNull(request.getParameter("username")), Null.nonNull(request.getParameter("password").toCharArray()));
        
        if(result == LoginReturn.LOGIN_SUCCESS) {
        	try {
				session.setAuthenticated(WebState.INST.users.getUserByName(Null.nonNull(request.getParameter("username"))), 3600 * 24 * 14);
			} catch (NoSuchKeyException e) {
				throw new ServletException(e);
			}
        	response.sendRedirect("/admin/index.jsp");
        } else if(result == LoginReturn.INVALID_PASSWORD) {
        	request.setAttribute("failure_reason", "Invalid password");
			this.get(request, session, response);
        } else {
        	request.setAttribute("failure_reason", "No such user");
			this.get(request, session, response);
        }
	}

}
