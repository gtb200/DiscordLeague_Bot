package com.example.botdemo.biz.classes;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class DiscordUser {

    protected static final Logger logger = LoggerFactory.getLogger(DiscordUser.class);
    protected static final JDA jda;

    static {
        try {
            jda = JDABuilder.create(System.getenv("JDA_KEY"),
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.GUILD_MEMBERS)
                    .build()
                    .awaitReady();
        } catch (InterruptedException | LoginException e) {
            logger.error("Unable to connect to discord");
            throw new RuntimeException(e);
        }
    }

    protected final User user;
    protected final String tag;

    public DiscordUser(String tag) {
        this.user = jda.getUserByTag(tag);
        if (this.user == null) {
            logger.error("Discord user not found: {}", tag);
            throw new RuntimeException();
        }
        this.tag = tag;
    }

    public void send(String msg) {
        user.openPrivateChannel().flatMap(pm -> pm.sendMessage(msg)).queue();
    }

    public void send(String msg, String embedTitle, String embedText) {
        user.openPrivateChannel().flatMap(pm -> pm.sendMessage(msg)
                        .setEmbeds(new EmbedBuilder()
                                .setTitle(embedTitle)
                                .setDescription(embedText)
                                .build()))
                .queue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiscordUser that = (DiscordUser) o;

        return tag.equals(that.tag);
    }

    @Override
    public int hashCode() {
        return tag.hashCode();
    }

    @Override
    public String toString() {
        return tag;
    }
}
