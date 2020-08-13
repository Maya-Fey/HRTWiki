package claire.hrt.wiki.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.junit.jupiter.api.Test;

import claire.hrt.wiki.data.virtual.DataContext;

/**
 * @author Claire
 */
public class TestDataContextImpl {
	
	/**
	 * Test reading and writing from context
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testFileReadWrite()
	{
		try {
			DataContext context = DataTestHelper.TEST_DATA_CONTEXT;
			try(FileOutputStream stream = context.getWriteStream("testpath", "test.file")) {
				PrintWriter writer = new PrintWriter(stream);
				writer.println("Hello there");
				writer.flush();
			}
			try(FileInputStream stream = context.getReadStream("testpath", "test.file")) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
				assertEquals("Hello there", reader.readLine());
			}
		} catch (IOException e) {
			fail(e);
		}
	}

}
