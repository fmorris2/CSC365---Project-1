package data_structures.btree;


public class CustomBTree
{
	public static final int DEGREE = 4;
	
	private Node root;
	private int height;
	private int numPairs;
	
	public CustomBTree()
	{
		root = new Node(null);
	}
	
	public Value[] get(String key)
	{
		return search(root, key, 0);
	}
	
	private Value[] search(Node node, String key, int height)
	{
		Entry[] keys = node.getKeys();
		
		//external node
		if(height == this.height)
		{
			//System.out.println("Searching through external node");
			//System.out.println("Num keys: " + node.getNumKeys());
			for(int j = 0; j < node.getNumKeys(); j++)
			{
				if(isEqual(key, keys[j].getKey()))
					return keys[j].getValues();
			}
		}
		else //internal node
		{
			//System.out.println("Searching through internal node with " + node.getNumKeys() + " keys and " + node.getNumLinks() + " links");
			for(int j = 0; j < node.getNumKeys(); j++) //Iterate through keys
			{
				//if the target is LESS than the CURRENT key
				if(isLess(key, keys[j].getKey()))
				{
					//System.out.println("isLess: Searching link " + j + " of a node at height " + height);
					return search(node.getLinks()[j], key, height + 1); //Go to the left link of this node
				}
				else if(j == node.getNumKeys() - 1) //We're at the last key & it the target is GREATER
				{
					//System.out.println("is last key: Searching link " + (j + 1) + " of a node at height " + height);
					return search(node.getLinks()[j + 1], key, height + 1); //Go to the right link
				}
			}
		}
		
		return null;
	}
	
	public void put(String key, Value value)
	{
		Value[] existing = get(key);
		
		if(existing != null)
		{
			for(int i = 0; i < existing.length; i++)
				if(existing[i] == null)
					existing[i] = value;			
		}
		else //key doesn't exist in tree yet
		{
			System.out.println("Insert");
			insert(key, value);
			numPairs++;
		}
	}
	
	private void makeNewRoot(Entry e)
	{
		height++;
	}
	
	private void recursiveAdd(Node starting, Entry e)
	{
		if(!starting.isFull())
			starting.addEntry(e);
		else
		{
			Node addTo = starting;
			
			if(starting.equals(root)) //we're at root and it's full
				makeNewRoot(e);
			else //Node is full and not root
			{
				Entry middle = starting.getKeys()[DEGREE / 2 - 1];
				
				//first, split the child
				Node secondHalf = split(starting);
				
				if(isGreater(e.getKey(), middle.getKey()))
					addTo = secondHalf;
					
				//and repair parents links
				starting.getParent().getLinks()[findLink(starting.getParent(), starting) + 1] = secondHalf;
				
				recursiveAdd(starting.getParent(), middle);
			}
			
			addTo.addEntry(e);
		}
	}
	
	private int findLink(Node parent, Node child)
	{
		for(int i = 0; i < DEGREE; i++)
			if(parent.getLinks()[i].equals(child))
				return i;
		
		return -1;
	}

	private void insert(String key, Value value)
	{
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
		
		//If the leaf node is full
		if(child.isFull())
		{
			recursiveAdd(child, new Entry(key, value));
			System.out.println("Leaf node overflow");
		}
		else
		{
			child.addEntry(new Entry(key, value));
		}
	}
	
	private Node findNextChild(String key, Node n)
	{
		Entry[] keys = n.getKeys();
		for(int j = 0; j < n.getNumKeys(); j++) //Iterate through keys
		{
			//if the target is LESS than the CURRENT key
			if(isLess(key, keys[j].getKey()))
			{
				System.out.println("Going left");
				return n.getLinks()[j];
			}
			else if(j == n.getNumKeys() - 1) //We're at the last key & it the target is GREATER
			{
				System.out.println("Going right");
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
		return keyOne.compareTo(keyTwo) < 0;
	}
	
	private boolean isEqual(String keyOne, String keyTwo)
	{
		return keyOne.compareTo(keyTwo) == 0;
	}
	
	private boolean isGreater(String keyOne, String keyTwo)
	{
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
}
