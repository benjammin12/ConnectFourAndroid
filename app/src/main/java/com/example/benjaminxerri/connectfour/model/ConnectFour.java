package com.example.benjaminxerri.connectfour.model;

import java.util.Arrays;

public class ConnectFour {
    public final static int ROWS = 6;
    public final static int COLUMNS = 7;
    private int[][] gameBoard;
    private int turn;
    private final static int PLAYER_ONE = 1;
    private final static int PLAYER_TWO = 2;
    private final int EMPTY_SLOT = 0;


    public ConnectFour(){
        gameBoard = new int[6][7];
        resetGame();
    }

    /**
     * Checks if the move the user made is valid and within the limits of the board,
     * if it is, it enters the turn of the player who went onto the board, and makes the turn the next persons
     * @param row
     * @param col
     * @return the old turn or 0 if the turn attempted was not valid
     */
    public int playMove(int row, int col){
        int currentTurn = turn;

        //checks if the move is within the limits of the board
        if (row >= 0 && col >= 0 && row < 6 && col < 7 && gameBoard[row][col] == 0){
            gameBoard[row][col] = currentTurn;

            if (turn == 1){
                setTurn(2);
            }
            else {
                setTurn(1);
            }
            return currentTurn;
        }

        return 0;
    }

    public int checkRows(){

        int numInARowP1 = 0;
        int numInARowP2 = 0;

        for (int row = 5; row >= 0; row--){

            for(int col=0; col < COLUMNS; col++ ){

                if (gameBoard[row][col] == PLAYER_ONE){
                    numInARowP1++;
                    numInARowP2 = 0;
                }

                if(gameBoard[row][col] == PLAYER_TWO){
                    numInARowP2 ++;
                    numInARowP1 = 0;
                }

                if (numInARowP1 == 4){
                    return PLAYER_ONE;
                }

                if (numInARowP2 == 4){
                    return PLAYER_TWO;
                }

            }
        }
        return 0;
    }



    public int checkWin() {
        for (int row = 0; row < ROWS; row++) { // iterate rows, bottom to top
            for (int column = 0; column < COLUMNS; column++) { // iterate columns, left to right
                int player = gameBoard[row][column];
                if (player == EMPTY_SLOT)
                    continue; // don't check empty slots

                if (column + 3 < COLUMNS &&
                        player == gameBoard[row][column+1] && // look right
                        player == gameBoard[row][column+2] &&
                        player == gameBoard[row][column+3])
                    return player;
                if (row + 3 < ROWS) {
                    if (player == gameBoard[row+1][column] && // look up
                            player == gameBoard[row+2][column] &&
                            player == gameBoard[row+3][column])
                        return player;
                    if (column + 3 < COLUMNS &&
                            player == gameBoard[row+1][column+1] && // look up & right
                            player == gameBoard[row+2][column+2] &&
                            player == gameBoard[row+3][column+3])
                        return player;
                    if (column - 3 >= 0 &&
                            player == gameBoard[row+1][column-1] && // look up & left
                            player == gameBoard[row+2][column-2] &&
                            player == gameBoard[row+3][column-3])
                        return player;
                }
            }
        }
        return EMPTY_SLOT; // no winner found
    }

    public boolean boardIsFull(){
        for(int row = 0; row < ROWS; row++){
            for(int col = 0; col < COLUMNS; col++){
                if (gameBoard[row][col]==0){
                    return false;
                }
            }
        }
        return true;
    }

    // if game is over then check the winner to output the results.
    // the game is over if either the board is full or checkWin returns a value greater than 0;
    public boolean gameOver(){
        return (boardIsFull() || checkWin() > 0);
    }

    public void resetGame(){
        for(int row = 0; row < ROWS; row++){
            for(int col = 0; col < COLUMNS; col++){
                gameBoard[row][col] = 0;
            }
        }
        turn = 1;
    }

    public void setTurn(int turn){
        this.turn = turn;
    }

    public int getTurn(){return turn;}

    public int[][] getBoard(){
        return gameBoard;
    }

    public void printGameBoard(){
        System.out.println(Arrays.toString(gameBoard));
    }

    public void setBoard(int[][] newBoard){
        gameBoard = newBoard.clone();
    }



}
