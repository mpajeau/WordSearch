package net.pajeau;

import java.io.*;
import java.util.*;

public class WordSearch {

    // This field contains the list of words to be searched for in the current puzzle
    // as well as any search result from searching the grid for the word.
    private Map<String,String> mySearchWords;

    // This field contains the puzzle grid to be searched.
    private List<List<String>> myPuzzleGrid;

    // This method clears the current puzzle.
    public void ResetPuzzle()
    {
        mySearchWords = null;
        myPuzzleGrid = null;
    }

    // This method imports a puzzle from a passed-in Reader (e.g., string or file).  The puzzle
    // should contain a list of comma-separated search words on the first line.  Each search word
    // must be at least two letters long.  Each subsequent line of the file should contain 
    // comma-separated letters making up one row of the grid to be searched.  The grid must
    // be square.
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
            if ((aSearchWordList = aBufferedReader.readLine()) != null)
            {
                List<String> aSearchWordArray = new ArrayList<String>();
                mySearchWords = new LinkedHashMap<String, String>();
                aSearchWordArray = Arrays.asList(aSearchWordList.split(","));

                for (String aWord : aSearchWordArray)
                {
                    if (aWord.length() < 2)
                    {
                        System.out.println("Search words must be at least two characters long.");
                        throw new IOException("Invalid search word found.");
                    }
                    else
                    {
                        // Build an ordered map of the search words with a default search result.
                        mySearchWords.put(aWord, "NOT FOUND\n");
                    }
                }
            }
            else
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

    // This method will import and then solve a puzzle from the passed in reader.
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

        StringBuilder aPuzzleSolution = new StringBuilder();

        // Search through the grid for each search word.
        for (Map.Entry<String, String> aSearchWord : mySearchWords.entrySet())
        {
            for (int aRow = 0; aRow < myPuzzleGrid.size(); aRow++)
            {
                List<String> aGridRow = myPuzzleGrid.get(aRow);
                for (int aColumn = 0; aColumn < aGridRow.size(); aColumn++)
                {
                    // Get the current grid letter.
                    Character aGridLetter = myPuzzleGrid.get(aRow).get(aColumn).charAt(0);
    
                    // See if any search words start with the current grid letter.
                    if (aGridLetter == aSearchWord.getKey().charAt(0))
                    {
                        // The current grid letter matches the first letter of the current search word.
                        // Search the grid around the current letter to see if it's the start of the
                        // current search word.
                        boolean aFoundFlag = false;
                        StringBuilder aSearchResult = new StringBuilder();
                        if (searchForward(aRow, aColumn, aSearchWord.getKey(), aSearchResult))
                        {
                            aFoundFlag = true;
                        }

                        if (aFoundFlag)
                        {
                            // Found the search word!  Record its position in the list of search words.
                            mySearchWords.put(aSearchWord.getKey(), aSearchResult.toString());
                        }
                    }
                }
            }

            // Add the search word to the puzzle solution.
            aPuzzleSolution.append(aSearchWord.getKey() + ": " + aSearchWord.getValue());
        }

        return aPuzzleSolution.toString();
    }

    // This method searches forward from the given grid position for the given search word.
    private boolean searchForward(int theRow, int theColumn, String theSearchWord, StringBuilder theSearchResult)
    {
        // Run through the letters of the search word to see if it is in the grid
        // starting at the passed-in coordinates.
        boolean aFoundTheWordFlag = false;
        int aSearchWordLetterIndex = 0;
        int aGridLetterIndex = theColumn;
        List<String> aGridRow = myPuzzleGrid.get(theRow);
        while ((aSearchWordLetterIndex < theSearchWord.length()) &&
               (aGridLetterIndex < aGridRow.size()))
        {
            // Get the current grid letter.
            Character aSearchWordLetter = theSearchWord.charAt(aSearchWordLetterIndex);
            Character aGridLetter = aGridRow.get(aGridLetterIndex).charAt(0);
            if (aSearchWordLetter == aGridLetter)
            {
                // The next letter was found
                if (aSearchWordLetterIndex < (theSearchWord.length() - 1))
                {
                    theSearchResult.append("(" + aGridLetterIndex + "," + theRow + "),");
                }
                else
                {
                    // Found the last character, done searching for the word!
                    theSearchResult.append("(" + aGridLetterIndex + "," + theRow + ")");
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
            aGridLetterIndex++;
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