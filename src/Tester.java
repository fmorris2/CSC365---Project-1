import data_structures.btree.CustomBTree;
import data_structures.btree.Value;


public class Tester
{

	public static void main(String[] args)
	{
		CustomBTree bTree = new CustomBTree("test.com");
		
		/*
		for(int i = 0; i < 1000; i++)
		{
			System.out.println("putting " + i);
			bTree.put(""+i, new Value("todo.com", 1.0));
		}
		*/
		bTree.put("noodle", new Value("blabla.com", 1.0));
		bTree.put("dookie", new Value("blabla.com", 1.0));
		bTree.put("dog", new Value("blabla.com", 1.0));
		bTree.put("laptop", new Value("blabla.com", 1.0));
		bTree.put("water", new Value("blabla.com", 1.0));
		bTree.put("water", new Value("took took", 34.5));
		/*
		bTree.put("took", new Value("blabla.com", 1.0));
		bTree.put("rook", new Value("blabla.com", 1.0));
		bTree.put("casino", new Value("blabla.com", 1.0));
		bTree.put("smooth", new Value("blabla.com", 1.0));
		bTree.put("santana", new Value("blabla.com", 1.0));
		bTree.put("trump", new Value("blabla.com", 1.0));
		bTree.put("problem", new Value("blabla.com", 1.0));
		bTree.put("noodle head", new Value("blabla.com", 1.0));
		bTree.put("metallica", new Value("blabla.com", 1.0));
		bTree.put("needle", new Value("blabla.com", 1.0));
		*/
		
		System.out.println("Trying to find water");
		Value[] v = bTree.get("water");
		if(v == null)
			System.out.println("Value is null");
		else
			for(Value val : v)
			{
				if(val == null)
					continue;
				System.out.println("Val with url " + val.getUrl() + " with tf-idf of " + val.getTfIdf());
			}
		
	}

}
