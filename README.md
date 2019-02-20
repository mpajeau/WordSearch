# WordSearch

This project contains a java program that can read a text file containing a list of search words and a grid of letters and then solve the puzzle by reporting where each search word occurs within the grid.

## Usage:

From within the project directory:

    To run the application:
        gradle run --args=<puzzle file>

          OR

        java net.pajeau.WordSearchApp <puzzle file>

    To run all tests:
        gradle test

## Puzzle File Format:

The puzzle file should contain a comma-separated list of words to search for on the first line followed by lines of comma-separated letters making up the grid containing the words.

For example, see below (or the included file ModifiedStarTrekWordSearch.txt):

    BONES,KHAN,KIRK,SCOTTY,SPOCK,SULU,UHURA,CHEKOV
    U,M,K,H,U,L,K,I,N,V,J,O,C,W,E
    L,L,S,H,K,Z,Z,W,Z,C,G,J,U,Y,G
    H,S,U,P,J,P,R,J,D,H,S,B,X,T,G
    B,R,J,S,O,E,Q,E,T,I,K,K,G,L,E
    A,Y,O,A,G,C,I,R,D,Q,H,R,T,C,D
    S,C,O,T,T,Y,K,Z,R,E,P,P,X,P,F
    B,L,Q,S,L,N,E,E,E,V,U,L,F,M,Z
    O,K,R,I,K,A,M,M,R,M,F,B,A,V,P
    N,U,I,I,Y,H,Q,M,E,M,Q,R,O,F,S
    E,Y,Z,Y,G,K,Q,J,P,C,Q,K,Y,A,K
    S,J,F,Z,M,Q,I,B,D,B,E,M,K,W,D
    T,G,L,B,H,C,B,E,C,H,T,O,Y,I,K
    O,J,Y,E,U,L,N,C,C,L,Y,B,Z,U,H
    W,Z,M,I,S,U,K,U,R,B,I,D,U,X,S
    K,Y,L,B,Q,Q,P,M,D,F,C,K,E,A,B

Search words must be at least two characters long and the grid must be square.
