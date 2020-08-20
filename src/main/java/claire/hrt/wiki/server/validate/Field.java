/**
 * 
 */
package claire.hrt.wiki.server.validate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Claire
 */
public class Field {

	private final Validator[] validators;
	private final boolean required;
	private final String name;
	
	/**
	 * @param name
	 * @param required
	 * @param validators
	 */
	public Field(String name, boolean required, Validator ... validators)
	{
		this.name = name;
		this.required = required;
		this.validators = validators;
	}
	
	/**
	 * @param req The HTTP Request to validate
	 * @return An error, or an empty string if the field is valid
	 */
	public String validate(HttpServletRequest req)
	{
		String prop = req.getParameter(this.name);
		if(prop != null) {
			for(Validator validator : this.validators)
			{
				String errQ = validator.validate(prop);
				if(errQ.length() > 0)
					return "Field `" + this.name + "`: " + errQ;
			}
		} else if(this.required) {
			return "Field `" + this.name + "` is required";
		}
		return "";
	}
	
}
