

public class Driver
{
	
	public static void main(String[] args)
	{
		Loader loader = new Loader();
		Application app = new Application(loader.getPageCache(), loader.getFreqTree());
	}

}
