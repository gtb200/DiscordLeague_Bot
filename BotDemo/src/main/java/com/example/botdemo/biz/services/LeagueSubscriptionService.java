package com.example.botdemo.biz.services;

import com.example.botdemo.biz.ApplicationManager;
import com.example.botdemo.biz.classes.LeagueUser;
import com.example.botdemo.biz.converters.LeagueRecordToUser;
import com.example.botdemo.biz.entities.LeagueSubscription;
import com.example.botdemo.biz.repositories.LeagueSubscriptionRepository;
import com.example.botdemo.biz.repositories.LeagueUserRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueSubscriptionService {
    protected static final Logger logger = LoggerFactory.getLogger(LeagueSubscriptionService.class);

    @Autowired
    private LeagueSubscriptionRepository leagueSubscriptionRepository;

    @Autowired
    private LeagueUserRecordRepository leagueUserRecordRepository;

    public boolean subscribe(String discordUser, String leagueUser) {
        try {
            if (leagueUserRecordRepository.findByUserName(leagueUser) == null) {
                LeagueUser newLeagueUser = new LeagueUser(leagueUser, "EUW1");
                leagueUserRecordRepository.save(new LeagueRecordToUser().convertUser(newLeagueUser));
            }
            if (!leagueSubscriptionRepository.doesSubscriptionExist(leagueUser, discordUser)) {
                LeagueSubscription leagueSubscription = new LeagueSubscription();
                leagueSubscription.setLeagueUser(leagueUserRecordRepository.findByUserName(leagueUser));
                leagueSubscription.setDiscordUser(discordUser);
                leagueSubscriptionRepository.save(leagueSubscription);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean unSubscribe(String discordUser, String leagueUser) {
        if (leagueSubscriptionRepository.doesSubscriptionExist(leagueUser, discordUser)) {
            leagueSubscriptionRepository.deleteSubscription(discordUser, leagueUser);
            if (leagueSubscriptionRepository.findByLeagueUser(leagueUser).isEmpty())
                leagueUserRecordRepository.deleteByUserName(leagueUser);
            return true;
        }
        return false;
    }
}