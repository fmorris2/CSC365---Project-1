package data_structures.btree;

public class Entry implements Comparable<Entry>
{
	private String key;
	private Value value;
	
	@Override
	public int compareTo(Entry o)
	{
		return key.compareTo(o.getKey());
	}

	public String getKey()
	{
		return key;
	}
	
	public Value getValue()
	{
		return value;
	}
}
