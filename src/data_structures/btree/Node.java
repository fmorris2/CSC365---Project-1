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
			comparison = keys[index] == null ? -1 : e.compareTo(keys[index]);
			
			//System.out.println("index: " + index);
			if(keys[index] == null || comparison < 0)
				break;
		}
		
		if(keys[index] == null) //Just insert, no swap needed
		{
			keys[index] = e;
		}
		else
		{
			//Shift if necessary
			if(index == 0 || comparison <= 0)
				shiftRight(index);
			else
				shiftLeft(index);
			
			keys[index] = e;
		}
		
		return index;
	}
	
	private void shiftLeft(int index)
	{
		Entry temp = keys[index] == null ? null : new Entry(keys[index]);
		keys[index] = null;
		
		for(int i = index - 1; i >= 0; i--)
		{
			Entry temp2 = keys[i] == null ? null : new Entry(keys[i]);
			keys[i] = temp;
			temp = temp2;
		}
	}
	
	private void shiftRight(int index)
	{
		Entry temp = keys[index] == null ? null : new Entry(keys[index]);
		keys[index] = null;
		
		for(int i = index + 1; i < keys.length; i++)
		{
			Entry temp2 = keys[i] == null ? null : new Entry(keys[i]);
			keys[i] = temp;
			temp = temp2;
		}
	}
}
