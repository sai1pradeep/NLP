package stanfordCoreNLP;

import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class POSTagger {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String text = "He got pleaded with his attempt";
		findPOSTags(text);

	}

	public static void findPOSTags(String text) {
		// TODO Auto-generated method stub
		
		Properties props = new Properties();
	    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

	    Annotation annotation = pipeline.process(text);
	    List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
	    for (CoreMap sentence : sentences) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				String pos = token.get(PartOfSpeechAnnotation.class);
				System.out.println(token+" --> "+pos);
			}
		}
		
	    for (CoreMap sentence : sentences) {
			Tree tree = sentence.get(TreeAnnotation.class);
			System.out.println(tree);
		}
	}

}
