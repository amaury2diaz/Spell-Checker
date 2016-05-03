package spellChecker;

/*	File Name: Paragraph Class
 *	Course Name: Data Structures CST 8130
 *	Lab	Section:CST 8130-301
 *	Student	Name: Amaury Diaz Diaz
 *	Date: Mar-25-2015
 */
/*
 * References:(Rex Woollard (2015) personal communication)
 */
/**
 * Contains the paragraph and its correspondig number which refers to its
 * position in the text file.
 * 
 * @author Amaury Diaz Diaz
 *
 */
public class Paragraph {
    /**
     * Paragraph in the text file
     */
	private String paragraph;
	/**
	 * Position of the paragraph in the text file 
	 */
	private int number;
    /**
     * Initializes the paragraph and its nummber to the parameters passed
     * @param paragraph
     * @param number
     */
	public Paragraph(String paragraph, int number) {
		this.paragraph = paragraph;
		this.number = number;
	}
    /**
     * Returns the formatted paragraph and its number.
     */
	@Override
	public String toString() {
		return String.format("%d - %s", number, paragraph);
	}// end of toString()
    /**
     * Accesor to the Paragraph field
     * @return A String which represents the paragraph
     */
	public String getParagraph() {
		return paragraph;
	}
    /**
     * Accesor to the number field
     * @return An int which represents the position of the paragraph in the text file.
     */
	public int getNumber() {
		return number;
	}

}
