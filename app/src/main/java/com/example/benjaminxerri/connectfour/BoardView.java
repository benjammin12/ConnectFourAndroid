package com.example.benjaminxerri.connectfour;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.benjaminxerri.connectfour.model.ConnectFour;

/**
 * Created by benjaminxerri on 10/12/17.
 */

public class BoardView extends GridLayout {

    private View[][] gameBoard;
    private Button[][] dropPiece;
    private TextView gameStatus;
    private int rows;
    private int columns;

    public BoardView(Context context,int width, int rows, int columns, OnClickListener listener){
        super(context);
        this.rows = rows;
        this.columns = columns;
        setRowCount(rows +2);
        setColumnCount(columns);


        gameBoard = new View[rows][columns];
        dropPiece = new Button[rows+1][columns];


        //set up game board of views
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                gameBoard[row][col] = new View(context);
                gameBoard[row][col].setBackgroundResource(R.drawable.board);
                addView(gameBoard[row][col], width, width);
            }
        }

        //add a row of buttons at bottom of board, when this gameBoard is clicked, a piece will appear next available slot
        for (int row = 5; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                dropPiece[row][col] = new Button(context);
                addView(dropPiece[row][col], width ,width);
                dropPiece[row][col].setOnClickListener(listener);
                dropPiece[row][col].setTag(Integer.valueOf(col));

            }
        }


        //creating status textview at bottom of the board
        gameStatus = new TextView(context);
        Spec rowSpec = GridLayout.spec(ConnectFour.ROWS+1, 1);
        Spec colSpec = GridLayout.spec(0, ConnectFour.COLUMNS);
        LayoutParams lp = new LayoutParams(rowSpec,colSpec);

        gameStatus.setLayoutParams(lp);
        gameStatus.setWidth(ConnectFour.COLUMNS * width);
        gameStatus.setHeight(width*2);
        gameStatus.setGravity(Gravity.CENTER);
        gameStatus.setTextSize(35);


        addView(gameStatus);

    }

    // set the background resourse
    public void drawOnPiece(int row, int col, int id){
        gameBoard[row][col].setBackgroundResource(id);
    }

    public void setStatusText(String txt){
        gameStatus.setText(txt);
    }


    public void clearGameBoard(){
        for(int row = 0; row < ConnectFour.ROWS; row++){
            for(int col=0; col < ConnectFour.COLUMNS; col++){
                gameBoard[row][col].setBackgroundResource(R.drawable.board);
            }
        }
    }




}
