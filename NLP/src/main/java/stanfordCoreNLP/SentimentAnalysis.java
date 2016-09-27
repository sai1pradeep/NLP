package stanfordCoreNLP;

import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

public class SentimentAnalysis {
	
	public static void main(String[] args){
		
		String[] statements = {"This Joseph is too good to be honest. But, the actual sense is in opposite sense",
				"Joseph is not so good",
				"Joseph is not good",
				"Joseph is good",
				"Joseph is not so bad",
				"Joseph is not bad",
				"Joseph is bad",
				"Joseph's home is not so distant",
				"This was not a great movie",
				"It should have been better",
				"This is too complex to understand",
				"He is good at nothing",
				"He is bad at nothing",
				"He is not good at anything",
				"He is not good at everything",
				"No smoking please",
				"This World is not a bad place",
				"This World is a bad place",
				"This World is not so bad place",
				"This World is not so good place",
				"This World is not a good place",
				"This World is good place",
				"This World is a very good place",
				"This world is an amazing place",
				"This world is a real bad place",
				"It was a great disaster",
				"I am feeling very sad and frustrated"
			};
		
		for (String eachString : statements) {
			findSentiment(eachString);
		}
		
		//String text = "This World is not a bad place";
	    
	}

	public static void findSentiment(String text) {
		// TODO Auto-generated method stub
		
		
		Properties props = new Properties();
	    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

	    Annotation annotation = pipeline.process(text);
	    List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
	    System.out.println("\n=============================================");
	    for (CoreMap sentence : sentences) {
	        String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
	        System.out.println(sentence+" ------ "+sentiment);
		}
	    System.out.println("=============================================\n");
	}
}
	
	
