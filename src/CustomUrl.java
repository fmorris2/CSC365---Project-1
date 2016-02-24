
public class CustomUrl
{
	private String url;
	private FrequencyTable freqTable;
	
	public CustomUrl(String url, Corpus corpus)
	{
		
		this.url = fixInput(url);
		this.freqTable = new FrequencyTable(corpus);
		corpus.add(this);
	}
	
	private String fixInput(String url)
	{
		if(!url.contains("www.") && !url.contains("http"))
			url = "www." + url;
		if(!url.contains("http"))
			url = "http://" + url;
		
		return url;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public FrequencyTable getFreqTable()
	{
		return freqTable;
	}
}
