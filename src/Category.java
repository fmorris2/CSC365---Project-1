import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import data_structures.btree.CustomBTree;
import data_structures.btree.Value;


public enum Category
{
	BOOKS("http://www.goodreads.com/"),
	BUSINESS("http://www.businessinsider.com/"),
	EDUCATION("http://www.education.com/"),
	ENTERTAINMENT("http://www.ew.com/"),
	FINANCE("http://www.reuters.com/finance"),
	BOARD_GAMES("http://www.boardgames.com/"),
	VIDEO_GAMES("http://www.ign.com/"),
	HEALTHCARE("http://www.health.com/"),
	FITNESS("http://www.fitnessmagazine.com/"),
	LIFESTYLE("http://www.lifehack.org/lifestyle"),
	MEDICAL("http://www.medicalnewstoday.com/"),
	MUSIC("http://www.billboard.com/news/"),
	NEWS("http://www.cnn.com/"),
	PHOTOGRAPHY("http://www.popphoto.com/tags/news/"),
	PRODUCTIVITY("http://www.entrepreneur.com/topic/productivity/"),
	REFERENCE("http://www.reference.com/"),
	SOCIAL_NETWORKING("http://www.facebook.com/"),
	SPORTS("http://www.espn.com/"),
	TRAVEL("http://www.travelweekly.com/"),
	WEATHER("http://www.weather.com/");
	
	private static final int MAX_SUB_LINKS = 10;
	
	private String parentUrl;
	private Corpus corpus;
	private CustomBTree bTree;
	
	Category(String parentUrl)
	{
		this.parentUrl = parentUrl;
		this.corpus = new Corpus();
		bTree = new CustomBTree(parentUrl.substring(11, parentUrl.indexOf("/", 12)));
		if(needsUpdate(bTree.getLastModifiedRaf()))
		{
			System.out.println("bTree for " + this + " needs update!");
			//first, remove the existing file
			bTree.removeRaf();
			loadUrlsIntoCorpus();
			addWordsIntoCorpus();
			calculateTfIdf();
			loadIntoBTree();
		}
	}
	
	private void loadIntoBTree()
	{
		for(CustomUrl url : corpus)
		{
			System.out.println("Inserting words from " + url.getUrl() + " into b tree");
			for(String word : url.getFreqTable().keySet())
			{
				Value toInsert = new Value(url.getUrl(), url.getFreqTable().get(word).getTfIdf());		
				bTree.put(word, toInsert);
			}
		}
	}
	
	private void calculateTfIdf()
    {
    	for(CustomUrl url : corpus)
    		url.getFreqTable().calculate();
    }
	
	private void loadUrlsIntoCorpus()
	{
		//add words from parent link first
		corpus.add(new CustomUrl(parentUrl, corpus));
		List<String> links = Utils.getSubLinks(parentUrl);
		Collections.shuffle(links);
		
		for(String link : links)
		{
			try
			{
				//check word length first
				if(link.getBytes("UTF-32BE").length >= 256)
				{
					System.out.println(link + " is >= 256 BYTES. THROWING OUT!");
					continue;
				}
				
				if(corpus.size() >= MAX_SUB_LINKS)
					break;
				
				CustomUrl newUrl = new CustomUrl(link, corpus);
				
				if(corpus.contains(newUrl))
				{
					System.out.println("Skipping duplicate url " + link);
					continue;
				}
			
				System.out.println(link);
				corpus.add(newUrl);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private void addWordsIntoCorpus()
	{
		for(CustomUrl url : corpus)
    	{
    		try
    		{  			
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
	    			
	    			//check word length first
					if(s.getBytes("UTF-32BE").length >= 256)
					{
						System.out.println(s + " is >= 256 BYTES. THROWING OUT!");
						continue;
					}
	    			
	    			url.getFreqTable().addWord(s);
	    		}
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    	}
	}
	
	public String getParentUrl()
	{
		return parentUrl;
	}
	
	public CustomBTree getBTree()
	{
		return bTree;
	}
	
	private boolean needsUpdate(Date d)
	{
		if(d == null)
			return true;
		
		LocalDateTime ldt = LocalDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault());
		LocalDateTime now = LocalDateTime.now();
		
		return ldt.plusWeeks(1).isBefore(now);
	}
	
	public void parseUrl(String url, String words)
	{
		CustomUrl customUrl = new CustomUrl(url, corpus);
		
		for(String word : words.split(" "))
			customUrl.getFreqTable().addWord(word);
		
		corpus.add(customUrl);
	}
}
