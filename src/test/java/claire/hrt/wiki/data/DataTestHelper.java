/**
 * 
 */
package claire.hrt.wiki.data;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import claire.hrt.wiki.commons.Null;
import claire.hrt.wiki.data.virtual.DataContext;

/**
 * @author Claire
 */
public final class DataTestHelper {
	
	/**
	 * A temporary data context for testing
	 */
	public static DataContext TEST_DATA_CONTEXT = initTestDataContext();
	
	private static DataContext initTestDataContext()
	{
		Path file;
		try {
			file = Files.createTempDirectory("mytemp-");
			String s = Null.nonNull(file.toFile().getCanonicalPath());
			return new DataContextImpl(s);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}	
	}

}
