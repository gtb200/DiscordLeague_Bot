package com.example.botdemo.biz.entities;

import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "league_user_record")
public class LeagueUserRecord {
    @Id
    @Column(name = "username", nullable = false)
    private String userName;

    private Summoner summoner;
    private int soloQueueLP;
    private String lastSoloQueueGameId;

    public String getId() {
        return userName;
    }

    public void setId(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Summoner getSummoner() {
        return summoner;
    }

    public void setSummoner(Summoner summoner) {
        this.summoner = summoner;
    }

    public int getSoloQueueLP() {
        return soloQueueLP;
    }

    public void setSoloQueueLP(int soloQueueLP) {
        this.soloQueueLP = soloQueueLP;
    }

    public String getLastSoloQueueGameId() {
        return lastSoloQueueGameId;
    }

    public void setLastSoloQueueGameId(String lastSoloQueueGameId) {
        this.lastSoloQueueGameId = lastSoloQueueGameId;
    }
}