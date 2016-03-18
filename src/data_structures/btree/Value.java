package data_structures.btree;

public class Value
{
	private String url;
	private double tfIdf;
	
	public Value(String url, double tfIdf)
	{
		this.url = url;
		this.tfIdf = tfIdf;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public double getTfIdf()
	{
		return tfIdf;
	}
}
