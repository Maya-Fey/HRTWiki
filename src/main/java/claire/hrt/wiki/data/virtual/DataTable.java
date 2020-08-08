package claire.hrt.wiki.data.virtual;

/**
 * Abstract data table
 * 
 * @author Claire
 */
public interface DataTable {
	
	void initialize(DataContext context);
	
	boolean isInitialized();
	
	//TODO: Synchronization?
	void flush(DataContext context);

}
