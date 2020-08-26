/**
 * 
 */
package claire.hrt.wiki.data.virtual;

import java.util.List;

/**
 * A data table for storing sequential records
 * 
 * @author Claire
 * 
 * @param <Type> The type of record in the table
 */
public interface DataTable<Type> {
	
	/**
	 * @param starting The point to start at
	 * @param limit The maximum number of records to select
	 * @return A list of records starting from <code>starting</code> with no more than <code>limit</code> records
	 */
	List<Type> getStartingFrom(int starting, int limit);
	
	/**
	 * @return The number of total records in the table
	 */
	int records();

}
