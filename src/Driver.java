import data_structures.btree.Value;



public class Driver
{	
	public static void main(String[] args) throws InterruptedException
	{
		/*
		Loader loader = new Loader();
		Application app = new Application(loader.getPageCache(), loader.getFreqTree());
		*/
		//loop through each category
		final String TEST = "book";
		for(Category c : Category.values())
		{
			System.out.println(c);
			Value[] record = c.getBTree().get(TEST);
			if(record == null)
				System.out.println("null");
			else
			{
				System.out.println("Values: " + record.length);
				for(Value v : record)
				{
					if(v == null)
						continue;
					else
						System.out.println(TEST + " - " + v.getUrl() + " - " + v.getTfIdf());
				}
			}
		}
	}

}
