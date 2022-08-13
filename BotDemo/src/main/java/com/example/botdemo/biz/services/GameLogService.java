package com.example.botdemo.biz.services;

import com.example.botdemo.biz.classes.GameStats;
import com.example.botdemo.biz.classes.LeagueUser;
import com.example.botdemo.biz.converters.GameStatsToModel;
import com.example.botdemo.biz.converters.LeagueRecordToUser;
import com.example.botdemo.biz.entities.GameLog;
import com.example.botdemo.biz.entities.GameStatsModel;
import com.example.botdemo.biz.entities.LeagueUserRecord;
import com.example.botdemo.biz.repositories.GameLogRepository;
import com.example.botdemo.biz.repositories.GameStatsModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GameLogService {
    @Autowired
    private GameLogRepository gameLogRepository;

    @Autowired
    private GameStatsModelRepository gameStatsModelRepository;

    public void logSoloRanked(LeagueUser leagueUser, GameStats gameStats){
        GameLog gameLog = new GameLog();
        GameStatsModel gameStatsModel = GameStatsToModel.toModel(gameStats);
        gameLog.setGameStats(gameStatsModel);
        gameLog.setLeagueUserRecord(LeagueRecordToUser.convertUser(leagueUser));
        gameLog.setLocalDateTime(LocalDateTime.now());

        gameStatsModelRepository.save(gameStatsModel);
        gameLogRepository.save(gameLog);
    }

    public List<GameLog> fetchLast10Logs(String leagueUserName){
        return gameLogRepository.findByLeagueUserName(leagueUserName, Pageable.ofSize(10));
    }

}
