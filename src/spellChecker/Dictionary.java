package spellChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*	File Name: Dictionary Class
 *	Course Name: Data Structures CST 8130
 *	Lab	Section:CST 8130-301
 *	Student	Name: Amaury Diaz Diaz
 *	Date: Mar-25-2015
 */
/*
 * References:(Rex Woollard (2015) personal communication)
 */

/**
 * Creates the dictionary that is going to be used by the SpellChecker class. It
 * takes a reference to a String as an argument which represents the name of the
 * file that originally contains the dictionary. Then it takes each word in the
 * file and stores it in a TreeSet to avoid duplicates and retrieve the
 * dictionary list in alphabetical order.
 * 
 * @author Amaury Diaz Diaz
 *
 */
public class Dictionary {
	/**
	 * Name of the file that contains the original dictionary.
	 */
	private String fileName;
	/**
	 * List used to store the dictionary.
	 */
	private List<String> dictionaryList;

	/**
	 * Initializes the fileName String to the value specified in the parameter.
	 * 
	 * @param fileName
	 *            Name of the original file that contains the dictionary
	 */
	public Dictionary(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Fills the dictionary with the words contained in the original file.
	 * Stores them in a TreeSet to order it alphabetically and to avoid 
	 * duplicates. Then it passes the TreeSet to an ArrayList to make it
	 * observable. 
	 * @throws FileNotFoundException Exception
	 */
	public void fillDictionary() throws FileNotFoundException {
		long startTime = System.currentTimeMillis();
		Collection<String> dictionaryWords = new TreeSet<>(
				String.CASE_INSENSITIVE_ORDER);//Create a Treeset to sore the dictionary
		try (Scanner input = new Scanner(new File(fileName))) {
			while (input.hasNext()) {
				String word = input.nextLine();
				dictionaryWords.add(word);//add each word to the TreeSet
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		dictionaryList = new ArrayList<String>(
				dictionaryWords);//pass the TreeSet as an ArrayList to make it observable.
		double elapseTime = (double) (System.currentTimeMillis() - startTime) / 1000.0;
		System.out
				.printf("Dictionary.fillDictionary() Word Count:%d Elapse Time:%.3f seconds\n",
						dictionaryList.size(), elapseTime);

	}

	/**
	 * Accesor to the dictionary list
	 * @return The ObservableList of the dictionary.
	 */
	public List<String> getDictionaryList() {
		return dictionaryList;
	}

	/**
	 * It takes a reference to a String object as a parameter which is going to be searched
	 * in the dictionary list.
	 * @param word A reference to a String which represents the word to be found in the dictionary list
	 * @return True if the word is in the dictionary, false otherwise.
	 */
	public boolean find(String word) {
		return Collections.binarySearch(dictionaryList, word,
				String.CASE_INSENSITIVE_ORDER) >= 0;
	}
}
