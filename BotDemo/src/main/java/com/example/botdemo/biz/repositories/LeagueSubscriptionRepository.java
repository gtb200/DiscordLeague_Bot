package com.example.botdemo.biz.repositories;

import com.example.botdemo.biz.entities.LeagueSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LeagueSubscriptionRepository extends JpaRepository<LeagueSubscription, Long> {
    @Query("select l from LeagueSubscription l where l.leagueUser.userName = ?1")
    List<LeagueSubscription> findByLeagueUser(String leagueUser);

    @Query("""
            select (count(l) > 0) from LeagueSubscription l
            where upper(l.leagueUser.userName) = upper(?1) and upper(l.discordUser) = upper(?2)""")
    boolean doesSubscriptionExist(String leagueUser, String discordUser);

    @Transactional
    @Modifying
    @Query("delete from LeagueSubscription l where l.discordUser = ?1 and l.leagueUser.userName = ?2")
    int deleteSubscription(String discordUser, String leagueUser);




}