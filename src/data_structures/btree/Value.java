package data_structures.btree;

import java.io.UnsupportedEncodingException;

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
	
	public byte[] getUrlUtf()
	{
		try
		{
			return url.getBytes("UTF-32BE");
		} 
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
