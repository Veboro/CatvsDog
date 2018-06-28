package com.veboro.catvsdog;

import android.media.MediaPlayer;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dragankrstic.autotypetextview.AutoTypeTextView;

public class MainActivity extends AppCompatActivity {

    MediaPlayer media = new MediaPlayer();

    //0 = yellow, 1 = red
    int activePlayer = 0;

    boolean gameIsActive = true;

    int attack = 0;

    //two means unplayed
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winnigPosition = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    public void dropIn(View view) {


        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameIsActive) {

            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.caticon);
                media = MediaPlayer.create(this,R.raw.catvoice);
                media.start();
                activePlayer = 1;
                AutoTypeTextView speedText = findViewById(R.id.speedtext);
                speedText.setTypingSpeed(50);
                speedText.setTextAutoTyping(getString(R.string.fight));
            } else {
                counter.setImageResource(R.drawable.dogicon);
                media.reset();
                media = MediaPlayer.create(this,R.raw.dogbark);
                media.start();
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);

            for (int[] winnigPosition : winnigPosition) {

                if (gameState[winnigPosition[0]] == gameState[winnigPosition[1]] && gameState[winnigPosition[1]] == gameState[winnigPosition[2]] &&
                        gameState[winnigPosition[0]] != 2){

                    String winner = getString(R.string.dogs);
                    ImageView winnerImage = findViewById(R.id.winner_image);
                    winnerImage.setImageResource(R.drawable.doghappy);

                    if(gameState[winnigPosition[0]]==0){
                        winner = getString(R.string.cats);
                        winnerImage.setImageResource(R.drawable.cathappy);
                    }

                    gameIsActive = false;

                    TextView winnerMessage = findViewById(R.id.winner_message);

                    winnerMessage.setText(winner+" "+ getString(R.string.wiiner_text));

                    LinearLayout linearLayout = findViewById(R.id.play_again_layout);
                    linearLayout.setVisibility(View.VISIBLE);
                } else {
                    boolean gameIsOver = true;
                    for(int counterState: gameState){
                        if(counterState == 2){
                            gameIsOver = false;
                        }
                    }

                    if (gameIsOver){
                        TextView winnerMessage = findViewById(R.id.winner_message);

                        winnerMessage.setText(R.string.draw);

                        LinearLayout linearLayout = findViewById(R.id.play_again_layout);
                        linearLayout.setVisibility(View.VISIBLE);

                    }
                }

            }

        }
    }

    public void playAgain(View view) {
        LinearLayout linearLayout = findViewById(R.id.play_again_layout);
        linearLayout.setVisibility(View.INVISIBLE);

        activePlayer = 0;
        gameIsActive = true;

        for(int i = 0; i<gameState.length;i++){
            gameState[i] = 2;
        }

        GridLayout gridLayout = findViewById(R.id.grid_layout);
            for(int i=0; i<gridLayout.getChildCount();i++){
                ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
            }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
