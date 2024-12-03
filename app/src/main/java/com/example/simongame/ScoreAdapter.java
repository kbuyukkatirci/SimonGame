package com.example.simongame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ScoreAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Score> scoreList;

    public ScoreAdapter(Context context, ArrayList<Score> scoreList) {
        this.context = context;
        this.scoreList = scoreList;
    }

    @Override
    public int getCount() {
        return scoreList.size();
    }

    @Override
    public Object getItem(int position) {
        return scoreList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.score_item, null);
        }


        TextView rankText = convertView.findViewById(R.id.rankText);
        TextView usernameText = convertView.findViewById(R.id.usernameText);
        TextView scoreText = convertView.findViewById(R.id.scoreText);


        Score score = scoreList.get(position);


        rankText.setText((position + 1) + "."); // Sıralama 1, 2, 3...
        usernameText.setText(score.getUsername());
        scoreText.setText(String.valueOf(score.getScore()));

        return convertView;
    }
}
