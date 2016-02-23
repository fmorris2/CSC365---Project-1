import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class Utils
{
	public static String getWebPageBody(String url)
	{
		try
		{
			Document doc = Jsoup.connect(url).get();
			Element body = doc.body();
			return body == null ? null : body.text().replaceAll("[^a-zA-Z ]", "").toLowerCase();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
