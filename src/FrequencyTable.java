import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

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
	
	public static double calculateAngle(FrequencyTable one, FrequencyTable two)
	{
		double dotProduct = dotProduct(one, two);
		System.out.println("Dot product: " + dotProduct);
		double magnitudeOne = one.magnitude();
		System.out.println("Magnitude: " + magnitudeOne);
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
			System.out.println("Word: " + word);
			Double valOne = one.get(word);
			Double valTwo = two.get(word);
			System.out.println("valOne: " + valOne + ", valTwo: " + valTwo);
			
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
		System.out.println("Calculate TfIdf for word: " + word);
		System.out.println("Term freq: " + calculateTermFreq(word));
		System.out.println("Inverse doc freq: " + calculateInverseDocFreq(word));
		return calculateTermFreq(word) * calculateInverseDocFreq(word);
	}
	
	private double calculateInverseDocFreq(String word)
	{
		System.out.println("Corpus size: " + corpus.size());
		System.out.println("1 + docsContainingTerm: " + (1 + corpus.getTotalDocsContainingTerm(word)));
		return Math.log((double)corpus.size() / (1 + corpus.getTotalDocsContainingTerm(word)));
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
