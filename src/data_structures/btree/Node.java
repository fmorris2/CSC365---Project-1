package data_structures.btree;

public class Node
{
	private Entry[] keys;
	private Node[] links;
	private Node parent;
	
	public Node(Node parent)
	{
		keys = new Entry[CustomBTree.DEGREE - 1];
		links = new Node[CustomBTree.DEGREE];
		this.parent = parent;
	}
	
	public boolean isFull()
	{
		return getNumKeys() == keys.length;
	}
	
	public boolean isLeaf()
	{
		for(Node n : links)
			if(n != null)
				return false;
		
		return true;
	}
	
	public Node getParent()
	{
		return parent;
	}
	
	public void setParent(Node n)
	{
		parent = n;
	}

	public Entry[] getKeys()
	{
		return keys;
	}
	
	public Node[] getLinks()
	{
		return links;
	}
	
	public int getNumKeys()
	{
		int c = 0;
		for(Entry e : keys)
			if(e != null)
				c++;
		
		return c;
	}
	
	public int getNumLinks()
	{
		int count = 0;
		for(Node n : links)
			if(n != null)
				count++;
		
		return count;
	}
	
	public int addEntry(Entry e)
	{
		if(getNumKeys() == CustomBTree.DEGREE - 1)
			return -1;
		
		int index;
		int comparison = 1;
		
		//Find appropriate index for the new entry
		for(index = 0; index < keys.length - 1; index++)
		{
			//System.out.println("index: " + index);
			if(keys[index] == null || comparison < 0)
				break;
			
			comparison = e.compareTo(keys[index]);
		}
		
		if(keys[index] == null) //Just insert, no swap needed
		{
			keys[index] = e;
		}
		else
		{
			//Swap if necessary
			Entry temp = keys[index];
			keys[index] = e;
			if(comparison < 0 || index == 0)
				keys[index + 1] = temp;
			else
				keys[index - 1] = temp;
		}
		
		return index;
	}
	
}
