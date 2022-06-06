package com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.R;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.domain.Player;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    private List<Player> rankingPlayers;
    Context context;
    private OnPlayerListener mOnPlayerListener;

    public PlayerAdapter(Context ctx, OnPlayerListener onPlayerListener) {
        this.context = ctx;
        this.mOnPlayerListener = onPlayerListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mOnPlayerListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Player player = rankingPlayers.get(position);
        holder.position.setText(String.valueOf(position+1)+".");
        holder.nickname.setText(player.getNickname());
        holder.score.setText(String.valueOf(player.getScore()));
        holder.games.setText(String.valueOf(player.getGamesPlayed()));
    }

    @Override
    public int getItemCount() {
        if (rankingPlayers == null) {
            return 0;
        }
        return rankingPlayers.size();
    }

    public void setPlayers(List<Player> players) {
        rankingPlayers = players;
        notifyDataSetChanged();
    }

    public List<Player> getPlayers() {
        return rankingPlayers;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView position,nickname,score,games;
        OnPlayerListener onPlayerListener;

        public ViewHolder(@NonNull View itemView, OnPlayerListener mOnPlayerListener) {
            super(itemView);
            position = itemView.findViewById(R.id.tvPosition);
            nickname = itemView.findViewById(R.id.tvNick);
            score = itemView.findViewById(R.id.tvScoreList);
            games = itemView.findViewById(R.id.tvGames);

            this.onPlayerListener = mOnPlayerListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onPlayerListener.onPlayerClick(getAdapterPosition());
        }
    }

    public interface OnPlayerListener {
        void onPlayerClick(int position);
    }
}
