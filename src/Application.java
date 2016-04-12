import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import data_structures.CustomHashTable;
import data_structures.btree.CustomBTree;
import data_structures.btree.Value;


public class Application
{
	private static final int NUM_SUGGESTIONS = 5; //we'll suggest the top 5 links in the category
	
	private String url;
	private Set<String> words;
	private Category category;
	private String[] suggestions;
	private CustomHashTable<String, Integer> linkOccurences;
	private List<String> exclusions = Arrays.asList("and", "or", "to", "the");
	
	public Application(String url)
	{
		this.url = url;
		this.words = new HashSet<>();
		this.linkOccurences = new CustomHashTable<>();
		this.suggestions = new String[NUM_SUGGESTIONS];
	}
	
	public void execute()
	{
		try
    	{
    		//parse words from primary url
    		addWords();
    		System.out.println("Added " + words.size() + " words");
    		
    		Category mostSimilar = null;
    		double highestSum = -1;
    		
    		for(Category c : Category.values())
    		{
    			double toCompare = sumTfIdf(c.getBTree());
    			if(toCompare > highestSum)
    			{
    				mostSimilar = c;
    				highestSum = toCompare;
    			}
    			
    			System.out.println("Checking category " + c);
    			System.out.println("sumTfIdf for category " + c + ": " + toCompare);
    		}
    		
    		category = mostSimilar;
    		System.out.println("Most similar category: " + mostSimilar);
   
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
	}
	
	private double sumTfIdf(CustomBTree bTree)
	{
		double sum = 0;
		
		for(String word : words)
		{
			long ms = System.currentTimeMillis();
			Value[] vals = bTree.get(word);
			//System.out.println("Took " + (System.currentTimeMillis() - ms) + "ms to find the entries for " + word);
			
			if(vals == null)
				continue;
			
			for(Value v : vals)
			{
				if(v == null)
					continue;
				
				sum += v.getTfIdf();
				Integer numTimes = linkOccurences.get(v.getUrl());
				
				if(numTimes == null)
					numTimes = 1;
				else
					numTimes++;
				
				linkOccurences.put(v.getUrl(), numTimes);
			}
		}
		
		return sum;
	}
    
    private void addWords()
    {
		try
		{
    		System.out.println("Adding words from url: " + url);
    		
    		//parse body of web page with JSoup
    		String body = Utils.getWebPageBody(url);
    		
    		//split by spaces
    		String[] bodyParts = body != null ? body.split(" ") : null;
    		
    		if(bodyParts == null)
    		{
    			System.out.println("ERROR: COULD NOT PARSE FROM PRIMARY URL!");
    			return;
    		}
    			
    		for(String s : bodyParts)
    		{
    			if(s.length() == 0)
    				continue;
    			if(exclusions.contains(s))
    				continue;
    			
    			words.add(s);
    		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    
    public Category getCategory()
    {
    	return category;
    }
    
    public String[] getSuggestions()
    {
    	return suggestions;
    }
}
