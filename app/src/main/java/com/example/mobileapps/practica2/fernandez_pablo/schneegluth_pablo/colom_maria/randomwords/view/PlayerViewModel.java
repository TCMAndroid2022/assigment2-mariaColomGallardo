package com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.daoImpl.PlayerDao;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.domain.Player;

import java.util.List;

public class PlayerViewModel extends AndroidViewModel {
    private PlayerDao repository;
    private LiveData<List<Player>> allPlayers;

    public PlayerViewModel(@NonNull Application application) {
        super(application);
        repository = new PlayerDao(application);
        allPlayers = repository.getAllPlayers();
    }

    public LiveData<List<Player>> getAllPlayers() {
        return allPlayers;
    }

    public void insertPlayer(Player player) {
        repository.insertPlayer(player);
    }

    public LiveData<Player> getUser(String nickname) { return repository.getUser(nickname);}

    public void updateScore(String nickname, float score) {repository.updateScore(nickname, score);}

    public void updatePlayed(String nickname, int games) { repository.updatePlayed(nickname, games); }

    public int getGamesPlayed(String nickname) { return repository.getGamesPlayed(nickname); }

    public float getScore(String nickname) { return repository.getScore(nickname); }
}
