/**
 * 
 */
package claire.hrt.wiki.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import claire.hrt.wiki.commons.Null;
import claire.hrt.wiki.data.Session;
import claire.hrt.wiki.data.WebState;
import claire.hrt.wiki.data.enumerate.LoginReturn;

/**
 * @author Claire
 */
public class LoginServlet extends AbstractStateServlet {

	private static final long serialVersionUID = -6986577362318872849L;
	
	@Override
    protected void get(HttpServletRequest request, Session session, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }
	
	@Override
    protected void post(HttpServletRequest request, Session session, HttpServletResponse response) throws ServletException, IOException {
		//TODO: IP rate limiting
		//TODO: Add additional checks
		if(request.getParameter("username") == null || request.getParameter("password") == null) {
			request.setAttribute("failure_reason", "Invalid Request");
			this.get(request, session, response);
		}
		
        LoginReturn result = WebState.INST.auth.read(Null.nonNull(request.getParameter("username")), Null.nonNull(request.getParameter("password").toCharArray()));
        
        if(result == LoginReturn.LOGIN_SUCCESS) {
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