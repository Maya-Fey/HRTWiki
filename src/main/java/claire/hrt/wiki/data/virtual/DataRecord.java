package claire.hrt.wiki.data.virtual;

import java.io.IOException;

/**
 * Abstract data table
 * 
 * @author Claire
 */
public interface DataRecord {
	
	/**
	 * Initializes this table from persistent memory
	 * 
	 * @param context The application context for reading data.
	 * @throws IOException If there's an exception during reading/parsing
	 */
	void initialize(DataContext context) throws IOException;
	
	/**
	 * @return Whether this table has been initialized from file or not
	 */
	boolean isInitialized();
	
	/**
	 * Flushes the table to persistent memory
	 * 
	 * @param context The application context for writing data
	 * @throws IOException If there's an exception during writing
	 */
	void flush(DataContext context) throws IOException;

}
