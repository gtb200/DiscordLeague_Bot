package com.example.botdemo.biz.converters;

import com.example.botdemo.biz.classes.GameStats;
import com.example.botdemo.biz.entities.GameStatsModel;

public class GameStatsToModel {

    static public GameStatsModel toModel(GameStats gameStats){
        GameStatsModel gameStatsModel = new GameStatsModel();
        gameStatsModel.setMatch(gameStats.getMatch());
        gameStatsModel.setParticipant(gameStats.getParticipant());
        gameStatsModel.setNewLP(gameStats.getNewLP());
        gameStatsModel.setOldLP(gameStats.getOldLP());

        return gameStatsModel;
    }
}
