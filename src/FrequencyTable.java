import java.util.HashSet;
import java.util.Set;

import data_structures.CustomHashTable;

/*
 * Each URL given by the user will have an instance of this Object assigned to it.
 * 
 * This "FrequencyTable" is essentially a table of the TF-IDF values for each word,
 * hence the String being the key (representing the word), and a Double being the value
 * (representing the TF-IDF value)
 */
public class FrequencyTable extends CustomHashTable<String, Double>
{
	private int maxRawFrequency;
	private Corpus corpus;
	private CustomHashTable<String, Integer> rawFreqTable;
	
	public FrequencyTable(Corpus corpus)
	{
		this.corpus = corpus;
		rawFreqTable = new CustomHashTable<>();
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
	
	public static double calculateAngle(FrequencyTable one, FrequencyTable two)
	{
		double dotProduct = dotProduct(one, two);
		double magnitudeOne = one.magnitude();
		double magnitudeTwo = two.magnitude();
		
		return dotProduct / (magnitudeOne * magnitudeTwo);
	}
	
	private static double dotProduct(FrequencyTable one, FrequencyTable two)
	{
		double sum = 0;
		
		//merge key sets
		Set<String> mergedKeySet = new HashSet<>();
		mergedKeySet.addAll(one.keySet());
		mergedKeySet.addAll(two.keySet());
		
		//iterate through words
		for(String word : mergedKeySet)
		{
			Double valOne = one.get(word);
			Double valTwo = two.get(word);
			
			sum += (valOne == null ? 0 : valOne) * (valTwo == null ? 0 : valTwo);
		}
		
		return sum;
	}
	
	public double magnitude()
	{
		return Math.sqrt(dotProduct(this, this));
	}
	
	private double calculateTfIdf(String word)
	{
		return calculateTermFreq(word) * calculateInverseDocFreq(word);
	}
	
	private double calculateInverseDocFreq(String word)
	{
		return Math.log((double)corpus.size() / (1 + corpus.getTotalDocsContainingTerm(word)));
	}
	
	private double calculateTermFreq(String word)
	{
		int rawFreq = rawFreqTable.get(word);
		
		if(rawFreq > maxRawFrequency)
			maxRawFrequency = rawFreq;
		
		return 0.5 + (0.5 * (rawFreq / maxRawFrequency));
	}
	
	public CustomHashTable<String, Integer> getRawFreqTable()
	{
		return rawFreqTable;
	}
}
