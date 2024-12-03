package com.example.simongame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ScoreboardActivity extends AppCompatActivity {

    private ListView scoreListView;
    private ScoreAdapter scoreAdapter;
    private DatabaseHelper dbHelper;
    private Button playAgainButton, quitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);


        scoreListView = findViewById(R.id.scoreListView);
        playAgainButton = findViewById(R.id.playAgainButton);
        quitButton = findViewById(R.id.quitButton);

        dbHelper = new DatabaseHelper(this);


        ArrayList<Score> topScores = dbHelper.getTopScores();


        scoreAdapter = new ScoreAdapter(this, topScores);
        scoreListView.setAdapter(scoreAdapter);


        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ScoreboardActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
            }
        });


        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finishAffinity();
            }
        });
    }
}
