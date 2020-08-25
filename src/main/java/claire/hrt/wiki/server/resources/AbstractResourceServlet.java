/**
 * 
 */
package claire.hrt.wiki.server.resources;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.annotation.Nullable;

import claire.hrt.wiki.commons.Null;
import claire.hrt.wiki.data.Session;
import claire.hrt.wiki.server.AbstractStateServlet;

/**
 * A servlet that returns files when they're requested, using the resources directory
 * 
 * @author Claire
 */
public abstract class AbstractResourceServlet extends AbstractStateServlet {

	private static final long serialVersionUID = 6718476860173423375L;
	
	private final byte @Nullable[] file;
	
	/**
	 * @param path
	 */
	public AbstractResourceServlet(String path)
	{
		byte[] assign = null;
		try(InputStream is = Null.nonNull(this.getClass().getResourceAsStream(path))) {
			assign = new byte[is.available()];
			is.read(assign);
		} catch(IOException e) {
			e.printStackTrace();
			this.file = null;
			return;
		}	
		this.file = assign;
	}
	
	@Override
	protected void get(HttpServletRequest req, Session session, HttpServletResponse resp) throws ServletException, IOException
	{
		if(this.file == null) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} else {
			resp.getOutputStream().write(this.file);
			resp.setStatus(HttpServletResponse.SC_OK);
		}
	}

}
