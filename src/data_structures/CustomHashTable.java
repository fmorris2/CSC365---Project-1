package data_structures;

import java.util.HashSet;
import java.util.Set;

public class CustomHashTable<K, V>
{	
	private BucketList<K, V> buckets;
	private Set<K> keySet;
	
	public CustomHashTable()
	{
		keySet = new HashSet<>();
		buckets = new BucketList<K, V>();
	}
	
	/* START PUBLIC API */
	
	public V get(K key)
	{
		//compute hash / index for key and return value in appropriate bucket
		int index = computeIndex(key);
		
		if(index >= buckets.size())
			return null;
		
		Bucket<K, V> bucket = buckets.get(index);
		return bucket == null ? null : bucket.get(key);
	}
	
	public void put(K key, V value)
	{
		//add key to keySet
		keySet.add(key);
		
		//compute hash / index and put in appropriate bucket
		int index = computeIndex(key);
		buckets.add(index, new BucketEntry<K, V>(key, value));
	}
	
	public Set<K> keySet()
	{
		return keySet;
	}
	
	public boolean containsKey(K key)
	{
		return keySet.contains(key);
	}
	
	/* END PUBLIC API */
	
	/* START PRIVATE METHODS */
	
	private int computeIndex(K key)
	{
		return buckets.size() == 0 ? 0 : Math.abs(computeHash(key) % buckets.size());
	}
	
	private int computeHash(K key)
	{
		return key.hashCode();
	}
	
	/* END PRIVATE METHODS */
}
