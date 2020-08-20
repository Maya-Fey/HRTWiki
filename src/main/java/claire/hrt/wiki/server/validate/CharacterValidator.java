/**
 * 
 */
package claire.hrt.wiki.server.validate;

/**
 * @author Claire
 */
public class CharacterValidator implements Validator {
	
	private final String allowed;

	/**
	 * @param allowed
	 */
	public CharacterValidator(String allowed) {
		this.allowed = allowed;
	}

	@Override
	public String validate(String value) {
		for(int i = 0; i < value.length(); i++)
			if(this.allowed.indexOf(value.charAt(i)) != -1)
				return "Illegal character, allowed: `" + this.allowed + "`";
		return "";
	}

}
