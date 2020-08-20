/**
 * 
 */
package claire.hrt.wiki.server.validate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Claire
 */
public class RequestValidator {

	private final Field[] fields;
	
	/**
	 * @param fields
	 */
	public RequestValidator(Field ... fields)
	{
		this.fields = fields;
	}
	
	/**
	 * @param req The HTTP Request to validate
	 * @return An error, or an empty string if the field is valid
	 */
	public String validate(HttpServletRequest req)
	{
		for(Field field : this.fields)
		{
			String errQ = field.validate(req);
			if(errQ.length() > 0)
				return errQ;
		}
		return "";
	}
	
}
