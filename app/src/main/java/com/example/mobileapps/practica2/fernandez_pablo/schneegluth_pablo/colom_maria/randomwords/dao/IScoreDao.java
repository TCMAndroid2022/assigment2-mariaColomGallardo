package com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.domain.Score;

import java.util.List;

@Dao
public interface IScoreDao {

    @Query("SELECT * FROM score WHERE nickname = :nickname ")
    List<Score> getScoresPlayer(String nickname);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertScore(Score score);
}
