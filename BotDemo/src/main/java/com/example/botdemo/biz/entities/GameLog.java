package com.example.botdemo.biz.entities;

import com.example.botdemo.biz.classes.GameStats;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "game_log")
public class GameLog{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    private LeagueUserRecord leagueUserRecord;

    @OneToOne
    private GameStatsModel gameStats;

    @Column(name = "date", columnDefinition = "TIMESTAMP")
    private LocalDateTime localDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameLog() {
    }

    public LeagueUserRecord getLeagueUserRecord() {
        return leagueUserRecord;
    }

    public void setLeagueUserRecord(LeagueUserRecord leagueUserRecord) {
        this.leagueUserRecord = leagueUserRecord;
    }

    public GameStatsModel getGameStats() {
        return gameStats;
    }

    public void setGameStats(GameStatsModel gameStats) {
        this.gameStats = gameStats;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}