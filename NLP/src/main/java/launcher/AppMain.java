package launcher;

import launcher.Dictionaries;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

public class AppMain {
	
	static ArrayList<String> taggedWords = new ArrayList<String>();
	static ArrayList<String> tagList = new ArrayList<String>();
	static ArrayList<String> stopWordsList = Dictionaries.stopWordsList;
	static ArrayList<String> positiveWordsList = Dictionaries.positiveWordsList;
	static ArrayList<String> negativeWordsList = Dictionaries.negativeWordsList;
	static ArrayList<String> inverseWordsList = Dictionaries.inverseWordsList;
	static ArrayList<String> strongWordsList = Dictionaries.strongWordsList;
	static ArrayList<String> weakWordsList = Dictionaries.weakWordsList;
	static Hashtable<String, Integer> wordScores = new Hashtable<String, Integer>();
	static int finalScore = 0;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String[] statements = {"This Joseph is too good to be honest. But, the actual sense is not like that.",
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
								"No smoking please"
							};
		int index = 0;
		for (String statement : statements) {
			taggedWords.clear();
			tagList.clear();
			wordScores.clear();
			finalScore = 0;
			System.out.println("=======================PROCESSING STATEMENT "+(index++)+" ============");
			detectSentences(statement);
			String[] tokenArray = tokenize(statement);
			tagPOS(tokenArray);
			removeStopWords();
			findNames(tokenArray);
			findScore();

			String sentiment = "neutral";
			if(finalScore > 0)
				sentiment = "positive";
			else if (finalScore < 0)
				sentiment = "negative";
			System.out.println("\n\n------------------------------");
			System.out.println(statement);
			System.out.println("Statement sentiment	: "+sentiment);
			System.out.println("------------------------------");
			System.out.println("\n===========================================================\n\n");
		}
	}
	

	private static void findScore() {
		// TODO Auto-generated method stub
		
		String prevWord = "null";
	
		for (String eachWord : taggedWords) {
				if(positiveWordsList.contains(eachWord))
					wordScores.put(eachWord, 1);
				else if(negativeWordsList.contains(eachWord))
					wordScores.put(eachWord, -1);
				else
					wordScores.put(eachWord, 0);
			}
		
		int index = 0;
		for (String eachWord : taggedWords) {
			if(!prevWord.equalsIgnoreCase("null")){
				if(strongWordsList.contains(prevWord)){
					int score = wordScores.get(eachWord);
					wordScores.remove(eachWord);
					wordScores.put(eachWord, score*2);
				}
				else if(weakWordsList.contains(prevWord)){
					int score = wordScores.get(eachWord);
					wordScores.remove(eachWord);
					wordScores.put(eachWord, score/2);
				}
				else if(inverseWordsList.contains(prevWord) && (inverseWordsList.contains(eachWord) || negativeWordsList.contains(eachWord))){
					wordScores.remove(eachWord);
					wordScores.put(eachWord, 0);
				}
				else if(inverseWordsList.contains(prevWord) && tagList.get(taggedWords.indexOf(eachWord)).equalsIgnoreCase("RB")){
					int score = wordScores.get(eachWord);
					wordScores.remove(eachWord);
					if(wordScores.get(taggedWords.get(index+1))>0)
						wordScores.put(eachWord, -2);
					else if(wordScores.get(taggedWords.get(index+1))<0)
						wordScores.put(eachWord, 2);
					else
						wordScores.put(eachWord, score);
				}
				else if(inverseWordsList.contains(prevWord) && !(inverseWordsList.contains(eachWord)) && !negativeWordsList.contains(eachWord)){
					int score = wordScores.get(eachWord);
					wordScores.remove(eachWord);
					wordScores.put(eachWord, score*-1);
				}
			}
			
			prevWord = eachWord;
			finalScore += wordScores.get(eachWord);
			index++;
		}
		
	}


	private static void removeStopWords() throws Exception {
		// TODO Auto-generated method stub
		
		Iterator<String> iter = taggedWords.iterator();
		while (iter.hasNext()) {
		    String eachTaggedWord = iter.next();
		    if (stopWordsList.contains(eachTaggedWord) || eachTaggedWord.length()==0){
		    	int index = taggedWords.indexOf(eachTaggedWord);
		    	iter.remove();
		    	tagList.remove(index);
		    }
		}
		System.out.println("\n\n---------Tagged words and their POS are ------------\n");
		System.out.println(taggedWords);
		System.out.println(tagList);
		System.out.println("\n----------------------------------------------------\n\n");
	} 


	private static void tagPOS(String[] tokenArray) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		InputStream modelInPOS = new FileInputStream("lib\\en-pos-maxent.bin");
		try {
			  POSModel model = new POSModel(modelInPOS);
			  POSTaggerME tagger = new POSTaggerME(model);		  
			  String tagArray[] = tagger.tag(tokenArray);
				for (int i = 0; i < tagArray.length; i++) {
					if(tagArray[i].startsWith("J")||tagArray[i].startsWith("V")||tagArray[i].startsWith("N")||tagArray[i].startsWith("R") || tagArray[i].startsWith("D")){
						taggedWords.add(tokenArray[i]);
						tagList.add(tagArray[i]);
					}
				}

			}
			catch (IOException e) {
			  // Model loading failed, handle the error
			  e.printStackTrace(); 
			}
		
		
	}

	private static void findNames(String[] tokenArray) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		InputStream modelInNames = new FileInputStream("lib\\en-ner-person.bin");
		try {
			TokenNameFinderModel model = new TokenNameFinderModel(modelInNames);
			NameFinderME nameFinder = new NameFinderME(model); 
			Span names[] = nameFinder.find(tokenArray);
			String[] nameArray = Span.spansToStrings(names, tokenArray);
			System.out.println("\n---------- Individual names are -----------------");
			for (String eachName : nameArray) {
				System.out.println(eachName);
			}
			System.out.println("------------------------------------------------------\n\n");
	}
	catch (IOException e) {
	  e.printStackTrace();
	}
		
	}

	private static void detectSentences(String statement) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		InputStream modelInSentences = new FileInputStream("lib\\en-sent.bin");

		try {
				SentenceModel sentenceModel = new SentenceModel(modelInSentences);
				SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);  
				String  strings[] = sentenceDetector.sentDetect(statement);
				System.out.println("\n\n---------- Individual statements are -----------------");
				for (String eachString : strings) {
					System.out.println(eachString);
				}
				System.out.println("------------------------------------------------------\n\n");
		}
		catch (IOException e) {
		  e.printStackTrace();
		}
		
	}
		

	public static String[] tokenize(String statement) throws FileNotFoundException{
		// TODO Auto-generated method stub
		
		String tokens[] = null;
		tokens = statement.split("[\\s+,?.$%^&*()#]");
		
		return tokens;
		
	}

}
