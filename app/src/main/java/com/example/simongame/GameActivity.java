package com.example.simongame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private TextView scoreText;
    private SimonView simonView;
    private int currentScore = 0;
    private ArrayList<Integer> sequence = new ArrayList<>();
    private int userPosition = 0;
    private Handler handler = new Handler();
    private DatabaseHelper dbHelper;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        scoreText = findViewById(R.id.scoreText);
        simonView = findViewById(R.id.simonView);


        dbHelper = new DatabaseHelper(this);


        username = getIntent().getStringExtra("username");
        if (username == null || username.isEmpty()) {
            showToast("Username is missing");
            finish();
            return;
        }


        if (!dbHelper.isTableExists()) {
            showToast("Scores table doesn't exist!");
        }


        generateSequence();
        playSequence();


        simonView.setOnSimonClickListener(this::handleSimonClick);
    }

    private void generateSequence() {
        Random random = new Random();
        sequence.add(random.nextInt(4));
    }

    private void playSequence() {
        handler.postDelayed(() -> {
            for (int i = 0; i < sequence.size(); i++) {
                int finalI = i;
                handler.postDelayed(() -> simonView.flashColor(sequence.get(finalI)), i * 1000);
            }
        }, 1000);
    }

    private void handleSimonClick(int color) {
        if (sequence.get(userPosition) == color) {
            userPosition++;
            if (userPosition == sequence.size()) {

                currentScore++;
                scoreText.setText(String.format("Score: %d", currentScore));
                userPosition = 0;
                generateSequence();
                playSequence();
            }
        } else {

            showToast("Game Over!");
            saveScoreAndGoToScoreboard();
        }
    }

    private void saveScoreAndGoToScoreboard() {
        try {
            dbHelper.insertScore(username, currentScore);
            Intent intent = new Intent(GameActivity.this, ScoreboardActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            showToast("Error saving score");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (dbHelper != null) {
            dbHelper.close();
        }
        super.onDestroy();
    }
}
