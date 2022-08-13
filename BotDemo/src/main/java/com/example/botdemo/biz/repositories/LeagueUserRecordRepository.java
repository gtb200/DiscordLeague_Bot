package com.example.botdemo.biz.repositories;

import com.example.botdemo.biz.entities.LeagueUserRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LeagueUserRecordRepository extends JpaRepository<LeagueUserRecord, String> {
    @Query("select l from LeagueUserRecord l where l.userName = ?1")
    LeagueUserRecord findByUserName(String userName);

    @Transactional
    @Modifying
    @Query("delete from LeagueUserRecord l where l.userName = ?1")
    int deleteByUserName(String userName);



}