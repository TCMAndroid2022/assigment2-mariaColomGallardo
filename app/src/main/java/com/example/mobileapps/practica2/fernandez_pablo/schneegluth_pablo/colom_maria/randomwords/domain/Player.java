package com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "player")
public class Player {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "nickname")
    private String nickname;

    @ColumnInfo(name = "gamesPlayed")
    private int gamesPlayed;

    @ColumnInfo(name = "score")
    private float score;

    public Player() {}

    public Player(String nick) {
        nickname = nick;
        gamesPlayed = 0;
        score = 0;
    }

    public String getNickname() {
        return nickname;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public float getScore() {
        return score;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
