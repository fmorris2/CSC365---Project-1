import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import data_structures.WebPageCache;
import data_structures.btree.CustomBTree;


public class Application
{
	private Corpus corpus;
	private List<CustomUrl> potentialUrls;
	private CustomUrl primaryUrl;
	private CustomUrl closestUrl;
	private GUI gui;
	private CustomBTree freqTree;
	private WebPageCache pageCache;
	
	public Application(WebPageCache pageCache, CustomBTree freqTree)
	{
		this.corpus = new Corpus();
		this.potentialUrls = new ArrayList<>();
		this.gui = new GUI(this);
		this.gui.setVisible(true);
		this.freqTree = freqTree;
		this.pageCache = pageCache;
	}
	
	public void execute()
	{
		try
    	{
    		//clear corpus from previous calculations
    		corpus.clear();
    		potentialUrls.clear();
    		
    		//parse GUI info
    		parseInfo();
    		
    		//parse words from all of the web pages
    		long time = System.currentTimeMillis();
    		addWords();
    		System.out.println("It took " + (System.currentTimeMillis() - time) + "ms to parse the web pages");
    		
    		//calculate TF-IDF values for each word in each document
    		calculateTfIdf();
    		
    		//compare and find most closely related URL
    		closestUrl = corpus.getClosestRelated(primaryUrl);
    		gui.getClosestLabel().setText("Closest: " + closestUrl.getUrl());
    		System.out.println(closestUrl.getUrl() + " is most closely related with " + primaryUrl.getUrl());
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
	}
	
	private void calculateTfIdf()
    {
    	for(CustomUrl url : corpus)
    		url.getFreqTable().calculate();
    }
    
    private void addWords()
    {
    	for(CustomUrl url : corpus)
    	{
    		try
    		{
    			if(isCached(url))
    				continue;
    			
	    		System.out.println("Adding words from url: " + url.getUrl());
	    		
	    		//parse body of web page with JSoup
	    		String body = Utils.getWebPageBody(url.getUrl());
	    		
	    		//split by spaces
	    		String[] bodyParts = body != null ? body.split(" ") : null;
	    		
	    		if(bodyParts == null)
	    			continue;
	    		
	    		for(String s : bodyParts)
	    		{
	    			if(s.length() == 0)
	    				continue;
	    			
	    			url.getFreqTable().addWord(s);
	    		}
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    	}
    }
    
    private boolean isCached(CustomUrl url)
    {
    	long lastModified;
		
    	try
    	{
			URLConnection connection = new URL(url.getUrl()).openConnection();
			lastModified = connection.getLastModified();
			
			String cachedVal = pageCache.get(url.getUrl());
			if(cachedVal != null)
			{
				if(!cachedVal.equals(""+lastModified))
					pageCache.put(url.getUrl(), ""+lastModified);
				else
					return true;
			}
			else if(lastModified > 0)
				pageCache.put(url.getUrl(), ""+lastModified);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    	return false;
    }
    
    private void parseInfo()
    {
    	primaryUrl = new CustomUrl(gui.getPrimaryTextBox().getText(), corpus);
    	corpus.setPrimaryUrl(primaryUrl);
		
		//Parse potential urls
		for (String line : gui.getPotentialTextArea().getText().split("\\n"))
		{
			line = line.trim();
			if(!line.isEmpty())
 				potentialUrls.add(new CustomUrl(line, corpus));
		}
    }
    
    public void end()
    {
    	System.out.println("On end");
    	pageCache.save(Loader.PAGE_CACHE_PATH);
    }
	
}
