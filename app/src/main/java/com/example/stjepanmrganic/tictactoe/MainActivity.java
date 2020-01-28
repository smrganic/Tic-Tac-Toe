package com.example.stjepanmrganic.tictactoe;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    
    private Button[][] Cell = new Button[3][3];
    private boolean Turn = true;
    private int movesMade;
    private int p1Score;
    private int p2Score;
    private TextView p1Label;
    private TextView p2Label;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        p1Label = findViewById(R.id.p1Label);
        p2Label = findViewById(R.id.p2Label);


        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                String ButtonID = "button_" + i + j;
                int resID = getResources().getIdentifier(ButtonID, "id", getPackageName());
                Cell[i][j]=findViewById(resID);
                Cell[i][j].setOnClickListener(this);
            }
        }


        Button buttonReset=findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    
    @Override
    public void onClick(View v) {

        if(!((Button) v).getText().toString().equals("")) {
            return;
        }
        if(Turn) {
            ((Button) v).setText("X");
        }
        else {
            ((Button) v).setText("O");
        }


        movesMade++;


        if(CheckForWIN()){
            if(Turn){
                Player1Wins();
            }
            else{
                Player2Wins();
            }
        }
        else if(movesMade == 9){
            draw();
        }
        else{
            Turn = !Turn;
        }
    }

    
    private boolean CheckForWIN(){
        
        String[][] array = new String[3][3];

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                array[i][j] = Cell[i][j].getText().toString();
            }
        }

        for(int j = 0; j < 3; j++) {
            if (array[j][0].equals(array[j][1]) && array[j][0].equals(array[j][2]) && !array[j][0].equals("")) {
                return true;
            }
            if (array[0][j].equals(array[1][j]) && array[0][j].equals(array[2][j]) && !array[0][j].equals("")) {
                return true;
            }
            if (array[0][0].equals(array[1][1]) && array[0][0].equals(array[2][2]) && !array[0][0].equals("")) {
                return true;
            }
            if (array[0][2].equals(array[1][1]) && array[0][2].equals(array[2][0]) && !array[0][2].equals("")) {
                return true;
            }
        }

        return false;
    }


    private void Player1Wins(){
        p1Score++;
        Toast.makeText( this,"Player 1 Wins", Toast.LENGTH_SHORT).show();
        updatePoints();
        reset();
    }


    private void Player2Wins(){
        p2Score++;
        Toast.makeText( this,"Player 2 Wins", Toast.LENGTH_SHORT).show();
        updatePoints();
        reset();
    }


    private void draw(){
        Toast.makeText( this,"Draw", Toast.LENGTH_SHORT).show();
        reset();
    }


    private void updatePoints(){
        p1Label.setText("Player 1: "+p1Score);
        p2Label.setText("Player 2: "+p2Score);
    }


    private void reset(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                Cell[i][j].setText("");
            }
        }
        movesMade = 0;
        Turn = true;
    }


    private void resetGame(){
        p1Score = 0;
        p2Score = 0;
        updatePoints();
        reset();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("movesMade", movesMade);
        outState.putInt("p1Score", p1Score);
        outState.putInt("p2Score", p2Score);
        outState.putBoolean("Turn", Turn);

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        movesMade=savedInstanceState.getInt("movesMade");
        p1Score=savedInstanceState.getInt("p1Score");
        p1Score=savedInstanceState.getInt("p2Score");
        Turn=savedInstanceState.getBoolean("Turn");
    }
}
