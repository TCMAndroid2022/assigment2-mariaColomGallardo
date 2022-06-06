package com.example.mobileapps.practica2.fernandez_pablo.schneegluth_pablo.colom_maria.randomwords.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "score")
public class Score {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nickname")
    @NonNull
    private String nickname;

    @ColumnInfo(name = "score")
    @NonNull
    private float score;

    public Score() {}

    public Score(String nick, float score) {
        this.nickname = nick;
        this.score = score;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id;}

    public String getNickname() {
        return nickname;
    }

    public float getScore() {
        return score;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
