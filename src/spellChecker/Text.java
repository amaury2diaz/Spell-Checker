package spellChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*	File Name: Text Class
 *	Course Name: Data Structures CST 8130
 *	Lab	Section:CST 8130-301
 *	Student	Name: Amaury Diaz Diaz
 *	Date: Mar-25-2015
 */
/*
 * References:(Rex Woollard (2015) personal communication)
 */

/**
 * It is going to hold the paragraphs in the original order they were retrieved
 * from the original file.
 * 
 * @author Amaury Diaz Diaz
 *
 */
public class Text {
	/**
	 * Name of the file that contains the original document.
	 */
	private String fileName;
	/**
	 * File path of the file that contains the document.
	 */
	private File file;
	/**
	 * List use to store the sequence of paragraphs.
	 */
	private List<Paragraph> paragraphList;

	/**
	 * Initializes the fileName String to the value specified in the parameter.
	 * 
	 * @param fileName
	 *            Name of the file that contains the original document
	 */
	public Text(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Initializes the file to the path specified in the parameter.
	 * 
	 * @param file
	 *            File path of the file that contains the document.
	 */
	public Text(File file) {
		this.file = file;
	}
	
	/**
	 * Load the paragraphs in their original order to an ArrayList if the 
	 * a reference to a String object is being used to refer to the file.
	 * @throws FileNotFoundException Exception
	 */
	public void loadParagraphsFromFileName() throws FileNotFoundException {
		long startTime = System.currentTimeMillis();

		try (Scanner input = new Scanner(new File(fileName))) {
			String inputLine;
			int counter=0;
			paragraphList = new ArrayList<Paragraph>();// creates a LinkedList to hold the paragraphs																				
			while (input.hasNext()) {
				inputLine=input.nextLine();
				counter++;
				Paragraph paragraph = new Paragraph(inputLine,counter);
				paragraphList.add(paragraph);// add to the LinkedList
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		double elapseTime = (double) (System.currentTimeMillis() - startTime) / 1000.0;
		System.out.printf(
				"Paragraph.loadParagraphsFromFileName() Word Count:%d Elapse Time:%.3f seconds\n",
				paragraphList.size(), elapseTime);
	}
	
	/**
	 * Loads the paragraphs in their original order to an ArrayList if the 
	 * a reference to the file path is being used to refer to the file.
	 * @throws FileNotFoundException Exception
	 */

	public void loadParagraphsFromFile() throws FileNotFoundException {
		long startTime = System.currentTimeMillis();
		try (Scanner input = new Scanner(file)) {
			String inputLine;
			int counter=0;
			paragraphList = new ArrayList<Paragraph>();
			while (input.hasNext()) {
				inputLine= input.nextLine();
				counter++;
				Paragraph paragraph = new Paragraph(inputLine,counter);
				paragraphList.add(paragraph);// add to the LinkedList
			}
		} catch (FileNotFoundException e) {
			System.out.println("No document selected\n");
		}
		double elapseTime = (double) (System.currentTimeMillis() - startTime) / 1000.0;
		System.out.printf(
				"Paragraph.loadParagraphsFromFile() Word Count:%d Elapse Time:%.3f seconds\n",
				paragraphList.size(), elapseTime);
	}

	/**
	 * Accesor to the paragraph list.
	 * @return The List of  paragraphs.
	 */
	public List<Paragraph> getParagraphList() {
		return paragraphList;
	}
}
