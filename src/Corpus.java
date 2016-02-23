import java.util.ArrayList;


public class Corpus extends ArrayList<CustomUrl>
{
	private static final long serialVersionUID = -5811919797076472772L;
	
	public int getTotalDocsContainingTerm(String term)
	{
		int total = 0;
		
		for(CustomUrl url : this)
			if(url.getFreqTable().getRawFreqTable().containsKey(term))
				total++;
		
		return total;
	}
}
