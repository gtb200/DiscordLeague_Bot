package com.example.botdemo.biz.converters;

import com.example.botdemo.biz.classes.LeagueUser;
import com.example.botdemo.biz.entities.LeagueUserRecord;

public class LeagueRecordToUser {


    static public LeagueUser convertRecord(LeagueUserRecord leagueUserRecord){
        LeagueUser leagueUser = new LeagueUser();
        leagueUser.setSummoner(leagueUserRecord.getSummoner());
        leagueUser.setSoloQueueLP(leagueUserRecord.getSoloQueueLP());
        leagueUser.setLastSoloQueueGameId(leagueUserRecord.getLastSoloQueueGameId());

        return leagueUser;
    }

    static public LeagueUserRecord convertUser(LeagueUser leagueUser){
        LeagueUserRecord leagueUserRecord = new LeagueUserRecord();
        leagueUserRecord.setId(leagueUser.getSummoner().getName());
        leagueUserRecord.setSummoner(leagueUser.getSummoner());
        leagueUserRecord.setSoloQueueLP(leagueUser.getSoloQueueLP());
        leagueUserRecord.setLastSoloQueueGameId(leagueUser.getLastSoloQueueGameId());

        return leagueUserRecord;
    }
}
