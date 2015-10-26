package com.assassin.assassinandroidgame.activities;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.assassin.assassinandroidgame.R;

public class JoinGameActivity extends AppCompatActivity {

    private Button mNewGameButton;
    private Button mJoinGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);

        mNewGameButton = (Button)findViewById(R.id.start_new_game_button);
        mJoinGameButton = (Button)findViewById(R.id.join_game_button);

        mNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Start New Game and show lobby
                int color = ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary);

                v.setBackgroundColor(color);

            }
        });

        mJoinGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Show lobby
                int color = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);

                v.setBackgroundColor(color);

            }
        });
    }
}
