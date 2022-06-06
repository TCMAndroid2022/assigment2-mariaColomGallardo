package com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.daoImpl;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.database.AppDatabase;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.dao.IScoreDao;
import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.domain.Score;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ScoreDao {
    private IScoreDao scoreDao;
    AppDatabase db;

    public ScoreDao(Application application) {
        db = AppDatabase.getDatabase(application);
        scoreDao = db.scoreDao();
    }

    public void insertScore(Score score) {
        new asyncTask(scoreDao).execute(score);
    }

    public List<Score> getScoresPlayer(String nickname) {
       List<Score> scores = scoreDao.getScoresPlayer(nickname);
       return scores;
    }

    private static class asyncTask {
        private IScoreDao asyncDao;
        private Executor executor = Executors.newSingleThreadExecutor();

        asyncTask(IScoreDao dao) {
            asyncDao = dao;
        }

        public void execute(Score score) {
            this.doInBackground(score);
        }

        private void doInBackground(final Score score) {
            this.executor.execute(new Runnable() {
                @Override
                public void run() {
                    asyncDao.insertScore(score);
                }
            });
        }
    }
}
