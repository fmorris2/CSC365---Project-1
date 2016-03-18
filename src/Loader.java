import data_structures.WebPageCache;
import data_structures.btree.CustomBTree;

public class Loader
{
	public static final String PAGE_CACHE_PATH = "page.cache";
	public static final String B_TREE_PATH =  "b.tree";
	
	private CustomBTree freqTree;
	private WebPageCache pageCache;
	
	
	public Loader()
	{
		this.pageCache = new WebPageCache();
		this.pageCache.load(PAGE_CACHE_PATH);
		
		this.freqTree = new CustomBTree();
		this.freqTree.load(B_TREE_PATH);
	}
	
	public CustomBTree getFreqTree()
	{
		return freqTree;
	}
	
	public WebPageCache getPageCache()
	{
		return pageCache;
	}
	
}
