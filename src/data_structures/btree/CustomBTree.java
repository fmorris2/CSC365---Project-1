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
			System.out.println("Not null");
			System.out.println("Number of links: " + node.getNumLinks());
			Entry[] keys = node.getKeys();
			Node[] links = node.getLinks();
			
			for(Entry e : keys)
			{
				if(e == null)
					continue;
				
				System.out.print(e.getKey() + " ");
			}
			
			System.out.println();
			
			for(int i = 0; i < numKeys; i++)
			{
				boolean isLess = isLess(key, keys[i].getKey());
				boolean isGreater = isGreater(key, keys[i].getKey());
				System.out.println("Comparing " + keys[i].getKey() + " to " + key);
				if(keys[i].getKey().equals(key))
					return keys[i].getValues();
				else if(isLess)
				{
					System.out.println("Going to link " + i);
					node = links[i];
					break;
				}
				else if(i == numKeys - 1 //greater than last key
						|| (isGreater && isLess(key, keys[i + 1].getKey()))) //greater than current key && less than next
				{
					System.out.println("Going to link " + (i + 1));
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
	
	private void makeNewRoot(Entry e)
	{
		System.out.println("Make new root, starting links: " + root.getNumLinks());
		Node addTo = root;
		Node newRoot = new Node(null); //create the new root
		
		root.setParent(newRoot);//set the parent of old root to the new root
	
		Entry middle = new Entry(root.getKeys()[MIDDLE]); //find middle entry
		
		Node secondHalf = split(root); //split the old root in half
		
		//remove middle key and swap last key to
		secondHalf.getKeys()[0] = new Entry(secondHalf.getKeys()[1]);
		secondHalf.getKeys()[1] = null;
		
		if(isGreater(e.getKey(), middle.getKey())) //see if we have to add the new entry to the second half
			addTo = secondHalf;
			
		//repair parents links
		newRoot.getLinks()[0] = root;
		newRoot.getLinks()[1] = secondHalf;
		
		newRoot.addEntry(middle);
		addTo.addEntry(e);
		System.out.println("New root keys: " + newRoot.getNumKeys() + ", links: " + newRoot.getNumLinks());
		System.out.println("First half size: " + root.getNumKeys() + ", links: " + root.getNumLinks());
		System.out.println("Second half size: " + secondHalf.getNumKeys() + ", links: " + secondHalf.getNumLinks());	
		root = newRoot;
		height++;
	}
	
	private void recursiveAdd(Node starting, Entry e)
	{
		if(!starting.isFull())
		{
			System.out.println("Starting is not full, simply adding entry");
			starting.addEntry(e);
		}
		else
		{
			System.out.println("Starting is full");
			Node addTo = starting;
			
			if(starting.equals(root)) //we're at root and it's full
			{
				System.out.println("At root, and it's full. Making new one.");
				makeNewRoot(e);
			}
			else //Node is full and not root
			{
				System.out.println("Node is full and it isn't root");
				Entry middle = new Entry(starting.getKeys()[MIDDLE]);
				
				//first, split the child
				Node secondHalf = split(starting);
				
				//remove middle key and swap last key to
				secondHalf.getKeys()[0] = new Entry(secondHalf.getKeys()[1]);
				secondHalf.getKeys()[1] = null;
				
				if(isGreater(e.getKey(), middle.getKey()))
					addTo = secondHalf;
				
				//recursively add middle to parent
				System.out.println("Recursive call");
				recursiveAdd(starting.getParent(), middle);
				
				//and repair parents links
				int linkIndex = findLink(starting.getParent(), starting);
				if(linkIndex != -1)
				{
					System.out.println("Found link index! Appending second half");
					starting.getParent().getLinks()[linkIndex + 1] = secondHalf;
				}
				else
				{
					System.out.println("Couldn't find link. Adding to end");
					starting.getParent().getLinks()[starting.getParent().getNumLinks()] = secondHalf;
				}
				
				for(Node n : starting.getParent().getLinks())
				{
					System.out.println((n == null));
				}
				System.out.println("Parent links: " + starting.getParent().getNumLinks());
				System.out.println("Finishing recursiveAdd, adding entry");
				addTo.addEntry(e);
			}
		}
	}
	
	private int findLink(Node parent, Node child)
	{
		System.out.println("Parent num links: " + parent.getNumLinks());
		for(int i = 0; i < parent.getNumLinks(); i++)
			if(parent.getLinks()[i].equals(child))
				return i;
		
		return -1;
	}

	private void insert(String key, Value value)
	{
		System.out.println("Insert");
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
			System.out.println("Leaf node overflow");
			recursiveAdd(child, new Entry(key, value));
		}
		else
		{
			System.out.println("Leaf node is not full, simply adding entry");
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
