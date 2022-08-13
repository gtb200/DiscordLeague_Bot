package com.example.botdemo.biz;

import com.example.botdemo.biz.classes.DiscordUser;
import com.example.botdemo.biz.classes.GameStats;
import com.example.botdemo.biz.classes.LeagueUser;
import com.example.botdemo.biz.classes.MessageBuilder;
import com.example.botdemo.biz.converters.LeagueRecordToUser;
import com.example.botdemo.biz.entities.LeagueSubscription;
import com.example.botdemo.biz.entities.LeagueUserRecord;
import com.example.botdemo.biz.repositories.LeagueSubscriptionRepository;
import com.example.botdemo.biz.repositories.LeagueUserRecordRepository;
import com.example.botdemo.biz.services.GameLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@EnableScheduling
public class ApplicationManager implements CommandLineRunner {

    private static GameLogService sGameLogService;

    private static LeagueSubscriptionRepository sLeagueSubscriptionRepository;

    private static LeagueUserRecordRepository sLeagueUserRecordRepository;

    @Autowired
    private GameLogService gameLogService;

    @Autowired
    private LeagueSubscriptionRepository leagueSubscriptionRepository;

    @Autowired
    private LeagueUserRecordRepository leagueUserRecordRepository;

    @PostConstruct
    public void init() {
        ApplicationManager.sLeagueSubscriptionRepository = leagueSubscriptionRepository;
        ApplicationManager.sLeagueUserRecordRepository = leagueUserRecordRepository;
        ApplicationManager.sGameLogService = gameLogService;

    }

    @Override
    public void run(String... args) throws Exception {
        main(args);
    }

    public static void main(String[] args) {

    }

    @Scheduled(fixedDelay = 1000*120)
    public static void soloQueueRoutine(){
        DiscordUser discordUser = null;
        MessageBuilder messageBuilder = new MessageBuilder();

        for (LeagueUserRecord leagueUserRecord : sLeagueUserRecordRepository.findAll()) {
            LeagueUser leagueUser = LeagueRecordToUser.convertRecord(leagueUserRecord);
            List<LeagueSubscription> subscriptionList = sLeagueSubscriptionRepository.findByLeagueUser(leagueUserRecord.getUserName());

            GameStats gameStats=leagueUser.updateSoloRanked();
            if(gameStats!=null){
                sGameLogService.logSoloRanked(leagueUser,gameStats);

                for (LeagueSubscription leagueSubscription : subscriptionList) {
                    discordUser = new DiscordUser(leagueSubscription.getDiscordUser());

                    discordUser.send(messageBuilder.soloRankedMessage(gameStats));
                }
            }
            sLeagueUserRecordRepository.save(LeagueRecordToUser.convertUser(leagueUser));
        }
    }
}
