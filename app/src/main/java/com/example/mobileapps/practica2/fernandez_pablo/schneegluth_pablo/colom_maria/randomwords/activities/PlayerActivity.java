package com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.R;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.domain.Player;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.domain.Score;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.adapter.ScoreAdapter;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.view.ScoreViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlayerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ScoreAdapter scoreAdapter;
    private ScoreViewModel viewModel;
    List<Score> scores;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        recyclerView = findViewById(R.id.player_recycler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        viewModel = new ViewModelProvider(this).get(ScoreViewModel.class);
        scoreAdapter = new ScoreAdapter();

        Intent intent = getIntent();
        String nick = intent.getStringExtra("nickname");
        scores = viewModel.getScoresPlayer(nick);
        scoreAdapter.setScore(scores);

        recyclerView.setAdapter(scoreAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}