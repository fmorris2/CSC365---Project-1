package data_structures.btree;

import java.io.File;
import java.io.RandomAccessFile;


public class CustomBTree
{
	public static final int DEGREE = 4;
	
	private Node root;
	private int height;
	private int numNodes;
	private String parentUrl;
	private RandomAccessFile raf;
	
	public CustomBTree(String parentUrl)
	{
		try
		{
			this.parentUrl = parentUrl;
			
			File f = new File("trees/"+parentUrl+".raf");
			
			raf = new RandomAccessFile(f, "rw");
			root = new Node(this, true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Value[] get(String key)
	{
		return root.search(key);
	}
	
	public boolean put(String key, Value value)
	{
		//System.out.println("Inserting entry with key " + key + " and value " + value.getUrl() + ", " + value.getTfIdf());
		return insert(new Entry(key, value));
	}
	
	private boolean insert(Entry e)
	{
		if(root.isFull())//if root is full
		{
			if(root.splitRoot(e)) //Split root and increment height
			{
				height++;
				return true;
			}
		}
		else if(root.insert(e)) //Insert the entry down the tree
			return true;
		
		return false;	
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getNumNodes()
	{
		return numNodes;
	}
	
	public RandomAccessFile getRaf()
	{
		return raf;
	}
	
	public void incrementNumNodes()
	{
		numNodes++;
	}
	
	public Node getRoot()
	{
		return root;
	}
}
