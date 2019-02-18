package net.pajeau;

import java.io.*;
import java.util.*;

public class WordSearch {

    // This field contains the list of words to be searched for in the current puzzle.
    private List<String> mySearchWords;

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
                mySearchWords = Arrays.asList(aSearchWordList.split(","));

                for (String aWord : mySearchWords)
                {
                    if (aWord.length() < 2)
                    {
                        System.out.println("Search words must be at least two characters long.");
                        throw new IOException("Invalid search word found.");
                    }
                }
            }
            else
            {
                System.out.println("No search words were found in the puzzle file.");
                throw new IOException("Failed to read a list of search words.");
            }

            // Each subsequent line contains a row of the grid.
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
}