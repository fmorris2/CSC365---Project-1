import data_structures.btree.CustomBTree;
import data_structures.btree.Value;


public class Tester
{

	public static void main(String[] args)
	{
		CustomBTree bTree = new CustomBTree();
		bTree.put("Tookie", new Value("tookie.com", 1.0));
		bTree.put("Doodo", new Value("doodoo.com", 1.0));
		bTree.put("Tookie", new Value("noodle.com", 5.4));
		bTree.put("Tookied", new Value("noodle.com", 5.4));
		bTree.put("Tookief", new Value("noodle.com", 5.4));
		bTree.put("Tookiegg", new Value("noodle.com", 5.4));
		bTree.put("Tookiesad", new Value("noodle.com", 5.4));
		bTree.put("Tookiesade", new Value("noodle.com", 5.4));
		bTree.put("Tookiesadsdfsd", new Value("noodle.com", 5.4));
		bTree.put("Tookiesadfdah", new Value("noodle.com", 5.4));
		bTree.put("Tookiesa34235d", new Value("noodle.com", 5.4));
		bTree.put("Tookiesad23522", new Value("noodle.com", 5.4));
		bTree.put("Tookiesad34tfgSD", new Value("noodle.com", 5.4));
		
		System.out.println("bTree height: " + bTree.getHeight() + ", numPairs: " + bTree.getNumPairs());
		Value[] v = bTree.get("Tookie");
		if(v == null)
			System.out.println("Value is null");
		else
			for(Value val : v)
				System.out.println("Val with url " + val.getUrl() + " with tf-idf of " + val.getTfIdf());
		
	}

}
