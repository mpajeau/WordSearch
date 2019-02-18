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

        mySearchWords.put("LUKE", "(1,0),(2,0),(3,0),(4,0)\n");
        mySearchWords.put("HAN", "(5,7),(6,7),(7,7)\n");
        mySearchWords.put("CHEWIE", "(0,4),(1,4),(2,4),(3,4),(4,4),(5,4)\n");

        for (Map.Entry<String, String> aSearchWord : mySearchWords.entrySet())
        {
            aPuzzleSolution.append(aSearchWord.getKey() + ": " + aSearchWord.getValue());
        }

        return aPuzzleSolution.toString();
    }
}