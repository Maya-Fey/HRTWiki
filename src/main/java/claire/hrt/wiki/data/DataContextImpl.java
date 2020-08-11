package claire.hrt.wiki.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import claire.hrt.wiki.commons.except.PreconditionViolationException;
import claire.hrt.wiki.data.virtual.DataContext;

/**
 * An implementation of DataContext
 * 
 * @author Claire
 */
public class DataContextImpl implements DataContext {

	private static final String DEFAULT_PATH = "application_data/";
	
	private final String rootPath;
	
	/**
	 * Creates a data context from a given file path
	 * 
	 * @param rootPath The root path to use for all files provided by this context
	 */
	public DataContextImpl(String rootPath)
	{
		this.rootPath = rootPath;
		File file = new File(rootPath);
		if(file.exists()) {
			if(file.isFile()) {
				throw new PreconditionViolationException("Given root path points to a file");
			}
		} else {
			file.mkdir();
		}
	}
	
	/**
	 * Creates a data contacts from the deafault root file path
	 */
	public DataContextImpl()
	{
		this(DEFAULT_PATH);
	}

	@Override
	public FileInputStream getReadStream(String domain, String name) throws IOException {
		File dir = new File(this.rootPath + "/" + domain);
		if(!dir.exists()) dir.mkdir();
		File file = new File(this.rootPath + "/" + domain + "/" + name);
		if(!file.exists()) file.createNewFile();
		return new FileInputStream(file);
	}

	@Override
	public FileOutputStream getWriteStream(String domain, String name) throws IOException {
		File dir = new File(this.rootPath + "/" + domain);
		if(!dir.exists()) dir.mkdir();
		File file = new File(this.rootPath + "/" + domain + "/" + name);
		if(!file.exists()) file.createNewFile();
		return new FileOutputStream(file);
	}

	@Override
	public void dispose() {
		//Nothing here
	}

}
