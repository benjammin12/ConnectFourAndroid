package com.example.benjaminxerri.connectfour;

import android.content.DialogInterface;
import android.graphics.Point;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.benjaminxerri.connectfour.model.ConnectFour;


public class MainActivity extends AppCompatActivity {

    private ConnectFour connectFour;
    private BoardView board;
    private int[] numInAColumn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        numInAColumn = new int[ConnectFour.COLUMNS];
        connectFour = new ConnectFour();
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int w = size.x / ConnectFour.COLUMNS;
        ButtonHandler bh = new ButtonHandler();

        //instantiate a board, which is a grid layout
        board = new BoardView(this, w, ConnectFour.ROWS, ConnectFour.COLUMNS, bh);
        board.setStatusText("Starting a new Game!");
        setContentView(board); //set the content view to the board instance
    }


    private class ButtonHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            //get the tag that is assignment when you're creating the row of buttons in BoardView.Java
            Integer positionClicked = (Integer) view.getTag();
            Log.w("The column clicked was", positionClicked.toString());
            Log.w("View is", view.getContext().toString());

            //play move finds out if it is a valid position on the board
            int turn = connectFour.playMove(ConnectFour.ROWS-1 - numInAColumn[positionClicked], positionClicked);

            // for debugging columns counter
            for (int i = 0; i < numInAColumn.length; i++) {
                Log.w("number in column", "" + numInAColumn[i]);
            }

            //set the turn depending on the results of play move
            if (turn == 1) {
                board.drawOnPiece(ConnectFour.ROWS-1 - numInAColumn[positionClicked], positionClicked, R.drawable.player_one);
                board.setStatusText("Player Two's Turn, Red GO!!");
            } else if (turn == 2) {
                board.drawOnPiece(ConnectFour.ROWS-1 - numInAColumn[positionClicked], positionClicked, R.drawable.player_two);
                board.setStatusText("Player One's Turn, Blue GO!!");
            }

            numInAColumn[positionClicked]++;

            if (connectFour.gameOver()) {
                showNewGameDialog();
                Log.w("Game is over", "Game is over!");
                connectFour.resetGame();
                clearColumnCount();
                board.clearGameBoard();
            }

        }
    }


        public void showNewGameDialog(){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over!");
            if(connectFour.getTurn() == 1){
                connectFour.setTurn(2);
            }else{
                connectFour.setTurn(1);
            }
            alert.setTitle("Player " + connectFour.getTurn() + " won!");
            alert.setMessage("Would you like to play again?");
            PlayDialog playAgain = new PlayDialog();
            alert.setPositiveButton("YES", playAgain);
            alert.setNegativeButton("NO", playAgain);
            alert.show();

        }
        //play dialog
        private class PlayDialog implements DialogInterface.OnClickListener {
            public void onClick(DialogInterface dialog, int id) {
                if (id == -1) {
                    connectFour.resetGame();
                    board.setStatusText("Staring a new game!");
                } else if (id == -2) {
                    MainActivity.this.finish();
                }
            }
        }

        public void clearColumnCount() {
            for (int i = 0; i < numInAColumn.length; i++) {
                numInAColumn[i] = 0;
            }
        }
    }

    /* Code before spltting up the view and controller
    private View[][] gameBoard;
    private ConnectFour connectFour;
    private Button[][] dropPiece;
    private int[] numInAColumn; //manually keep track of number in each column
    private TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectFour = new ConnectFour();
        buildConnnectFourBoard();
    }


    public void buildConnnectFourBoard() {
        numInAColumn = new int[ConnectFour.COLUMNS];
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int w = size.x / ConnectFour.COLUMNS;

        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(ConnectFour.COLUMNS);
        gridLayout.setRowCount(ConnectFour.ROWS+2);



        gameBoard = new View[ConnectFour.ROWS][ConnectFour.COLUMNS];
        dropPiece = new Button[ConnectFour.ROWS+1][ConnectFour.COLUMNS];
        ButtonHandler onClick = new ButtonHandler();
        for (int row = 0; row < ConnectFour.ROWS; row++) {
            for (int col = 0; col < ConnectFour.COLUMNS; col++) {
                gameBoard[row][col] = new View(this);
                gameBoard[row][col].setBackgroundResource(R.drawable.board);
                gridLayout.addView(gameBoard[row][col], w, w);
            }
        }

        //add drop gameBoard at bottom of board, when this gameBoard is clicked, a piece will appear next available slot
        for (int row = 5; row < ConnectFour.ROWS; row++) {
            for (int col = 0; col < ConnectFour.COLUMNS; col++) {
                dropPiece[row][col] = new Button(this);
                gridLayout.addView(dropPiece[row][col], w ,w);
                dropPiece[row][col].setOnClickListener(onClick);

            }
        }



        //creating a new spec to add to the layout
        txt = new TextView(this);
        GridLayout.Spec rowSpec = GridLayout.spec(ConnectFour.ROWS+1, 1);
        GridLayout.Spec colSpec = GridLayout.spec(0, ConnectFour.COLUMNS);
        GridLayout.LayoutParams lp = new GridLayout.LayoutParams(rowSpec,colSpec);


        txt.setLayoutParams(lp);
        txt.setWidth(ConnectFour.COLUMNS * w);
        txt.setHeight(w*2);
        txt.setGravity(Gravity.CENTER);
        txt.setTextSize(35);
        txt.setText("Welcome to Connect Four!");


        gridLayout.addView(txt);
        setContentView(gridLayout);



    }

    private class ButtonHandler implements View.OnClickListener{

        @Override
        public void onClick(View view) {

            for (int row = 0; row < ConnectFour.ROWS; row++) {
                for (int col = 0; col < ConnectFour.COLUMNS; col++) {
                    if (view == dropPiece[row][col]) {
                        Log.w("Inside ", "View: " + gameBoard[row][col]);
                        //you need to get the turn of the row - numInAColumn, otherwise, you will get 0 because the column is full
                        //the play is looking for if its a valid position within the connect four instance
                        int turn = connectFour.playMove(row-numInAColumn[col],col);
                        Log.w("Writing at", "" + row + " " + col);
                        for (int i = 0; i < numInAColumn.length; i++){
                            Log.w("number in column", "" + numInAColumn[i]);
                        }

                        if (turn == 1) {
                            gameBoard[row-numInAColumn[col]][col].setBackgroundResource(R.drawable.player_one);
                            txt.setText("Player Two's Turn, Red GO!!");
                        }
                        else if(turn == 2){
                            gameBoard[row-numInAColumn[col]][col].setBackgroundResource(R.drawable.player_two);
                            txt.setText("Player One's Turn, Blue GO!!");

                        }

                        numInAColumn[col]++;

                        if(connectFour.gameOver()){
                            showNewGameDialog();
                            Log.w("Game is over", "Game is over!");
                            connectFour.resetGame();
                            clearColumnCount();
                            clearGameBoard();
                        }


                    }
                }
            }

        }


    }
    public void showNewGameDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Game Over!");
        if(connectFour.getTurn() == 1){
            connectFour.setTurn(2);
        }else{
            connectFour.setTurn(1);
        }
        alert.setTitle("Player " + connectFour.getTurn() + " won!");
        alert.setMessage("Would you like to play again?");
        PlayDialog playAgain = new PlayDialog();
        alert.setPositiveButton("YES", playAgain);
        alert.setNegativeButton("NO", playAgain);
        alert.show();

    }

    public void clearGameBoard(){
        for(int row = 0; row < ConnectFour.ROWS; row++){
            for(int col=0; col < ConnectFour.COLUMNS; col++){
                gameBoard[row][col].setBackgroundResource(R.drawable.board);
            }
        }
    }

    public void clearColumnCount(){
        for (int i = 0; i < numInAColumn.length; i++){
            numInAColumn[i] = 0;
        }
    }

    private class PlayDialog implements DialogInterface.OnClickListener{
        public void onClick(DialogInterface dialog, int id){
            if(id==-1){
                connectFour.resetGame();
                txt.setText("Staring a new game!");
            }
            else if(id == -2){
                MainActivity.this.finish();
            }
        }
    }
*/

