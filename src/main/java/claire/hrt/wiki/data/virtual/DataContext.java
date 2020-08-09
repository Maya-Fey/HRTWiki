package claire.hrt.wiki.data.virtual;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Programming context for database read/write
 * 
 * @author Claire
 */
public interface DataContext {
	
	/**
	 * @param domain The domain for this file
	 * @param name The name of this file
	 * @return A stream capable of reading to the file
	 * @throws IOException If there is an error in creating, finding, or reading from the file
	 */
	FileInputStream getReadStream(String domain, String name) throws IOException;
	
	/**
	 * @param domain The domain for this file
	 * @param name The name of this file
	 * @return A stream capable of writing to the file
	 * @throws IOException If there is an error in creating, finding, or writing to the file
	 */
	FileOutputStream getWriteStream(String domain, String name) throws IOException;
	
	/**
	 * Disposes of this context, destroying any file associations and closing
	 * any network connections
	 */
	void dispose();

}
