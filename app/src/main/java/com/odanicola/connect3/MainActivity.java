package com.odanicola.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0 = yellow, 1 = red
    int activePlayer = 0;

    // 2 means unplayed
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    public void dropIn (View view){
        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2){
            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.button_yellow);

                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics());
                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics());

                counter.getLayoutParams().width = width;
                counter.getLayoutParams().height = height;
                activePlayer = 1;

            } else {
                counter.setImageResource(R.drawable.button_red);

                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics());
                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics());

                counter.getLayoutParams().width = width;
                counter.getLayoutParams().height = height;
                activePlayer = 0;

            }

            counter.animate().translationYBy(1000f).setDuration(300).rotation(360);

            for (int[] winningPosition : winningPositions){
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                        && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                        && gameState[winningPosition[0]] != 2) {

                    // Someone has won!

                    String winner = "Red";

                    if(gameState[winningPosition[0]] == 0){
                        winner = "Yellow";
                    }

                    TextView winnerMessage = findViewById(R.id.txtWin);
                    winnerMessage.setText(winner + " has won!");

                    LinearLayout layout =  findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);

                }else{
                    boolean gameIsOver = true;
                    for (int counterState : gameState) {
                        if (counterState == 2) gameIsOver = false;
                    }

                    if (gameIsOver){
                        TextView winnerMessage = findViewById(R.id.txtWin);
                        winnerMessage.setText("It's a draw");

                        LinearLayout layout =  findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }

    }

    public void playAgain(View view){
        LinearLayout layout = findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        activePlayer = 0;

        for (int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }

        final GridLayout grid = findViewById(R.id.gridLayout);
        int child = grid.getChildCount();
        for (int i = 0; i < child; i++){
            ((ImageView) grid.getChildAt(i)).setImageResource(android.R.color.transparent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
