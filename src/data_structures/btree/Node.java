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
	
	public void setKeys(Entry[] entries)
	{
		keys = entries;
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
		return addEntry(keys, e);
	}
	
	private int addEntry(Entry[] arr, Entry e)
	{	
		int index;
		int comparison = 1;
		
		//Find appropriate index for the new entry
		for(index = 0; index < arr.length - 1; index++)
		{
			comparison = arr[index] == null ? -1 : e.compareTo(arr[index]);
			
			//System.out.println("index: " + index);
			if(arr[index] == null || comparison < 0)
				break;
		}
		
		if(arr[index] == null) //Just insert, no swap needed
		{
			arr[index] = e;
		}
		else
		{
			//Shift if necessary
			if(index == 0 || comparison <= 0)
				shiftRight(index);
			else
				shiftLeft(index, 0);
			
			arr[index] = e;
		}
		
		return index;
	}
	
	public Entry[] getOverflow(Entry e)
	{
		Entry[] overflow = new Entry[CustomBTree.DEGREE];
		for(int i = 0; i < keys.length; i++)
			overflow[i] = keys[i];
		
		addEntry(overflow, e);
		return overflow;
	}
	
	public void shiftLeft(int index, int endIndex)
	{
		Entry temp = keys[index] == null ? null : new Entry(keys[index]);
		keys[index] = null;
		
		for(int i = index - 1; i >= endIndex; i--)
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
