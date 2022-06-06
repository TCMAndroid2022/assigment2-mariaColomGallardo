package com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.R;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.domain.Score;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {

    private List<Score> scorePlayer;

    public ScoreAdapter() { }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_player, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Score score = scorePlayer.get(position);
        holder.position.setText(String.valueOf(position+1)+".");
        holder.nickname.setText(score.getNickname());
        holder.score.setText(String.valueOf(score.getScore()));
    }

    @Override
    public int getItemCount() {
        if (scorePlayer == null) {
            return 0;
        }
        return scorePlayer.size();
    }

    public void setScore(List<Score> scores) {
        scorePlayer = scores;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView position,nickname,score;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            position = itemView.findViewById(R.id.tvPositionPlayer);
            nickname = itemView.findViewById(R.id.tvNickPlayer);
            score = itemView.findViewById(R.id.tvScoreListPlayer);
        }
    }
}
