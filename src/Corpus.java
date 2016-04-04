import java.util.ArrayList;


public class Corpus extends ArrayList<CustomUrl>
{
	private static final long serialVersionUID = -5811919797076472772L;
	
	private CustomUrl primaryUrl;
	
	public int getTotalDocsContainingTerm(String term)
	{
		int total = 0;
		
		for(CustomUrl url : this)
			if(url.getFreqTable().containsKey(term))
				total++;
		
		return total;
	}
	
	public CustomUrl getClosestRelated(CustomUrl primary)
	{
		CustomUrl closest = primary;
		double closestSimilarity = -150.0;
		
		for(CustomUrl url : this)
		{
			if(url.equals(primary))
				continue;
			
			double similarity = FrequencyTable.calculateAngle(primary.getFreqTable(), url.getFreqTable());
			
			if(similarity > closestSimilarity)
			{
				closestSimilarity = similarity;
				closest = url;
			}
		}
		
		return closest;
	}
	
	public void setPrimaryUrl(CustomUrl url)
	{
		primaryUrl = url;
	}
	
	public CustomUrl getPrimaryUrl()
	{
		return primaryUrl;
	}
}
