package data_structures.btree;

import java.io.UnsupportedEncodingException;


public class Entry implements Comparable<Entry>
{
	private static final int KEY_BLOCK_SIZE = 256; //256 bytes = 64 characters UTF-32
	public static final int MAX_CHILDREN = 5;
	private static final int VALUE_BLOCK_SIZE = (MAX_CHILDREN * 256) + (MAX_CHILDREN * 8); //vals contain both URL and 8 byte double value for TF-IDF
	public static final int TOTAL_BLOCK_SIZE = KEY_BLOCK_SIZE + VALUE_BLOCK_SIZE;
	
	private String key;
	private Value[] values = new Value[MAX_CHILDREN];
	
	public Entry(String key, Value value)
	{
		this.key = key;
		this.values[0] = value;
	}
	
	public Entry(Entry e)
	{
		this.key = e.getKey();
		this.values = e.getValues();
	}
	
	@Override
	public int compareTo(Entry o)
	{
		return key.compareTo(o.getKey());
	}

	public String getKey()
	{
		return key;
	}
	
	public byte[] getKeyUtf()
	{
		try
		{
			return key.getBytes("UTF-32BE");
		} 
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Value[] getValues()
	{
		return values;
	}
	
	public void setKey(String key)
	{
		this.key = key;
	}
}
