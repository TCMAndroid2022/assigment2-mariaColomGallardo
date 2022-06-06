package com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.domain.Player;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.adapter.PlayerAdapter;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.view.PlayerViewModel;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.R;

import java.util.List;

public class RankingActivity extends AppCompatActivity implements PlayerAdapter.OnPlayerListener {
    private RecyclerView recyclerView;
    private PlayerAdapter playerAdapter;
    private PlayerViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        viewModel = new ViewModelProvider(this).get(PlayerViewModel.class);
        viewModel.getAllPlayers().observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(List<Player> players) {
                playerAdapter.setPlayers(players);
            }
        });

        playerAdapter = new PlayerAdapter(this,this);
        recyclerView.setAdapter(playerAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onPlayerClick(int position) {
        Player currentPlayer = playerAdapter.getPlayers().get(position);
        Intent intent = new Intent(this, PlayerActivity.class);

        intent.putExtra("nickname", currentPlayer.getNickname());

        startActivity(intent);
    }
}
