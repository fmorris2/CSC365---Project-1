package data_structures.btree;


public class Entry implements Comparable<Entry>
{
	private static final int MAX_CHILDREN = 5;
	
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
	
	public Value[] getValues()
	{
		return values;
	}
	
	public void setKey(String key)
	{
		this.key = key;
	}
}
