package data_structures.btree;

public class Node
{
	private Entry[] children;
	
	public Node()
	{
		children = new Entry[CustomBTree.DEGREE];
	}
	
	public int getNumChildren()
	{
		return children.length;
	}
	
	public Entry[] getChildren()
	{
		return children;
	}
	
	public boolean addEntry(Entry e)
	{
		if(children.length == CustomBTree.DEGREE - 1)
			return false;
		
		int index;
		int comparison = 1;
		
		//Find appropriate index for the new entry
		for(index = 0; index < children.length; index++)
		{
			if(children[index] == null || comparison < 0)
				break;
			
			comparison = e.compareTo(children[index]);
		}
		
		if(children[index] == null) //Just insert, no swap needed
		{
			children[index] = e;
			return true;
		}
		
		//Swap if necessary
		Entry temp = children[index];
		children[index] = e;
		if(comparison < 0 || index == 0)
			children[index + 1] = temp;
		else
			children[index - 1] = temp;
		
		return true;
	}
	
}
