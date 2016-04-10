package data_structures.btree;


public class CustomBTree
{
	public static final int DEGREE = 4;
	private static final int MIDDLE = DEGREE / 2 - 1;
	
	private Node root;
	private int height;
	private int numPairs;
	
	public CustomBTree()
	{
		root = new Node(null);
	}
	
	public Value[] get(String key)
	{
		return search(key);
	}
	
	private Value[] search(String key)
	{
		Node node = root;
		int numKeys = node.getNumKeys();
		
		while(node != null && numKeys > 0)
		{	
			//System.out.println("Not null");
			//System.out.println("Number of links: " + node.getNumLinks());
			Entry[] keys = node.getKeys();
			Node[] links = node.getLinks();
			
			for(Entry e : keys)
			{
				if(e == null)
					continue;
				
				//System.out.print(e.getKey() + " ");
			}
			
			//System.out.println();
			
			for(int i = 0; i < numKeys; i++)
			{
				boolean isLess = isLess(key, keys[i].getKey());
				boolean isGreater = isGreater(key, keys[i].getKey());
				//System.out.println("Comparing " + keys[i].getKey() + " to " + key);
				if(keys[i].getKey().equals(key))
					return keys[i].getValues();
				else if(isLess)
				{
					//System.out.println("Going to link " + i);
					node = links[i];
					break;
				}
				else if(i == numKeys - 1 //greater than last key
						|| (isGreater && isLess(key, keys[i + 1].getKey()))) //greater than current key && less than next
				{
					//System.out.println("Going to link " + (i + 1));
					node = links[i + 1];
					break;
				}
			}
			
			numKeys = node != null ? node.getNumKeys() : numKeys;
		}
		
		return null;
	}
	
	public void put(String key, Value value)
	{
		Value[] existing = get(key);
		
		if(existing != null)
		{
			System.out.println(key + " ALREADY EXISTS IN TREE!");
			for(int i = 0; i < existing.length; i++)
				if(existing[i] == null)
					existing[i] = value;			
		}
		else //key doesn't exist in tree yet
		{
			String before = toString();
			insert(key, value);
			String after = toString();
			numPairs++;
			System.out.println("Before: " + before);
			System.out.println("\nAfter: " + after);
		}
	}
	
	private void add(Node n, Entry e)
	{
		//If the leaf node is full
		if(n.isFull())
		{
			System.out.println("Node overflow");
			Entry[] overflow = n.getOverflow(e);
			Entry[] parentOverflow = null;
			Node[] overflowLinks = null;
			
			while(overflow != null)
			{
				Node newRoot = null;
				
				if(n.equals(root))
				{
					System.out.println("NEED TO MAKE NEW ROOT!");
					newRoot = new Node(null);
					n.setParent(newRoot);
				}
				
				Entry middle = new Entry(overflow[MIDDLE]);
				
				//START remove middle key from node
				n.setKeys(overflow);
				n.shiftLeft(n.getKeys().length - 1, MIDDLE);
				
				Entry[] fixed = new Entry[DEGREE - 1];
				for(int i = 0; i < fixed.length; i++)
					fixed[i] = overflow[i];
				
				n.setKeys(fixed);
				//END remove middle key from node
				
				//Split the node in half
				Node secondHalf = split(n);
				int linkIndex = newRoot != null ? 0 : findLink(n.getParent(), n);
				
				//parent is also full, need to do something about that
				if(n.getParent().isFull())
				{
					parentOverflow = n.getParent().getOverflow(middle);
					overflowLinks = new Node[DEGREE + 1];
					
					Node[] parentLinks = n.getParent().getLinks();
					boolean repaired = false;
					
					for(int i = 0; i < DEGREE; i++)
					{
						if(i <= linkIndex)
							overflowLinks[i] = parentLinks[i];
						else
						{
							
							if(!repaired) //second half must be inserted into this index
							{
								overflowLinks[i] = secondHalf;
								repaired = true;
							}
							
							overflowLinks[i + 1] = parentLinks[i];
						}
					}
					
					overflow = n.getParent();
				}
			
				
				if(n.equals(root))
				{
					height++;
					root = newRoot;
				}
			}
		}
		else
		{
			System.out.println("Leaf node is not full, simply adding entry");
			n.addEntry(e);
		}
	}
	
	private int findLink(Node parent, Node child)
	{
		int index;
		for(index = 0; index < parent.getNumLinks(); index++)
			if(parent.getLinks()[index].equals(child))
				return index;
		
		return -1;
	}

	private void insert(String key, Value value)
	{
		System.out.println("Insert " + key);
		Node n = root;
		Node child = n;
		
		//Find the leaf node where value should be added
		while(!child.isLeaf())
		{
			if(!child.equals(n))
				n = child;
			
			child = findNextChild(key, n);
		}
		
		//We've found the leaf node
		addRecursively(child, new Entry(key, value));
		
	}
	
	private Node findNextChild(String key, Node n)
	{
		Entry[] keys = n.getKeys();
		for(int j = 0; j < n.getNumKeys(); j++) //Iterate through keys
		{
			//if the target is LESS than the CURRENT key
			if(isLess(key, keys[j].getKey()))
			{
				System.out.println("Going to link " + j);
				return n.getLinks()[j];
			}
			else if(j == n.getNumKeys() - 1) //We're at the last key & it the target is GREATER
			{
				System.out.println("Going to link " + (j + 1));
				return n.getLinks()[j + 1];
			}
		}
		
		return null;
	}
	
	private Node split(Node n)
	{		
		//split
		Node newNode = new Node(n.getParent()); //Create the new node
		
		for(int i = 0; i < DEGREE / 2; i++) //Iterate through half of the node
		{
			newNode.getKeys()[i] = n.getKeys()[(DEGREE / 2 + i) - 1]; //New node contains second half of original node
			newNode.getLinks()[i + 1] = n.getLinks()[DEGREE / 2 + i]; //Transfer links to newNode
			n.getKeys()[(DEGREE / 2 + i) - 1] = null; //Clean transferred links from original node
			n.getLinks()[DEGREE / 2  + i] = null;  //Clean transferred keys from original node
		}
		
		return newNode;
	}
	
	private boolean isLess(String keyOne, String keyTwo)
	{
		//return Integer.parseInt(keyOne) < Integer.parseInt(keyTwo);
		return keyOne.compareTo(keyTwo) < 0;
	}
	
	private boolean isGreater(String keyOne, String keyTwo)
	{
		//return Integer.parseInt(keyOne) > Integer.parseInt(keyTwo);
		return keyOne.compareTo(keyTwo) > 0;
	}
	
	public void load(String filePath)
	{
		
	}
	
	public void save(String filePath)
	{
		
	}
	
	public int getNumPairs()
	{
		return numPairs;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public String toString()
	{
		String tabs = "\t";
		for(int i = 0; i < height * 4 + 2; i++)
			tabs += "\t";
		
		int h = 0;
		Node n = root;
		String base = "";
		System.out.println("height: " + height);
		while(h <= height)
		{
			base = tabs + "|";
			for(int i = 0; i < n.getNumKeys(); i++)
			{
				Entry e = n.getKeys()[i];
				if(e == null)
					continue;
				
				base += e.getKey() + (i == n.getNumKeys() - 1 ? "" : " ");
			}
			base += "|";
			tabs = tabs.substring(0, tabs.length() - 2);
			
			base += "\n";
			for(Node link : n.getLinks())
			{
				if(link == null)
					continue;
				base += tabs + "|";
				
				for(int i = 0; i < link.getNumKeys(); i++)
				{
					Entry e = link.getKeys()[i];
					if(e == null)
						continue;
					
					base += e.getKey() + (i == link.getNumKeys() - 1 ? "" : " ");
				}
				base += "|";
				//tabs = tabs.substring(0, tabs.length() - 2);
			}
			h++;
		}
		
		return base;
	}
}
