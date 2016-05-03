package spellChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*	File Name: SpellChecker Class
 *	Course Name: Data Structures CST 8130
 *	Lab	Section:CST 8130-301
 *	Student	Name: Amaury Diaz Diaz
 *	Date: Mar-25-2015
 */
/*
 * References:(Rex Woollard (2015) personal communication)
 */

/**
 * It runs the spell checks the words contained in the list of paragraphs
 * of a document by comparing those words to the dictionary stored in
 * the Dictionary class. It also finds the concordance of the words in the
 * paragraphs.
 * @author Amaury Diaz Diaz
 *
 */
public class SpellChecker {

	/**
	 * Reference to a Dictionary object.
	 */
	private Dictionary dict;
	/**
	 * Reference to a Paragraph object.
	 */
	private Text text;
	/**
	 * Map used to store the spelling errors.
	 */
	private Map<String,LinkedHashSet<Paragraph>> errorsMap;
	/**
	 * Map used to store the concordance
	 */
	private Map<String,LinkedHashSet<Paragraph>> concordanceMap;

	/**
	 * Initializes and loads the dictionary and the list of paragraphs
	 * @throws FileNotFoundException Exception
	 */
	public SpellChecker() throws FileNotFoundException {

		dict = new Dictionary("Main.txt");
		dict.fillDictionary();

		text = new Text("Oliver.txt");
		text.loadParagraphsFromFileName();

	}

	/**
	 * Initializes and loads the dictionary. It initializes the Paragraph
	 * to the file specified in the parameter.
	 * @param file path of the file to spell check.
	 * @throws FileNotFoundException Exception
	 */
	public SpellChecker(File file) throws FileNotFoundException {
		dict = new Dictionary("Main.txt");
		dict.fillDictionary();

		text = new Text(file);
		text.loadParagraphsFromFile();

	}

	/**
	 * Parses each word in the paragraph and compare them to the words in the dictionary.
	 * If the word is not found in the dictionary it is added to a TreeMap as a key and 
	 * its value(the paragraph where the word resides) is stored in a LinkedHashSet.
	 */
	public void spellCheckIt() {
		long startTime = System.currentTimeMillis();
		errorsMap = new TreeMap<String,LinkedHashSet<Paragraph>>(String.CASE_INSENSITIVE_ORDER);//store the grammar errors in a TreeMap
		for (Paragraph paragraphToCheck : text.getParagraphList()) {
			String delimeter = "[^a-zA-Z']+";//delimeter used to split the words.
			String[] fields = paragraphToCheck.getParagraph().split(delimeter);
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].length() > 0 && !dict.find(fields[i])) { // if find returns false add the word
					                                                   //as the key of misspelled words
					if (errorsMap.get(fields[i]) == null){ //if the value is null
						errorsMap.put(fields[i].toLowerCase(), new LinkedHashSet<>()); //create a new LinkedHashSet
						errorsMap.get(fields[i]).add(paragraphToCheck); //put the key and LinkedHashSet in the Map
					} else {
						errorsMap.get(fields[i]).add(paragraphToCheck); //else add the paragraph to the existing LinkedHashSet
					}
				}
			}
		}
		
		double elapseTime = (double) (System.currentTimeMillis() - startTime) / 1000.0;
		System.out.printf(
				"spellCheckIt() Word Count:%d Elapse Time:%.3f seconds\n\n",
				errorsMap.size(), elapseTime);
	}//end of spellCheckIt method
	/**
	 * Parses each word in the paragraph .If the word is bigger than 0(to avoid blank spaces)it 
	 * is added to a TreeMap as a key and its value(The paragraph where the word resides)
	 *  is stored in a LinkedHashSet.
	 */
	public void concordance(){
		long startTime = System.currentTimeMillis();
		concordanceMap= new TreeMap<String,LinkedHashSet<Paragraph>>(String.CASE_INSENSITIVE_ORDER);
		for(Paragraph paragraphToCheck : text.getParagraphList()){
			String delimeter ="[^a-zA-Z']+";//delimeter used to split the words.
			String[] fields = paragraphToCheck.getParagraph().split(delimeter);
			for(int i=0;i<fields.length;i++){
				if(fields[i].length()>0){// if find returns true add the word
                                         //as the key of concordance words
				
				if( concordanceMap.get(fields[i])==null){ //if the value is null
					concordanceMap.put(fields[i].toLowerCase(),new LinkedHashSet<>());//create a new LinkedHashSet
					concordanceMap.get(fields[i]).add(paragraphToCheck);//put the key and LinkedHashSet in the Map
				}else{
					 concordanceMap.get(fields[i]).add(paragraphToCheck);//else add the paragraph to the existing LinkedHashSet
				}
				}
			}
		}
		double elapseTime = (double)(System.currentTimeMillis()-startTime)/1000.0;
		System.out.printf("concordance() Word Count: %d Elapse Time: %.3f seconds\n\n",concordanceMap.size(),elapseTime);
	}
	/**
	 * Accesor to the errorsMap
	 * @return Map of spelling errors.
	 */
	public Map<String, LinkedHashSet<Paragraph>> getGrammarErrorsMap() { return errorsMap; }
	/**
	 * Accesor to the concordanceMap
	 * @return Map of concordance
	 */
	public Map<String, LinkedHashSet<Paragraph>> getConcordanceMap(){return concordanceMap;}
	/**
	 * Accesor to the errorsMap size 
	 * @return the size of the errosMap
	 */
	public int getGrammarErrorsSize(){
		return errorsMap.size();
	}
	/**
	 * Accesor to the concordanceMap size 
	 * @return the size of the errosMap
	 */
	public int getConcordanceSize(){
		return concordanceMap.size();
	}
    /**
     * Accesor to a Paragraph object.
     * @return Paragraph object
     */
	public Text getParagraphs() {
		return text;
	}//end of getParagraphs method
    /**
     * Accesor to a Dictionary object.
     * @return Dictionary object
     */
	public Dictionary getDictionary() {
		return dict;
	}//end of getDictionary method

}//end of SpellChecker Class
