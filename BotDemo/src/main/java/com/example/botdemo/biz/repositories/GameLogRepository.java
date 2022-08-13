package com.example.botdemo.biz.repositories;

import com.example.botdemo.biz.entities.GameLog;
import com.example.botdemo.biz.entities.LeagueUserRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameLogRepository extends JpaRepository<GameLog, LeagueUserRecord> {
    @Query("select g from GameLog g where g.leagueUserRecord.userName = ?1")
    List<GameLog> findByLeagueUserName(String userName, Pageable pageable);


}