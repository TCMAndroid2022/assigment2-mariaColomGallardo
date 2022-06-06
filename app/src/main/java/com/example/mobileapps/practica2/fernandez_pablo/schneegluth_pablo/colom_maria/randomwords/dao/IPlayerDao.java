package com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.domain.Player;

import java.util.List;

@Dao
public interface IPlayerDao {

    @Query("SELECT * FROM player ORDER BY score DESC")
    LiveData<List<Player>> getAllPlayers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlayer(Player player);

    @Query("SELECT * FROM player WHERE nickname = :nick")
    LiveData<Player> getUser(String nick);

    @Query("SELECT score FROM player WHERE nickname = :nick")
    float getScore(String nick);

    @Query("SELECT gamesPlayed FROM player WHERE nickname = :nick")
    int getGamesPlayed(String nick);

    @Query("UPDATE player SET score = :score WHERE nickname = :nickname")
    void updateScore(String nickname, float score);

    @Query("UPDATE player SET gamesPlayed = :games WHERE nickname = :nickname")
    void updateGamesPlayed(String nickname, int games);

}
