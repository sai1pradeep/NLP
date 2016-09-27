package launcher;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Dictionaries {
	
	static ArrayList<String> stopWordsList = new ArrayList<String>();
	static ArrayList<String> positiveWordsList = new ArrayList<String>();
	static ArrayList<String> negativeWordsList = new ArrayList<String>();
	static ArrayList<String> inverseWordsList = new ArrayList<String>();
	static ArrayList<String> strongWordsList = new ArrayList<String>();
	static ArrayList<String> weakWordsList = new ArrayList<String>();
	
	public Dictionaries() {
		// TODO Auto-generated constructor stub
		
	}
	
	public static void loadDictionaries(){
		
		loadStopWords();
		loadPositiveWords();
		loadNegativeWords();
		loadInverseWords();
		loadStrongWords();
		loadWeakWords();
	}

	public static void loadWeakWords() {
		// TODO Auto-generated method stub
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("Dictionary\\weak.txt"));
			String eachWord = "";
			
			while((eachWord=br.readLine())!=null){
				weakWordsList.add(eachWord);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public static void loadStrongWords() {
		// TODO Auto-generated method stub
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("Dictionary\\strong.txt"));
			String eachWord = "";
			
			while((eachWord=br.readLine())!=null){
				strongWordsList.add(eachWord);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public static void loadInverseWords() {
		// TODO Auto-generated method stub
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("Dictionary\\inverse.txt"));
			String eachWord = "";
			
			while((eachWord=br.readLine())!=null){
				inverseWordsList.add(eachWord);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public static void loadNegativeWords() {
		// TODO Auto-generated method stub
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("Dictionary\\negative.txt"));
			String eachWord = "";
			
			while((eachWord=br.readLine())!=null){
				negativeWordsList.add(eachWord);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public static void loadPositiveWords() {
		// TODO Auto-generated method stub
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("Dictionary\\positive.txt"));
			String eachWord = "";
			
			while((eachWord=br.readLine())!=null){
				positiveWordsList.add(eachWord);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public static void loadStopWords() {
		// TODO Auto-generated method stub
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("Dictionary\\stopWords.txt"));
			String eachWord = "";
			
			while((eachWord=br.readLine())!=null){
				stopWordsList.add(eachWord);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
	}

}
