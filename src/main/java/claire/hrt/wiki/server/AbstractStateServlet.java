package claire.hrt.wiki.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.annotation.Nullable;

import claire.hrt.wiki.commons.Null;
import claire.hrt.wiki.data.Session;
import claire.hrt.wiki.data.WebState;
import claire.hrt.wiki.data.except.NoSuchKeyException;

/**
 * @author Claire
 *
 */
public class AbstractStateServlet extends HttpServlet {
	
	private static final long serialVersionUID = -1031500483249940650L;

	@Override
	protected void doGet(@Nullable HttpServletRequest req, @Nullable HttpServletResponse resp) throws ServletException, IOException
    {
		if(req == null || resp == null) 
			return;
		
		Session session = getSessionForRequest(req, resp);
		
		this.get(req, session, resp);
    }
	
	@Override
	protected void doPost(@Nullable HttpServletRequest req, @Nullable HttpServletResponse resp) throws ServletException, IOException
    {
		if(req == null || resp == null) 
			return;
		
		Session session = getSessionForRequest(req, resp);
		
		this.post(req, session, resp);
    }
	
	/**
	 * Performs a get request on this servlet
	 * 
	 * @param req The request
	 * @param session The session associate with the request
	 * @param resp The response to write to
	 * @throws ServletException If there's an exception in processing the request
	 * @throws IOException If there's an exception writing to the network
	 */
	protected void get(HttpServletRequest req, Session session, HttpServletResponse resp) throws ServletException, IOException
	{
		super.doGet(req, resp);
	}
	
	/**
	 * Performs a post request on this servlet
	 * 
	 * @param req The request
	 * @param session The session associate with the request
	 * @param resp The response to write to
	 * @throws ServletException If there's an exception in processing the request
	 * @throws IOException If there's an exception writing to the network
	 */
	protected void post(HttpServletRequest req, Session session, HttpServletResponse resp) throws ServletException, IOException
	{
		super.doPost(req, resp);
	}
	
	private static Session getSessionForRequest(HttpServletRequest req, HttpServletResponse resp) {
		@Nullable Session session = null;
		
		@SuppressWarnings("null")
		@Nullable Cookie[] cookies = req.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie != null && cookie.getName().equals("wikisession")) {
					String value = Null.nonNull(cookie.getValue());
					if(WebState.INST.sessions.isActiveSession(value)) {
						try {
							session = WebState.INST.sessions.getSessionByID(value);
						} catch (@SuppressWarnings("unused") NoSuchKeyException e) { /**/ }
					} 
				}
			}
		}
		
		if(session == null) {
			session = WebState.INST.sessions.newSession();
			resp.addCookie(new Cookie("wikisession", session.getKey()));
		}
		
		return session;
	}

}
