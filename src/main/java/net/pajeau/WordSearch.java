package net.pajeau;

import java.io.*;
import java.util.*;

public class WordSearch {

    ////////////////////////////////////////////////
    // Constants

    // Horizontal search directions
    private static final int FORWARD = 1;
    private static final int NONE = 0;
    private static final int BACKWARD = -1;

    // Horizontal search directions
    private static final int DOWNWARD = 1;
    private static final int UPWARD = -1;

    ////////////////////////////////////////////////
    // Properties

    // This field contains the list of words to be searched for in the current puzzle
    // mapped to the result from searching the grid for the word.
    private Map<String,String> mySearchWords;

    // This field contains the puzzle grid to be searched.
    private List<List<String>> myPuzzleGrid;

    // Summary: This method clears the current puzzle's data out of internal fields.
    public void ResetPuzzle()
    {
        mySearchWords = null;
        myPuzzleGrid = null;
    }

    ////////////////////////////////////////////////
    // Methods

    // Summary: This method imports a puzzle from a passed-in Reader (e.g., string or file) and 
    // stores its data in internal fields.
    // Parameters:
    //      thePuzzleReader: a Reader (e.g., FileReader, StringReader) containing the puzzle data.
    // Note:
    //      The puzzle should contain a list of comma-separated search words on the first line.  
    //      Each search word must be at least two letters long.  Each subsequent line of the file 
    //      should contain comma-separated letters making up one row of the grid to be searched.  
    //      The grid must be square.
    public void importPuzzle(Reader thePuzzleReader) throws IOException
    {
        if (thePuzzleReader == null)
        {
            throw new IOException("No puzzle data was provided.");
        }

        BufferedReader aBufferedReader = null;
        try
        {
            aBufferedReader = new BufferedReader(thePuzzleReader);

            // The first line should contain the list of words to be searched for.
            String aSearchWordList;
            if ((aSearchWordList = aBufferedReader.readLine()) == null)
            {
                System.out.println("No search words were found in the puzzle file.");
                throw new IOException("Failed to read a list of search words.");
            }

            // Each subsequent line contains a row of the grid - read them all in.
            myPuzzleGrid = new ArrayList<List<String>>();
            String aLine;
            while ((aLine = aBufferedReader.readLine()) != null)
            {
                myPuzzleGrid.add(Arrays.asList(aLine.split(",")));
            }

            // Make sure the grid has enough rows in it to be valid.
            if (myPuzzleGrid.size() < 2)
            {
                // The minimum search word is 2 letters long so the grid must be at least two rows tall to be square.
                System.out.println("The grid has too few rows.");
                throw new IOException("Invalid puzzle grid - too few rows.");
            }

            // Make sure the grid is square.
            for (List<String> aRow : myPuzzleGrid)
            {
                // Each line should have  the same number of columns as there are rows.
                if (aRow.size() != myPuzzleGrid.size())
                {
                    System.out.println("The grid is not square.");
                    throw new IOException("Invalid puzzle grid - not square.");
                }
            }

            // Make sure the search words are at least 2 characters long but not longer than the grid is wide/tall.
            List<String> aSearchWordArray = new ArrayList<String>();
            mySearchWords = new LinkedHashMap<String, String>();
            aSearchWordArray = Arrays.asList(aSearchWordList.split(","));

            for (String aWord : aSearchWordArray)
            {
                if (aWord.length() < 2 || aWord.length() > myPuzzleGrid.size())
                {
                    System.out.println("Search words must be at least two characters long and fit within the grid.");
                    throw new IOException("Invalid search word found.");
                }
                else
                {
                    // Build an ordered map of the search words with a default search result.
                    mySearchWords.put(aWord, "NOT FOUND\n");
                }
            }

        }
        catch (IOException anException)
        {
            System.out.println("Caught an unknown exception while parsing the puzzle input.");
            throw anException;
        }
        finally
        {
            if (aBufferedReader != null)
            {
                aBufferedReader.close();
            }
        }
    }

    // Summary: This method will import and then solve a puzzle from the passed in reader.
    // Parameters:
    //      thePuzzleReader: a Reader (e.g., FileReader, StringReader) containing the puzzle data.
    // Returns:
    //      the solution for the puzzle - a list of the search words and, if the word was found a lit
    //      of the coordinates in the puzzle grid where the word was found
    public String solvePuzzle(Reader thePuzzleReader) throws IOException
    {
        try
        {
            importPuzzle(thePuzzleReader);
        }
        catch (IOException anException)
        {
            System.out.println("The puzzle could not be imported.");
            throw new IOException("An error occurred importing the puzzle.");
        }

        // Build a map of the first letters of all search words so we can quickly assess
        // whether we need to search for a word starting at any given letter in the grid.
        HashMap<Character, List<String>> aFirstLetterMap = new HashMap<Character, List<String>>();
        BuildFirstLetterHashMap(aFirstLetterMap);

        // Walk through the grid.  Check each letter to see if it's in our map of search words' first letters
        // and, if so, search around it for one of our search words.
        boolean anAllWordsFoundFlag = false;
        for (int aRow = 0; aRow < myPuzzleGrid.size() && !anAllWordsFoundFlag; aRow++)
        {
            List<String> aGridRow = myPuzzleGrid.get(aRow);
            for (int aColumn = 0; aColumn < aGridRow.size() && !anAllWordsFoundFlag; aColumn++)
            {
                // Get the current grid letter.
                Character aGridLetter = myPuzzleGrid.get(aRow).get(aColumn).charAt(0);

                // See if any search words start with the current grid letter.
                if (aFirstLetterMap.containsKey(aGridLetter))
                {
                    // There's at least one search word starting with the current grid letter.
                    // Run through the search words starting with this letter and search for each one.
                    Iterator<String> aWordListIterator = aFirstLetterMap.get(aGridLetter).iterator();

                    while (aWordListIterator.hasNext())
                    {
                        String aSearchWord = aWordListIterator.next();
                        boolean aFoundFlag = false;
                        StringBuilder aSearchResult = new StringBuilder();

                        // Search forward
                        if (search(aRow, aColumn, FORWARD, NONE, aSearchWord, aSearchResult))
                        {
                            aFoundFlag = true;
                        }
                        // Search downward
                        else if (search(aRow, aColumn, NONE, DOWNWARD, aSearchWord, aSearchResult))
                        {
                            aFoundFlag = true;
                        }
                        // Search diagonally, foward & downward
                        else if (search(aRow, aColumn, FORWARD, DOWNWARD, aSearchWord, aSearchResult))
                        {
                            aFoundFlag = true;
                        }
                        // Search diagonally, foward & upward
                        else if (search(aRow, aColumn, FORWARD, UPWARD, aSearchWord, aSearchResult))
                        {
                            aFoundFlag = true;
                        }
                        // Search upward
                        else if (search(aRow, aColumn, NONE, UPWARD, aSearchWord, aSearchResult))
                        {
                            aFoundFlag = true;
                        }
                        // Search diagonally, backward & upward
                        else if (search(aRow, aColumn, BACKWARD, UPWARD, aSearchWord, aSearchResult))
                        {
                            aFoundFlag = true;
                        }
                        // Search backward
                        else if (search(aRow, aColumn, BACKWARD, NONE, aSearchWord, aSearchResult))
                        {
                            aFoundFlag = true;
                        }
                        // Search diagonally, backward & downward
                        else if (search(aRow, aColumn, BACKWARD, DOWNWARD, aSearchWord, aSearchResult))
                        {
                            aFoundFlag = true;
                        }

                        if (aFoundFlag)
                        {
                            // Found the search word!                              
                            // Remove the found word from the map of search words.
                            aWordListIterator.remove();

                            // Record the search result for the found word.
                            mySearchWords.put(aSearchWord, aSearchResult.toString());
                        }
                    }

                    if (aFirstLetterMap.get(aGridLetter).isEmpty())
                    {
                        // All of the search words starting with the current grid letter have been found,
                        // remove the letter from the first letter map.
                        aFirstLetterMap.remove(aGridLetter);
                    }

                    if (aFirstLetterMap.isEmpty())
                    {
                        // All search words have been found, stop searching the grid!
                        anAllWordsFoundFlag = true;
                    }
                }
            }
        }

        // Build the solution string.
        StringBuilder aPuzzleSolution = new StringBuilder();

        Iterator<String> aSearchWordIterator = mySearchWords.keySet().iterator();
        while (aSearchWordIterator.hasNext())
        {
            String aSearchWord = aSearchWordIterator.next();
            aPuzzleSolution.append(aSearchWord + ": ");
            aPuzzleSolution.append(mySearchWords.get(aSearchWord));
        }

        return aPuzzleSolution.toString();
    }

    // Summary: This method builds a HashMap where the words to be searched for are sorted
    // into buckets by their first letter.
    // Parameters:
    //      theHashMap: the map to be filled with search word buckets
    private void BuildFirstLetterHashMap(HashMap<Character, List<String>> theHashMap)
    {
        Iterator<String> aSearchWordIterator = mySearchWords.keySet().iterator();

        theHashMap.clear();

        while (aSearchWordIterator.hasNext())
        {
            String aSearchWord = aSearchWordIterator.next();
            Character aFirstLetter = aSearchWord.charAt(0);
            if (theHashMap.containsKey(aFirstLetter))
            {
                // The first letter of this search word is already in the map, add this word to its list.
                theHashMap.get(aFirstLetter).add(aSearchWord);
            }
            else
            {
                // The first letter of this search word is NOT already in the map, add it.
                List<String> aWordList = new ArrayList<String>();
                aWordList.add(aSearchWord);
                theHashMap.put(aFirstLetter, aWordList);
            }
        }
    }

    // Summary: This method searches in the given horizontal and vertical directions starting from the given grid position
    // for the given search word.
    // Parameters:
    //      theRow: the grid row to start searching at
    //      theColumne: the grid column to start searching at
    //      theHorizontalDirection: the horizontal direction to search (1 = forward, 0 = none [search only vertically], -1 = backward)
    //      theVerticalDirection: the vertical direction to search (1 = downward, 0 = none [search only horizontally], -1 = upward)
    //      theSearchWord: the word to search for
    //      theSearchResult: a holder to return the search result
    // Returns:
    //      true if the word is found.  theSearchResult will contain the list of coordinates where the search word's letters were found in the grid.
    //      false if the word is not found.  theSearchResult will be empty.
    private boolean search(int theRow, int theColumn, int theHorizontalDirection, int theVerticalDirection, String theSearchWord, StringBuilder theSearchResult)
    {
        // Run through the letters of the search word to see if it is in the grid
        // starting at the passed-in coordinates.
        boolean aFoundTheWordFlag = false;
        int aSearchWordLetterIndex = 0;
        int aRowLength = myPuzzleGrid.get(theRow).size();
        int aGridRowIndex = theRow;
        int aGridColumnIndex = theColumn;

        // Check to see if the word being sought will fit in the grid starting at the given position.
        int aGridHeight = myPuzzleGrid.size();
        int aGridWidth = myPuzzleGrid.get(0).size();
        int aSearchWordLength = theSearchWord.length();

        if ((theHorizontalDirection == FORWARD && (aGridWidth - theColumn) < aSearchWordLength) ||
            (theHorizontalDirection == BACKWARD && (theColumn + 1) < aSearchWordLength) ||
            (theVerticalDirection == DOWNWARD && (aGridHeight - theRow) < aSearchWordLength) ||
            (theVerticalDirection == UPWARD && (theRow + 1) < aSearchWordLength))

        {
            // The starting point is too close to an edge of the grid for the given
            // word to fit.  
            theSearchResult.setLength(0);
            return false;
        }

        while ((aSearchWordLetterIndex < theSearchWord.length()) &&
               ((aGridColumnIndex >= 0) && (aGridColumnIndex < aRowLength)) &&
               ((aGridRowIndex >= 0) && (aGridRowIndex < myPuzzleGrid.size())))
        {
            // Get the current grid letter.
            List<String> aGridRow = myPuzzleGrid.get(aGridRowIndex);
            Character aGridLetter = aGridRow.get(aGridColumnIndex).charAt(0);

            Character aSearchWordLetter = theSearchWord.charAt(aSearchWordLetterIndex);

            if (aSearchWordLetter == aGridLetter)
            {
                // The next letter was found
                if (aSearchWordLetterIndex < (theSearchWord.length() - 1))
                {
                    theSearchResult.append("(" + aGridColumnIndex + "," + aGridRowIndex + "),");
                }
                else
                {
                    // Found the last character, done searching for the word!
                    theSearchResult.append("(" + aGridColumnIndex + "," + aGridRowIndex + ")");
                    aFoundTheWordFlag = true;
                    break;
                }
            }
            else
            {
                // The next grid letter does NOT match the next letter in the search word
                break;
            }

            // Move to the next letter in the search word and the grid.
            aSearchWordLetterIndex++;
            aGridColumnIndex += theHorizontalDirection;
            aGridRowIndex += theVerticalDirection;
        }

        if (aFoundTheWordFlag)
        {
            // Found the word, finish up the search result and return success!
            theSearchResult.append("\n");
            return true;
        }
        else
        {
            // Didn't find the word, clear the search result and return failure.
            theSearchResult.setLength(0);
            return false;
        }
    }  
}