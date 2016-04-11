package data_structures.btree;

import java.io.IOException;
import java.io.RandomAccessFile;


public class Node
{	
	private static final int LINKS_BLOCK_SIZE = CustomBTree.DEGREE * 4; //Each link is an integer, size 4 bytes
	public static final int BLOCK_SIZE = LINKS_BLOCK_SIZE + ((CustomBTree.DEGREE - 1) * Entry.TOTAL_BLOCK_SIZE); //Block size in bytes
	
	private Entry[] keys;
	private int[] links;
	private Node[] loadedLinks;
	private int nodeNum;
	private boolean needsSave;
	private CustomBTree tree;
	
	public Node(CustomBTree tree)
	{
		keys = new Entry[CustomBTree.DEGREE - 1];
		links = new int[CustomBTree.DEGREE];
		loadedLinks = new Node[CustomBTree.DEGREE];
		this.tree = tree;
		this.nodeNum = tree.getNumNodes();
		tree.incrementNumNodes();
	}
	
	public boolean isFull()
	{
		return getNumKeys() == keys.length;
	}
	
	public boolean isLeaf()
	{
		return links[0] == 0;
	}
	
	public Node getLink(int i)
	{
		return loadedLinks[i] != null ? loadedLinks[i] : read(i);
	}
	
	private Node read(int blockNum)
	{
		RandomAccessFile raf = tree.getRaf();
		
		try
		{
			//seek to the appropriate block
			raf.seek((BLOCK_SIZE + 1) * nodeNum);
			
			//load links
			for(int i = 0; i < CustomBTree.DEGREE; i++)
			{
				links[i] = raf.readInt();
				System.out.println("Loaded link " + i + " with value " + links[i]);
			}
			
			//load entries
			for(int i = 0; i < CustomBTree.DEGREE; i++)
			{
				//load the key for this entry
				String key
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void save()
	{
		RandomAccessFile raf = tree.getRaf();
		
		try
		{
			//seek to the appropriate block
			raf.seek((BLOCK_SIZE + 1) * nodeNum);
			
			//save links
			for(int i : links)
				raf.writeInt(i);
			
			//iterate through each entry in this node
			for(Entry e : keys)
			{
				if(e == null)
					continue;
				
				//save the key for the entry first
				raf.write(e.getKeyUtf());
				
				//save the values for the entry next
				for(Value v : e.getValues())
				{
					if(v == null)
						continue;
					
					//save the url for this value
					raf.write(v.getUrlUtf());
					
					//save the tf-idf for this value
					raf.writeDouble(v.getTfIdf());
				}
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public int getNodeNum()
	{
		return nodeNum;
	}
	
	public Entry[] getKeys()
	{
		return keys;
	}
	
	public void setKeys(Entry[] entries)
	{
		keys = entries;
	}
	
	public int getNumKeys()
	{
		int c = 0;
		for(Entry e : keys)
			if(e != null)
				c++;
		
		return c;
	}
	public boolean splitRoot(Entry e)
	{
		//Create new left and right nodes
		Node l = new Node(tree);
		Node r = new Node(tree);
		//Switch keys to new nodes
		for(int x = 0; x < (CustomBTree.DEGREE-1)/2; x++)
		{
			l.keys[x] = this.keys[x];
			r.keys[x] = this.keys[x+(CustomBTree.DEGREE/2)];
		}
		//Switch links
		for(int x = 0; x < CustomBTree.DEGREE/2; x++)
		{
			l.links[x] = this.links[x];
			r.links[x] = this.links[x+(CustomBTree.DEGREE/2)];
		}
		//Fix root keys
		keys[0] = keys[(CustomBTree.DEGREE-1)/2];
		for(int x = 1; x < keys.length; x++)
		{
			keys[x] = null;
		}
		//Fix root links
		int lnum = l.getNodeNum();
		int rnum = r.getNodeNum();
		
		links[0] = lnum;
		links[1] = rnum;
		l.save();
		r.save();
		for(int x = 2; x < links.length; x++)
		{
			links[x] = 0;
		}
		needsSave = true;
		return insert(e);
	}
	
	public boolean insert(Entry e)
	{
		//Search for link to check next
		for(int x = 0; x < keys.length; x++)
		{
			if(keys[x] == null)
			{
				if(links[x] == 0)
				{
					needsSave = true;
					keys[x] = e;
					return true;
				}
				else
				{
					Node linkx = getLink(x);
					if(linkx.isFull() == false)
					{
						boolean ret = linkx.insert(e);//save success
						if(linkx.needsSave)//if node changed
						{
							linkx.needsSave = false;//reset save
							linkx.save();
						}
						return ret;//return success
					}
					else//if the node is full
					{
						splitFullNode(linkx);//save the two links
						needsSave = true;
						return insert(e);
					}
				}
			}
			else if(e.compareTo(keys[x]) == 0)
			{
				return false;
			}
			else if(e.compareTo(keys[x]) < 0)
			{
				if(links[x] != 0)
				{
					Node linkx = getLink(x);
					if(linkx.isFull() == false)
					{
						boolean ret = linkx.insert(e);//save success
						if(linkx.needsSave)//if node changed
						{
							linkx.needsSave = false;//reset save
							linkx.save();
						}
						return ret;//return success
					}
					else
					{
						splitFullNode(linkx);
						needsSave = true;
						return insert(e);
					}
				}
				else//add to this node
				{
					for(int i = 0; i >= keys.length; i++)
					{
						if(e.compareTo(keys[i]) == 0)
							return false;
					}
					for(int i = keys.length-2; i >= x; i--)
						keys[i+1] = keys[i];
					
					keys[x] = e;
					needsSave = true;
					return true;
				}
			}
			else if(x == CustomBTree.DEGREE - 2)
			{
				Node linkx1 = getLink(x+1);
				boolean ret = linkx1.insert(e);//save success
				if(linkx1.needsSave)//if node changed
				{
					linkx1.needsSave = false;//reset save
					linkx1.save();//re-write it
				}
				return ret;//return success
			}
		}//end for loop to search for insert spot
		
		return true;
	}//end insert
	
	public void splitFullNode(Node fNode)
	{
		int midIndex = 0;
		Entry mid = fNode.keys[(CustomBTree.DEGREE-1)/2];
		for(int x = 0; x < keys.length; x++)
		{
			if(keys[x] == null)
			{
				keys[x] = mid;
				midIndex = x;
				break;
			}
			else if(mid.compareTo(keys[x]) < 0)
			{//slide all the values over
				for(int i = keys.length-1; i > x; i--)
				{
					keys[i] = keys[i-1];
					links[i+1] = links[i];
				}
				keys[x] = mid;
				midIndex = x;
				break;
			}
		}
		Node right = new Node(tree);//make new right node
		for(int x = 0; x < (CustomBTree.DEGREE/2)-1; x++)
		{
			right.keys[x] = fNode.keys[x+(CustomBTree.DEGREE/2)];
			right.links[x] = fNode.links[x+(CustomBTree.DEGREE/2)];
		}
		right.links[CustomBTree.DEGREE/2-1] = fNode.links[CustomBTree.DEGREE-1];
		int rnum = right.getNodeNum();
		links[midIndex+1] = rnum;
		right.save();
		for(int x = (CustomBTree.DEGREE/2)-1; x < CustomBTree.DEGREE-1; x++)
		{
			fNode.keys[x] = null;
			fNode.links[x+1] = 0;
		}
		links[midIndex] = fNode.getNodeNum();
		fNode.save();
	}
	
	public Value[] search(String key)
	{
		for(int x = 0; x < keys.length; x++)
		{
			if(keys[x] == null)
			{
				if(isLeaf())
					return null;
				
				return getLink(x).search(key);
			}
			else if(key.compareTo(keys[x].getKey()) == 0)
				return keys[x].getValues();
			else if(key.compareTo(keys[x].getKey()) < 0)
				return isLeaf() ? null : getLink(x).search(key);
		}
		return null;
	}
}
