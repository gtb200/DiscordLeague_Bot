package com.example.botdemo.biz.classes;

import com.example.botdemo.biz.classes.GameStats;
import no.stelar7.api.r4j.basic.APICredentials;
import no.stelar7.api.r4j.basic.calling.DataCall;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.impl.lol.builders.matchv5.match.MatchBuilder;
import no.stelar7.api.r4j.impl.lol.builders.summoner.SummonerBuilder;
import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch;
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class LeagueUser {
    protected static final Logger logger = LoggerFactory.getLogger(LeagueUser.class);

    protected static final R4J r4j = new R4J(new APICredentials(System.getenv("R4J_KEY")));

    protected Summoner summoner;
    private int soloQueueLP;
    private String lastSoloQueueGameId;
    private GameStats gameStats;

    static {
        DataCall.setCacheProvider(null);
    }

    public LeagueUser(){}

    public LeagueUser(String summonerName, String region) {
        summoner = new SummonerBuilder()
                .withPlatform(LeagueShard.fromString(region)
                        .orElseThrow())
                .withName(summonerName)
                .get();
        lastSoloQueueGameId = getLatestSoloRankedId();
        GameStats gameStats = new GameStats(getSoloRankedMatch(),getSoloRankedParticipant());
        gameStats.setOldLP(soloQueueLP);
        soloQueueLP = getUpdatedSoloLP();
        gameStats.setNewLP(soloQueueLP);

        this.gameStats = gameStats;
    }

    public GameStats fetchLatestSoloRankedGameStats(){
        GameStats gameStats = new GameStats(getSoloRankedMatch(),getSoloRankedParticipant());
        gameStats.setOldLP(soloQueueLP);
        gameStats.setNewLP(soloQueueLP);

        return gameStats;
    }

    public String getLatestSoloRankedId(){
        return summoner.getLeagueGames()
                .withQueue(GameQueueType.TEAM_BUILDER_RANKED_SOLO)
                .get()
                .stream()
                .findFirst()
                .orElseThrow();
    }

    public int getUpdatedSoloLP() {
        return summoner.getLeagueEntry()
                .stream()
                .filter(leagueEntry -> leagueEntry.getQueueType().equals(GameQueueType.RANKED_SOLO_5X5))
                .findFirst()
                .orElseThrow()
                .getLeaguePoints();

    }
    public boolean isThereNewSoloRankedGame(){
        String fetchedId = getLatestSoloRankedId();
        logger.info("isThereNewSoloRankedGame(): " + !Objects.equals(lastSoloQueueGameId, getLatestSoloRankedId()) +
                " soloIdSaved: "+lastSoloQueueGameId+" soloIdFetched:" + fetchedId);

        return !Objects.equals(lastSoloQueueGameId, fetchedId);
    }

    public GameStats updateSoloRanked(){
        logger.info("Solo queue updating league user = "+ this.summoner.getName() + " (In-Game = " +(summoner.getCurrentGame() != null ? "True" : "False")+")");

        if(!isThereNewSoloRankedGame())
            return null;

        lastSoloQueueGameId = getLatestSoloRankedId();
        GameStats gameStats = new GameStats(getSoloRankedMatch(),getSoloRankedParticipant());
        gameStats.setOldLP(soloQueueLP);
        soloQueueLP = getUpdatedSoloLP();
        gameStats.setNewLP(soloQueueLP);

        this.gameStats = gameStats;
        return gameStats;
    }

    public LOLMatch getSoloRankedMatch() {
        MatchBuilder mb = new MatchBuilder(summoner.getPlatform());
        return mb.withId(lastSoloQueueGameId)
                .getMatch();
    }

    public MatchParticipant getSoloRankedParticipant() {
        return getSoloRankedMatch()
                .getParticipants()
                .stream().filter(matchParticipant -> matchParticipant.getSummonerId().equals(summoner.getSummonerId()))
                .findFirst()
                .orElseThrow();
    }

    public GameStats getGameStats() {
        return gameStats;
    }

    public void setGameStats(GameStats gameStats) {
        this.gameStats = gameStats;
    }

    public Summoner getSummoner() {
        return summoner;
    }

    public int getSoloQueueLP() {
        return soloQueueLP;
    }

    public void setSoloQueueLP(int soloQueueLP) {
        this.soloQueueLP = soloQueueLP;
    }

    public String getLastSoloQueueGameId() {
        return lastSoloQueueGameId;
    }

    public void setLastSoloQueueGameId(String lastSoloQueueGameId) {
        this.lastSoloQueueGameId = lastSoloQueueGameId;
    }

    public void setSummoner(Summoner summoner) {
        this.summoner = summoner;
    }
}
