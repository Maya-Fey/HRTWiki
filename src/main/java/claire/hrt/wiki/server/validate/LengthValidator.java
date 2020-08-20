/**
 * 
 */
package claire.hrt.wiki.server.validate;

/**
 * @author Claire
 *
 */
public class LengthValidator implements Validator {

	private final int minLen, maxLen;
	
	/**
	 * @param minLen
	 * @param maxLen
	 */
	public LengthValidator(int minLen, int maxLen) {
		this.minLen = minLen;
		this.maxLen = maxLen;
	}

	@Override
	public String validate(String value) {
		int len = value.length();
		if(len < this.minLen)
			return "Minimum length is " + this.minLen;
		else if(len > this.maxLen)
			return "Maximum length is " + this.maxLen;
		return "";
	}

}
