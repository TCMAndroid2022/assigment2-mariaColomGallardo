package com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.daoImpl.ScoreDao;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.domain.Score;

import java.util.List;

public class ScoreViewModel extends AndroidViewModel {
    private ScoreDao repository;

    public ScoreViewModel(@NonNull Application application) {
        super(application);
        repository = new ScoreDao(application);
    }

    public List<Score> getScoresPlayer(String nickname) { return repository.getScoresPlayer(nickname); }

    public void insertScore(Score score) { repository.insertScore(score); }
}
