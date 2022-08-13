package com.example.botdemo.biz.classes;

import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant;

import java.util.Optional;
import java.util.Random;

public class MessageBuilder {

    protected final static String[] winVideos = {
            "https://www.youtube.com/watch?v=GGXzlRoNtHU",
            "https://www.youtube.com/watch?v=Kbj2Zss-5GY",
            "https://www.youtube.com/watch?v=tfSS1e3kYeo",
            "https://www.youtube.com/watch?v=6ONRf7h3Mdk"
    };

    public MessageBuilder() {
    }


    public String soloRankedWinMessage(GameStats gameStats) {
        return "\uD83D\uDE0E EPIC WIN!!!!!!!!!! (\uD83D\uDE2C " +
                gameStats.getParticipant().getChampionName().toUpperCase() +
                " \uD83D\uDE2C) \uD83D\uDE0E\n" +
                "\uD83D\uDE0E WINNER: " + gameStats.getParticipant().getSummonerName() + "\n" +
                "\uD83D\uDC40 VISION WARDS PLACED: " + gameStats.getParticipant().getVisionWardsBoughtInGame() + "\n" +
                winVideos[new Random().nextInt(winVideos.length)];
    }

    public String soloRankedMessage(GameStats gameStats){
        return (gameStats.getParticipant().didWin() ?
                soloRankedWinMessage(gameStats) :
                soloRankedLossMessage(gameStats)
        );
    }

    public String soloRankedLossMessage(GameStats gameStats) {
        MatchParticipant p = gameStats.getParticipant();
        return ffText(gameStats) +
                "⚠️ NEW LOSS  ⚠️\n" +
                "⚠️ OFFENDER: " + p.getSummonerName() + "\n" +
                lossLPReport(gameStats) +
                "⚠️ GRAY SCREEN TIMER: " + grayScreenTimer(p) + "\n" +
                "⚠️ GOBLIN AMOUNT: " + (p.getGoldEarned()- p.getGoldSpent()) + "💰\n" +
                "⚠️ " + p.getLane().prettyName().toUpperCase() + " DIF\n" +
                "⚠️ CHAMPION: " + p.getChampionName() + " \uD83E\uDD14\n" +
                kanyeQuote();
    }

    protected String grayScreenTimer(MatchParticipant p) {
        return String.format("%d:%02d:%02d", p.getTotalTimeSpentDead() / 3600, (p.getTotalTimeSpentDead() % 3600) / 60, (p.getTotalTimeSpentDead() % 60));
    }

    protected String lossLPReport(GameStats gameStats) {
        //cases:normal lostpromo
        //if(gameStats.getOldLP()==gameStats.getNewLP())
        //    return "";
        int gains = gameStats.getNewLP() - gameStats.getOldLP();

        return "⚠️ REPORT: " + (gains >= 0 ? "+" + gains : gains) + "LP \uD83D\uDE33\n" +
                "⚠️ OLD LP: " + gameStats.getOldLP() + "\n" +
                "⚠️ NEW LP: " + gameStats.getNewLP() + "\n";
    }
    protected String kanyeQuote() {
        Optional<String> quote = KanyeUtils.getQuote();
        if (quote.isPresent()) {
            return "\n> %s\n> **Kanye West**".formatted(quote.get());
        }
        return "";
    }

    protected String ffText(GameStats gameStats) {
        MatchParticipant p = gameStats.getParticipant();
        if (p.isGameEndedInSurrender()) {
            return "🚨🚨 SURRENDER DETECTED 🚨🚨" + (p.isGameEndedInEarlySurrender() ? " 😨 15 MINUTE 😨" : "") + "\n";
        }
        return "";
    }
}
