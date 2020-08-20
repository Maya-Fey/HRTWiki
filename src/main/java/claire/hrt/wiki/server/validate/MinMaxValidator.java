/**
 * 
 */
package claire.hrt.wiki.server.validate;

/**
 * @author Claire
 *
 */
public class MinMaxValidator implements Validator {

	private final int minVal, maxVal;
	
	/**
	 * @param minVal
	 * @param maxVal
	 */
	public MinMaxValidator(int minVal, int maxVal) {
		this.minVal = minVal;
		this.maxVal = maxVal;
	}

	@Override
	public String validate(String value) {
		int val = Integer.parseInt(value);
		if(val < this.minVal)
			return "Minimum value is " + this.minVal;
		else if(val > this.maxVal)
			return "Maximum value is " + this.maxVal;
		return "";
	}

}
