package com.example.botdemo.biz.entities;


import javax.persistence.*;
import java.lang.invoke.TypeDescriptor;

@Entity
@Table(name = "league_subscription")
public class LeagueSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "league_username")
    private LeagueUserRecord leagueUser;

    private String discordUser;


    public LeagueSubscription() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LeagueUserRecord getLeagueUser() {
        return leagueUser;
    }

    public void setLeagueUser(LeagueUserRecord leagueUser) {
        this.leagueUser = leagueUser;
    }

    public String getDiscordUser() {
        return discordUser;
    }

    public void setDiscordUser(String discordUser) {
        this.discordUser = discordUser;
    }
}