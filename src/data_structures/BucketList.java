package data_structures;

import java.util.Arrays;
import java.util.LinkedList;

public class BucketList<K, V> extends LinkedList<Bucket<K, V>>
{
	private static final long serialVersionUID = 795688579905882920L;
	
	private final int NUM_BUCKETS = 173; //Arbitrary prime number
	
	public BucketList()
	{
		
	}
	
	public void add(int index, BucketEntry<K, V> entry)
	{
		if(index >= size())
			fillUpToBucket(index, entry);
		else
			addToExistingBucket(index, entry);
	}
	
	private void fillUpToBucket(int index, BucketEntry<K, V> entry)
	{
		for(int i = size(); i <= index; i++)
			add(null);
		
		add(new Bucket<>(entry));
	}
	
	private void addToExistingBucket(int index, BucketEntry<K, V> entry)
	{
		if(get(index) == null)
			set(index, new Bucket<>(entry));
		else
			get(index).add(entry);
	}
}
