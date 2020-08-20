/**
 * 
 */
package claire.hrt.wiki.server.validate;

/**
 * @author Claire
 *
 */
public class IntegerValidator implements Validator {

	@Override
	public String validate(String value) {
		try {
			Integer.parseInt(value);
		} catch(@SuppressWarnings("unused") NumberFormatException e) { 
			return "Integer expected.";
		}
		return "";
	}

}
