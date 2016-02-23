import java.util.Hashtable;

/*
 * Each URL given by the user will have an instance of this Object assigned to it.
 * 
 * This "FrequencyTable" is essentially a table of the TF-IDF values for each word,
 * hence the String being the key (representing the word), and a Double being the value
 * (representing the TF-IDF value)
 */
public class FrequencyTable extends Hashtable<String, Double>
{
	private static final long serialVersionUID = -9097372443547729620L;
	
	private int maxRawFrequency;
	private Corpus corpus;
	private Hashtable<String, Integer> rawFreqTable;
	
	public FrequencyTable(Corpus corpus)
	{
		this.corpus = corpus;
		rawFreqTable = new Hashtable<>();
	}
	
	public void addWord(String word)
	{
		Integer currentCount = rawFreqTable.get(word);
		rawFreqTable.put(word, currentCount == null ? 1 : currentCount + 1);
	}
	
	public void calculate()
	{
		for(String key : rawFreqTable.keySet())
			this.put(key, calculateTfIdf(key));
	}
	
	private double calculateTfIdf(String word)
	{
		return calculateTermFreq(word) * calculateInverseDocFreq(word);
	}
	
	private double calculateInverseDocFreq(String word)
	{
		return Math.log(corpus.size() / (1 + corpus.getTotalDocsContainingTerm(word)));
	}
	
	private double calculateTermFreq(String word)
	{
		int rawFreq = rawFreqTable.get(word);
		
		if(rawFreq > maxRawFrequency)
			maxRawFrequency = rawFreq;
		
		return 0.5 + (0.5 * (rawFreq / maxRawFrequency));
	}
	
	public Hashtable<String, Integer> getRawFreqTable()
	{
		return rawFreqTable;
	}
}
