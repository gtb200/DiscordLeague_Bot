package com.example.botdemo.biz.entities;

import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch;
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant;

import javax.persistence.*;

@Entity
@Table(name = "game_stats_model")
public class GameStatsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private LOLMatch match;
    private MatchParticipant participant;

    private int oldLP,newLP;


    public GameStatsModel() {

    }

    public LOLMatch getMatch() {
        return match;
    }

    public void setMatch(LOLMatch match) {
        this.match = match;
    }

    public MatchParticipant getParticipant() {
        return participant;
    }

    public void setParticipant(MatchParticipant participant) {
        this.participant = participant;
    }

    public int getOldLP() {
        return oldLP;
    }

    public void setOldLP(int oldLP) {
        this.oldLP = oldLP;
    }

    public int getNewLP() {
        return newLP;
    }

    public void setNewLP(int newLP) {
        this.newLP = newLP;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}