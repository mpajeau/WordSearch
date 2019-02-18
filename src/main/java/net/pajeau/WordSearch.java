package net.pajeau;

import java.io.*;

public class WordSearch {

    // This field contains the list of words to be searched for in the current puzzle.
    private String[] mySearchWords;

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
                mySearchWords = aSearchWordList.split(",");

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

        }
        catch (Exception anException)
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