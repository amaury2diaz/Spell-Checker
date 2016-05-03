package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import spellChecker.SpellChecker;
/**
 * It tests that the dictionary was loaded in the organized order and that
 * it does not contain any duplicate.
 * @author Amaury Diaz Diaz
 */
public class Test {
	 
    /**
     * Reference to as SpellCheck object
     */
	private SpellChecker spellCheck;
	/**
	 * It checks that the dictionary it's correctly loaded.
	 * @throws FileNotFoundException Exception
	 */
	@org.junit.Test
	public void dictionaryOrder() throws FileNotFoundException{
		spellCheck=new SpellChecker();
		String previous=null;
		for (String current : spellCheck.getDictionary().getDictionaryList()) {
		      if (previous == null) { // first iteration
		        previous = current;   // capture current as the previous
		        continue;             // skip the rest of the loop to get the next current . . . then you will have a legitimate previous and current
		      } // end case of first record where we capture previous
		      assertTrue( previous.toLowerCase().compareTo(current.toLowerCase()) < 0);
		      previous = current;
		    } // end for
	}//end of dictionaryOrder method

}
