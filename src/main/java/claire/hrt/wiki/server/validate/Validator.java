package claire.hrt.wiki.server.validate;

/**
 * Given a field value, do we 
 * 
 * @author Claire
 */
public interface Validator {
	
	/**
	 * @param value The value of the field
	 * @return An error if the field is invalid, an empty otherwise
	 */
	String validate(String value);

}
