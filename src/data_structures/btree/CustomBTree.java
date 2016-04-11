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
			root = new Node(this);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Value[] get(String key)
	{
		return search(key);
	}
	
	private Value[] search(String key)
	{
		return root.search(key);
	}
	
	public boolean put(String key, Value value)
	{
		return insert(new Entry(key, value));
	}
	
	private boolean insert(Entry e)
	{
		Value[] existing = get(e.getKey());
		
		if(existing != null) //Key already exists in tree
		{
			for(int i = 0; i < existing.length; i++)
			{
				if(existing[i] == null) //There is an open slot to put this entry
				{
					existing[i] = e.getValues()[0];
					return true;
				}
			}
			
			return false; //There are no open slots to put the entry
		}
		else //Key doesn't exist in tree
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
		}
		
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
}
