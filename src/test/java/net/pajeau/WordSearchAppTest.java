/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package net.pajeau;

import net.pajeau.WordSearch;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.*;

public class WordSearchAppTest {
    private static WordSearch myWordSearch;

    @BeforeClass public static void setupTestSession()
    {
        myWordSearch = new WordSearch();
    }

    @Before public void setupOneTest()
    {
        myWordSearch.ResetPuzzle();
    }

    @Test public void wordSearchThrowsIOExceptionIfPuzzleDataIsEmpty() {
        IOException anException = null;

        String anEmptyPuzzle = "";
        StringReader aStringReader = new StringReader(anEmptyPuzzle);

        try
        {
            myWordSearch.importPuzzle(aStringReader);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            anException = e;
        }

        aStringReader.close();

        assertNotNull("No IOException was thrown!", anException);
    }

    @Test public void wordSearchThrowsIOExceptionIfSearchWordsAreShorterThanTwoCharacters() {
        IOException anException = null;

        String anInvalidSearchWordList = "AB,C,DE,FG,HI";
        StringReader aStringReader = new StringReader(anInvalidSearchWordList);

        try
        {
            myWordSearch.importPuzzle(aStringReader);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            anException = e;
        }

        aStringReader.close();

        assertNotNull("No IOException was thrown!", anException);
    }

    @Test public void wordSearchThrowsIOExceptionIfNoSearchWordsAreSubmitted() {
        IOException anException = null;

        String anEmptySearchWordList = 
            "\n" + 
            "U,M,K,H,U,L,K,I,N,V,J,O,C,W,E\n";
        StringReader aStringReader = new StringReader(anEmptySearchWordList);

        try
        {
            myWordSearch.importPuzzle(aStringReader);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            anException = e;
        }

        aStringReader.close();

        assertNotNull("No IOException was thrown!", anException);
    }

    @Test public void wordSearchThrowsIOExceptionIfPuzzleGridHasFewerThanTwoLines() {
        IOException anException = null;

        String anInvalidPuzzleGrid = 
            "BONES,KHAN,KIRK,SCOTTY,SPOCK,SULU,UHURA,CHEKOV\n" + 
            "U,M,K,H,U,L,K,I,N,V,J,O,C,W,E\n";

        StringReader aStringReader = new StringReader(anInvalidPuzzleGrid);

        try
        {
            myWordSearch.importPuzzle(aStringReader);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            anException = e;
        }

        aStringReader.close();

        assertNotNull("No IOException was thrown!", anException);
    }

    @Test public void wordSearchThrowsIOExceptionIfPuzzleGridHasTooFewRows() {
        IOException anException = null;

        String anInvalidPuzzleGrid = 
            "BONES,KHAN,KIRK,SCOTTY,SPOCK,SULU,UHURA,CHEKOV\n" + 
            "U,M,K,H,U,L,K,I,N,V,J,O,C,W,E\n" +
            "L,L,S,H,K,Z,Z,W,Z,C,G,J,U,Y,G\n" + 
            "H,S,U,P,J,P,R,J,D,H,S,B,X,T,G\n" +
            "K,Y,L,B,Q,Q,P,M,D,F,C,K,E,A,B\n";
            
        StringReader aStringReader = new StringReader(anInvalidPuzzleGrid);

        try
        {
            myWordSearch.importPuzzle(aStringReader);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            anException = e;
        }

        aStringReader.close();

        assertNotNull("No IOException was thrown!", anException);
    }

    @Test public void wordSearchThrowsIOExceptionIfPuzzleGridIsJagged() {
        IOException anException = null;

        String anInvalidPuzzleGrid = 
            "BONES,KHAN,KIRK,SCOTTY,SPOCK,SULU,UHURA,CHEKOV\n" + 
            "U,M,K,H,U,L,K,I,N,V,J,O,C,W,E\n" +
            "L,L,S,H,K,Z,Z,W,Z,C,G,J,U,Y,G\n" + 
            "H,S,U,P,J,P,R,J,D,H,S,B,X,T,G\n" +
            "B,R,J,S,O,E,Q,E,T,I,K,K,G,L,E\n" +
            "A,Y,O,A,G,C,I,R,D,Q,H,R,T,C,D\n" +
            "S,C,O,T,T,Y,K,Z,R,E,P,P,X,P,F\n" +
            "B,L,Q,S,L,N,E,E,E,V,U,L,F,M,Z\n" +
            "O,K,R,I,K,A,M,M,R,M,F,B,A,V,P\n" +
            "N,U,I,I,Y,H,Q,M,E,M,Q,R,O,F\n" +
            "E,Y,Z,Y,G,K,Q,J,P,C,Q,K,Y,A,K\n" +
            "S,J,F,Z,M,Q,I,B,D,B,E,M,K,W,D\n" +
            "T,G,L,B,H,C,B,E,C,H,T,O,Y,I,K\n" +
            "O,J,Y,E,U,L,N,C,C,L,Y,B,Z,U,H\n" +
            "W,Z,M,I,S,U,K,U,R,B,I,D,U,X,S\n" +
            "K,Y,L,B,Q,Q,P,M,D,F,C,K,E,A,B\n";
            
        StringReader aStringReader = new StringReader(anInvalidPuzzleGrid);

        try
        {
            myWordSearch.importPuzzle(aStringReader);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            anException = e;
        }

        aStringReader.close();

        assertNotNull("No IOException was thrown!", anException);
    }

    // NOTE: this test requires the "ModifiedStarTrekWordSearch.txt" file
    // to exist where App.java can find it!
    @Test public void wordSearchCanImportAPuzzleFromATextFile() {
        Exception anException = null;

        try
        {
            String[] anArgumentList = {"ModifiedStarTrekWordSearch.txt"};
            WordSearchApp.main(anArgumentList);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            anException = e;
        }

        assertNull("An exception was thrown!", anException);
    }

    @Test public void wordSearchCanFindForwardHorizontalWords() {
        String aPuzzleGrid = 
            "LUKE,HAN,CHEWIE\n" + 
            "L,L,U,K,E,Z,Z,W\n" + 
            "H,S,U,P,J,P,R,J\n" +
            "B,R,J,S,O,E,Q,E\n" +
            "A,Y,O,A,G,C,I,R\n" +
            "C,H,E,W,I,E,K,Z\n" +
            "B,L,Q,S,L,N,E,E\n" +
            "O,K,R,I,K,A,M,M\n" +
            "K,Y,L,B,Q,H,A,N\n";
            
        StringReader aStringReader = new StringReader(aPuzzleGrid);
        String aPuzzleSolution = "";

        try
        {
             aPuzzleSolution = myWordSearch.solvePuzzle(aStringReader);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        aStringReader.close();

        String anExpectedResult = 
            "LUKE: (1,0),(2,0),(3,0),(4,0)\n" + 
            "HAN: (5,7),(6,7),(7,7)\n" + 
            "CHEWIE: (0,4),(1,4),(2,4),(3,4),(4,4),(5,4)\n";

        assertEquals(anExpectedResult, aPuzzleSolution);
    }

    @Test public void wordSearchCanFindDownwardVerticalWords() {
        String aPuzzleGrid = 
            "ROBB,EDDARD,JON\n" + 
            "L,J,U,K,E,Z,Z,W\n" + 
            "H,O,U,P,J,P,R,J\n" +
            "B,N,J,R,O,E,Q,E\n" +
            "A,Y,O,O,G,D,I,R\n" +
            "X,P,E,B,I,D,K,Z\n" +
            "B,L,Q,B,L,A,E,E\n" +
            "O,K,R,I,K,R,M,M\n" +
            "K,Y,L,B,Q,D,A,N\n";
            
        StringReader aStringReader = new StringReader(aPuzzleGrid);
        String aPuzzleSolution = "";

        try
        {
             aPuzzleSolution = myWordSearch.solvePuzzle(aStringReader);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        aStringReader.close();

        String anExpectedResult = 
            "ROBB: (3,2),(3,3),(3,4),(3,5)\n" + 
            "EDDARD: (5,2),(5,3),(5,4),(5,5),(5,6),(5,7)\n" + 
            "JON: (1,0),(1,1),(1,2)\n";

        assertEquals(anExpectedResult, aPuzzleSolution);
    }

    @Test public void wordSearchCanFindForwardAndDownwardDiagonalWords() {
        String aPuzzleGrid = 
            "MULDER,SCULLY,CSM\n" + 
            "L,M,U,K,E,Z,Z,W\n" + 
            "H,O,S,P,J,P,R,J\n" +
            "B,M,J,C,O,E,Q,E\n" +
            "A,Y,U,O,U,D,I,R\n" +
            "X,P,E,L,I,L,K,Z\n" +
            "B,C,Q,B,D,A,L,E\n" +
            "O,K,S,I,K,E,M,Y\n" +
            "K,Y,L,M,Q,D,R,N\n";
            
        StringReader aStringReader = new StringReader(aPuzzleGrid);
        String aPuzzleSolution = "";

        try
        {
             aPuzzleSolution = myWordSearch.solvePuzzle(aStringReader);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        aStringReader.close();

        String anExpectedResult = 
            "MULDER: (1,2),(2,3),(3,4),(4,5),(5,6),(6,7)\n" + 
            "SCULLY: (2,1),(3,2),(4,3),(5,4),(6,5),(7,6)\n" + 
            "CSM: (1,5),(2,6),(3,7)\n";

        assertEquals(anExpectedResult, aPuzzleSolution);
    }

    @Test public void wordSearchCanFindForwardAndUpwardDiagonalWords() {
        String aPuzzleGrid = 
            "DEAN,SAM,CASTIEL\n" + 
            "L,M,U,K,N,Z,Z,W\n" + 
            "H,O,S,A,J,P,R,L\n" +
            "B,L,E,S,O,E,E,E\n" +
            "A,D,U,O,U,I,I,R\n" +
            "X,P,E,Q,T,R,K,Z\n" +
            "B,C,Q,S,D,A,L,M\n" +
            "O,K,A,I,K,G,A,Y\n" +
            "K,C,L,M,Q,S,R,N\n";
            
        StringReader aStringReader = new StringReader(aPuzzleGrid);
        String aPuzzleSolution = "";

        try
        {
             aPuzzleSolution = myWordSearch.solvePuzzle(aStringReader);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        aStringReader.close();

        String anExpectedResult = 
            "DEAN: (1,3),(2,2),(3,1),(4,0)\n" + 
            "SAM: (5,7),(6,6),(7,5)\n" + 
            "CASTIEL: (1,7),(2,6),(3,5),(4,4),(5,3),(6,2),(7,1)\n";

        assertEquals(anExpectedResult, aPuzzleSolution);
    }

    @Test public void wordSearchCanFindUpwardVerticalWords() {
        String aPuzzleGrid = 
            "BUCK,WILMA,TWIKKI\n" + 
            "A,M,U,K,N,Z,Z,W\n" + 
            "M,O,S,R,J,P,R,L\n" +
            "L,L,E,K,O,I,E,E\n" +
            "I,M,U,C,U,K,I,R\n" +
            "W,P,E,U,T,K,K,Z\n" +
            "B,C,Q,B,D,I,L,M\n" +
            "O,K,A,I,K,W,A,Y\n" +
            "K,C,L,M,Q,T,R,N\n";
            
        StringReader aStringReader = new StringReader(aPuzzleGrid);
        String aPuzzleSolution = "";

        try
        {
             aPuzzleSolution = myWordSearch.solvePuzzle(aStringReader);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        aStringReader.close();

        String anExpectedResult = 
            "BUCK: (3,5),(3,4),(3,3),(3,2)\n" + 
            "WILMA: (0,4),(0,3),(0,2),(0,1),(0,0)\n" + 
            "TWIKKI: (5,7),(5,6),(5,5),(5,4),(5,3),(5,2)\n";

        assertEquals(anExpectedResult, aPuzzleSolution);
    }

    @Test public void wordSearchCanFindBackwardAndUpwardDiagonalWords() {
        String aPuzzleGrid = 
            "FRODO,GANDALF,SAM\n" + 
            "F,M,U,K,N,Z,Z,W\n" + 
            "M,L,S,R,M,P,R,L\n" +
            "T,L,A,K,O,A,E,E\n" +
            "O,M,U,D,U,K,S,R\n" +
            "A,D,E,U,N,K,K,Z\n" +
            "B,C,O,B,D,A,L,M\n" +
            "O,K,A,R,K,W,G,Y\n" +
            "K,C,L,M,F,T,R,N\n";
            
        StringReader aStringReader = new StringReader(aPuzzleGrid);
        String aPuzzleSolution = "";

        try
        {
             aPuzzleSolution = myWordSearch.solvePuzzle(aStringReader);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        aStringReader.close();

        String anExpectedResult = 
            "FRODO: (4,7),(3,6),(2,5),(1,4),(0,3)\n" + 
            "GANDALF: (6,6),(5,5),(4,4),(3,3),(2,2),(1,1),(0,0)\n" + 
            "SAM: (6,3),(5,2),(4,1)\n";

        assertEquals(anExpectedResult, aPuzzleSolution);
    }

    @Test public void wordSearchCanFindBackwardHorizontalWords() {
        String aPuzzleGrid = 
            "CLARK,DIANA,BRUCE\n" + 
            "L,M,U,K,N,Z,Z,W\n" + 
            "M,O,E,C,U,R,B,L\n" +
            "T,L,A,K,O,A,E,E\n" +
            "O,M,U,D,U,K,S,R\n" +
            "A,N,A,I,D,K,K,Z\n" +
            "B,C,O,B,D,A,L,M\n" +
            "O,K,A,R,K,W,G,Y\n" +
            "K,R,A,L,C,T,R,N\n";
            
        StringReader aStringReader = new StringReader(aPuzzleGrid);
        String aPuzzleSolution = "";

        try
        {
             aPuzzleSolution = myWordSearch.solvePuzzle(aStringReader);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        aStringReader.close();

        String anExpectedResult = 
            "CLARK: (4,7),(3,7),(2,7),(1,7),(0,7)\n" + 
            "DIANA: (4,4),(3,4),(2,4),(1,4),(0,4)\n" + 
            "BRUCE: (6,1),(5,1),(4,1),(3,1),(2,1)\n";

        assertEquals(anExpectedResult, aPuzzleSolution);
    }

    @Test public void wordSearchCanFindBackwardAndDownwardDiagonalWords() {
        String aPuzzleGrid = 
            "STEVE,TONY,NATASHA\n" + 
            "L,M,U,K,S,Z,Z,W\n" + 
            "M,O,E,T,U,R,N,L\n" +
            "T,L,E,K,O,A,E,E\n" +
            "O,V,U,D,T,K,T,R\n" +
            "E,N,A,A,D,O,K,Z\n" +
            "B,C,S,B,N,A,L,M\n" +
            "O,H,A,Y,K,W,G,Y\n" +
            "A,R,K,L,T,T,R,N\n";
            
        StringReader aStringReader = new StringReader(aPuzzleGrid);
        String aPuzzleSolution = "";

        try
        {
             aPuzzleSolution = myWordSearch.solvePuzzle(aStringReader);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        aStringReader.close();

        String anExpectedResult = 
            "STEVE: (4,0),(3,1),(2,2),(1,3),(0,4)\n" + 
            "TONY: (6,3),(5,4),(4,5),(3,6)\n" + 
            "NATASHA: (6,1),(5,2),(4,3),(3,4),(2,5),(1,6),(0,7)\n";

        assertEquals(anExpectedResult, aPuzzleSolution);
    }
}
