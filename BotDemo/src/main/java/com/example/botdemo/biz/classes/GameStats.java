package com.example.botdemo.biz.classes;

import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch;
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant;

public class GameStats {
    private LOLMatch match;
    private MatchParticipant participant;

    private int oldLP,newLP;

    GameStats(){}

    public GameStats(LOLMatch match, MatchParticipant participant) {
        this.match = match;
        this.participant = participant;

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
}
