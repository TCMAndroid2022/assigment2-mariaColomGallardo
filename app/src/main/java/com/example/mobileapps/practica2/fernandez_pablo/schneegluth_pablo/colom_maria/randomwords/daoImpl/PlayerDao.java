package com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.daoImpl;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.database.AppDatabase;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.dao.IPlayerDao;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.domain.Player;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PlayerDao {
    private IPlayerDao playerDao;
    private LiveData<List<Player>> allPlayers;
    AppDatabase db;

    public PlayerDao(Application application) {
        db = AppDatabase.getDatabase(application);
        playerDao = db.playerDao();
        allPlayers = playerDao.getAllPlayers();
    }

    public LiveData<List<Player>> getAllPlayers() {
        return allPlayers;
    }

    public void insertPlayer(Player player) {
        new asyncTask(playerDao).execute(player);
    }

    public LiveData<Player> getUser(String nickname) {
       LiveData<Player> player = playerDao.getUser(nickname);

       return player;
    }

    public void updateScore(String nickname, float score) {
        new asyncTask(playerDao).updateScore(nickname, score);
    }

    public void updatePlayed(String nickname, int games) {
        new asyncTask(playerDao).updatePlayed(nickname, games);
    }

    public int getGamesPlayed(String nickname) {
        int gamesPlayed = playerDao.getGamesPlayed(nickname);
        return gamesPlayed;
    }

    public float getScore(String nickname) {
        float score = playerDao.getScore(nickname);
        return score;
    }

    private static class asyncTask {
        private IPlayerDao asyncDao;
        private Executor executor = Executors.newSingleThreadExecutor();

        asyncTask(IPlayerDao dao) {
            asyncDao = dao;
        }

        public void execute(Player player) {
            this.doInBackground(player);
        }

        public void updateScore(String player, float score) {this.updateScorePlayer(player, score);}

        public void updatePlayed(String player, int games) {this.updateGamesPlayed(player,games);}

        private void doInBackground(final Player player) {
            this.executor.execute(new Runnable() {
                @Override
                public void run() {
                    asyncDao.insertPlayer(player);
                }
            });
        }

        private void updateScorePlayer(String player, float score) {
            this.executor.execute(new Runnable() {
                @Override
                public void run() {
                    asyncDao.updateScore(player, score);
                }
            });
        }

        private void updateGamesPlayed(String player, int games) {
            this.executor.execute(new Runnable() {
                @Override
                public void run() {
                    asyncDao.updateGamesPlayed(player,games);
                }
            });
        }
    }
}
