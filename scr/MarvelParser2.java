package hw7;
import java.io.*;
import java.util.*;

/**
 * Parser utility to load the Marvel Comics dataset.
 * 
 */
public class MarvelParser2 {
	
	/**
     * A checked exception class for bad data files
     */
	@SuppressWarnings("serial")
    public static class MalformedDataException extends Exception {
        public MalformedDataException() { }

        public MalformedDataException(String message) {
            super(message);
        }

        public MalformedDataException(Throwable cause) {
            super(cause);
        }

        public MalformedDataException(String message, Throwable cause) {
            super(message, cause);
        }
    }
	
	/**
	 * 
	 * Reads the Marvel Universe dataset.
     * Each line of the input file contains a character name and a comic
     * book the character appeared in, separated by a tab character
     * 
     * @requires file is well-formed, with each line containing exactly two
     *           quote-delimited tokens separated by a tab, or else starting with
     *           a # symbol to indicate a comment line.
     * @param filename the file that will be read
     * @param countChar map characters with other characters who appear in other books 
     *        together with the number of book in they appear together
     * @modifies countChar
     * @effects fills countChar with characters and map with other characters who appear 
     *          in other books together with the number of book in they appear together
	 * 
	 */
	public static void parseData(String filename, 
			Map<String, HashMap<String, Integer>> countChar) throws Exception {
		
		BufferedReader reader = null;
	    try {
			reader = new BufferedReader(new FileReader(filename));
			
			// a map to hold the book and its characters
			HashMap<String, ArrayList<String>> books = 
					new HashMap<String, ArrayList<String>>();
			
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				// Ignore comment lines.
		        if (inputLine.startsWith("#")) {
		        	continue;
		        }
		        
		        // Parse the data, stripping out quotation marks and throwing
		        // an exception for malformed lines.
		        inputLine = inputLine.replace("\"", "");
		        String[] tokens = inputLine.split("\t");
		        if (tokens.length != 2) {
		            throw new Exception("Line should contain exactly one tab: " + inputLine);
		        }

		        String character = tokens[0];
		        String book = tokens[1];
		        
		        // add characters to countChar is the character is not in map
		        if (!countChar.containsKey(character))
		        		countChar.put(character, new HashMap<String, Integer>());
		        
		        if (!books.containsKey(book)) {
			        	ArrayList<String> allCharacters = new ArrayList<String>();
			        	allCharacters.add(character);
			        	books.put(book, allCharacters);
		        } else {
			        	// if the book is already in books, update the 
			        	// edge count of this character and the character
			        	// in this book
			        	ArrayList<String> chars = books.get(book);
			        	HashMap<String, Integer> count1 = countChar.get(character);
			        	for (String singleChar : chars) {
			        		if (!count1.containsKey(singleChar))
			        			count1.put(singleChar, 1);
			        		else
			        			count1.put(singleChar, count1.get(singleChar) + 1);
			        		String secondChar = singleChar;
			        		HashMap<String, Integer> count2 = countChar.get(secondChar);
			        		if (!count2.containsKey(character))
			        			count2.put(character, 1);
			        		else
			        			count2.put(character, count2.get(character) + 1);
			        	}
			        	// add the character to the book
			        	chars.add(character);
			        	books.put(book, chars);
		        }
			}
	    } catch (IOException e) {
	        System.err.println(e.toString());
	        e.printStackTrace(System.err);
	    } finally {
	        if (reader != null) {
	        	reader.close();
	        }
	    }
	}
}
