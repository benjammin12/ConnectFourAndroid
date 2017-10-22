package com.example.benjaminxerri.connectfour.UnitTests;

/**Unit tests, used to check logic of connect four board before building GUI
 * Built and tested in eclipse
 * Created by benjaminxerri on 10/12/17.
 */


import com.example.benjaminxerri.connectfour.model.ConnectFour;

public class CheckWinnings {

    public static void main(String []args){
        ConnectFour gb = new ConnectFour();

        int[][] board = gb.getBoard(); //defaultBoard
        int[][] winBoard = {
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,2,0,1,0},
                {0,0,0,2,1,2,0},
                {0,0,0,2,1,1,2},
                {2,1,2,1,1,1,1}
        };

        gb.setBoard(winBoard);

        for(int i = 0; i < board.length ; i++){
            System.out.print("\n");
            for(int j = 0; j < board[i].length; j++){
                System.out.print(board[i][j]);
            }
        }

        System.out.println("\n" + gb.checkRows());

        System.out.println("Number of columns = " + board[0].length);
        System.out.println("Number of rows = " + board.length);
        System.out.println(gb.checkWin());
    }

}
