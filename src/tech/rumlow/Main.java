package tech.rumlow;

import java.util.Random;
import java.util.Scanner;

public class Main
{

    public static void main(String[] args)
    {
        boolean gameOn=true;
        while (gameOn)
        {
            play();
            gameOn=askToReplay();
        }
        message(2);
    }
    public static void play()
    {
        char[][] gameBoard=generateBoard();

        boolean keepGenerateBoard = true;
        while (keepGenerateBoard){
            gameBoard= generateBoard();
            randomizeCellNumber(gameBoard);
            keepGenerateBoard=isBoardUnsolvable(gameBoard);
        }

        printBoard(gameBoard);

        message(0);

        boolean keepPlaying=true;
        while (keepPlaying)
        {
            int[] theEmptyCell = findEmptyCell(gameBoard);
            playerMove(theEmptyCell, gameBoard);
            printBoard(gameBoard);

            theEmptyCell = findEmptyCell(gameBoard);
            if
            (
                    theEmptyCell[0]==theEmptyCell[1] &&
                            theEmptyCell[0]==4
            )
            {
                keepPlaying=isBoardStillRandom(gameBoard);
            }
        }
    }
    public static char[][] generateBoard(){
        char[][] gameBoard =
                {
                    {' ','|',' ','|',' '},
                    {'-','+','-','+','-'},
                    {' ','|',' ','|',' '},
                    {'-','+','-','+','-'},
                    {' ','|',' ','|',' '},
                };
        return gameBoard;
    }
    public static void message(int messageNumbere)
    {
        switch (messageNumbere)
        {
            case 0:
                System.out.println
                        (
                                "\nUse A, S, D, or W in your..." +
                                "\nkeyboard and press ENTER to" +
                                "...\nmove the empty cell!"
                        );
                break;
            case 1:
                System.out.print("Play again? (y/n) ");
                break;
            case 2:
                System.out.println("Okay, then. See ya!");
                break;
            case 3:
                System.out.println("You win");
                break;
            case 4:
                System.out.println("Goodbye!");
                break;
            case 5:
                System.out.print("\nEnter your move (q=EXIT): ");
                break;
            case 6:
                System.out.println
                        (
                                "Wrong input. " +
                                        "Please enter " +
                                        "another input!"
                        );
                break;
            case 7:
                System.out.println
                        (
                                "Empty cell already on " +
                                        "the edge of the board." +
                                        "\nPlease enter another" +
                                        " direction!"
                        );
                break;
        }
    }
    public static boolean askToReplay()
    {
        message(1);
        Scanner SC = new Scanner(System.in);
        char userInput = SC.next().charAt(0);
        return userInput == 'Y' || userInput == 'y';
    }
    public static boolean isBoardStillRandom
            (
                    char[][] gameBoard
            )
    {
        for(int row=0;row<gameBoard.length;row+=2)
            for (int col=0;col<gameBoard[row].length;col+=2)
            {
                int cellExpectedValue =
                        cellExpectedValue
                                (
                                        new int[]{row, col}
                                );

                if(gameBoard[row][col]==' '){}
                else if((gameBoard[row][col]-'0') != cellExpectedValue)
                {
                    return true;
                }
            }
        message(3);
        return false;
    }
    public static void forceExit()
    {
        message(4);
        System.exit(0);
    }
    public static void randomizeCellNumber
            (
                    char[][] gameBoard
            )
    {
        int numberOfCell = 9;
        Random rand = new Random();
        for(
                int cellNumber=1;
                cellNumber<numberOfCell;
                cellNumber++
        )
        {
            boolean keepLooking = true;
            while (keepLooking)
            {
                int randomCellLocation =
                        rand.nextInt(numberOfCell)+1;
                int[] randomCellCoordinate = cellLocator
                        (
                                randomCellLocation
                        );
                int row = randomCellCoordinate[0];
                int col = randomCellCoordinate[1];

                if(gameBoard[row][col] == ' ')
                {
                    gameBoard[row][col] = (char)
                            (
                                    cellNumber+'0'
                            );
                    keepLooking = false;
                }
            }
        }
    }
    public static boolean isBoardUnsolvable
            (
                    char[][] gameBoard
            )
    {
        int[] valueInOneDim = new int[9];
        for(int row=0;row<gameBoard.length;row+=2)
            for (int col=0;col<gameBoard[row].length;col+=2)
            {
                int expectedValue =
                        cellExpectedValue
                                (
                                        new int[]{row, col}
                                );
                if(gameBoard[row][col]==' '){}
                else
                {
                    valueInOneDim[expectedValue-1] = gameBoard[row][col] - '0';
                }
            }

        int inversionCounter = 0;
        for(int a = 0;a<valueInOneDim.length-1;a++)
        {
            if(a+1 <valueInOneDim.length-1)
            {
                if(valueInOneDim[a]==0) a++;
                if(valueInOneDim[a]==1) a++;
                if(valueInOneDim[a]==0) a++;
            }
            for(int b = a+1;b<valueInOneDim.length;b++)
            {
                if(valueInOneDim[b]==0 && b+1 !=valueInOneDim.length) b++;
                if(valueInOneDim[b]==0){}
                else if(valueInOneDim[a] > valueInOneDim[b]) inversionCounter++;
            }
        }
        return inversionCounter % 2 != 0;
    }
    public static int[] findEmptyCell(char[][] gameBoard)
    {
        for(int row=0;row<gameBoard.length;row+=2)
            for (int col=0;col<gameBoard[row].length;col+=2)
            {
                if (gameBoard[row][col] == ' ') {
                    return new int[]{row, col};
                }
            }
        return new int[0];
    }
    public static int cellExpectedValue(int[] cellLocator)
    {
        int row = cellLocator[0];
        int col = cellLocator[1];
        int expectedValue = ((row/2)*3+(col/2)+1);
        char expectedValueChar = (char) (expectedValue+'0');
        if(expectedValueChar=='9') expectedValueChar=' ';
        return expectedValue;
    }
    public static int[] cellLocator(int input)
    {
        int row = 2*((input-1)/3);

        int mod3 = input%3;
        if (mod3==0) mod3=3;

        int col = 2*(mod3-1);

        return new int[]{row, col};
    }
    public static void printBoard(char[][] gameBoard)
    {
        for (char[] row : gameBoard)
        {
            for (char c : row) System.out.print(c);
            System.out.println();
        }
    }
    public static int[] nextEmptyCell
            (
                    int[] theEmptyCell,
                    int[] dirArray
            )
    {
        int[] nextEmptyCell={0,0};
        for(int i=0;i<theEmptyCell.length;i++)
        {
            nextEmptyCell[i]=theEmptyCell[i]+dirArray[i];
        }
        return nextEmptyCell;
    }
    public static void playerMove
            (
                    int[] theEmptyCell,
                    char[][] gameBoard
            )
    {
        Scanner SC = new Scanner(System.in);
        boolean keepAsking=true;
        while (keepAsking)
        {
            int[] dirArray={0,0};
            keepAsking = false;
            message(5);
            char userInput = Character.toUpperCase
                    (
                            SC.next().charAt(0)
                    );

            switch (userInput)
            {
                case 'A':
                    dirArray[1] = -2;
                    break;
                case 'S':
                    dirArray[0] = 2;
                    break;
                case 'D':
                    dirArray[1] = 2;
                    break;
                case 'W':
                    dirArray[0] = -2;
                    break;
                case 'Q':
                    forceExit();
                    keepAsking=false;
                    break;
                default:
                    message(6);
                    keepAsking = true;
                    break;
            }
            int[] nextEmptyCell = nextEmptyCell
                    (
                            theEmptyCell, dirArray
                    );
            int nextRow = nextEmptyCell[0];
            int nextCol = nextEmptyCell[1];

            if(
                    nextRow>4 || nextCol>4 ||
                    nextRow<0 || nextCol<0
            )
            {
                message(7);
                printBoard(gameBoard);
                keepAsking = true;
                continue;
            }

            int initRow = theEmptyCell[0];
            int initCol = theEmptyCell[1];

            gameBoard[initRow][initCol] =
                    gameBoard[nextRow][nextCol];
            gameBoard[nextRow][nextCol] = ' ';
        }
    }

}
