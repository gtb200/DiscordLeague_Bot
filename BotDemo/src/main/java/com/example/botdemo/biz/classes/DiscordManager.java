package com.example.botdemo.biz.classes;

/*
 * Copyright 2015 Austin Keener, Michael Ritter, Florian Spieß, and the JDA contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.example.botdemo.biz.ApplicationManager;
import com.example.botdemo.biz.services.LeagueSubscriptionService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.EnumSet;

@Component
public class DiscordManager extends ListenerAdapter  implements CommandLineRunner
{

    protected static final Logger logger = LoggerFactory.getLogger(DiscordManager.class);
    // See https://emojipedia.org/red-heart/ and find the codepoints
    public static final Emoji HEART = Emoji.fromUnicode("U+2764");

    private static LeagueSubscriptionService sLeagueSubscriptionService;

    @Autowired
    private LeagueSubscriptionService leagueSubscriptionService;

    @PostConstruct
    public void init() {
        DiscordManager.sLeagueSubscriptionService = leagueSubscriptionService;
    }
    @Override
    public void run(String... args) throws Exception {
        main(args);
    }

    public static void main(String[] args) throws IOException
    {

        String token = System.getenv("JDA_KEY");

        EnumSet<GatewayIntent> intents = EnumSet.of(
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.DIRECT_MESSAGES,
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.DIRECT_MESSAGE_REACTIONS
        );
        try
        {
            JDA jda = JDABuilder.createLight(token, intents)
                    .addEventListeners(new DiscordManager())
                    .setActivity(Activity.watching("your messages"))
                    .build();

           jda.getRestPing().queue(ping ->
                    // shows ping in milliseconds
                    logger.info("Logged in with ping: " + ping)
            );
            jda.awaitReady();
            logger.info("Guilds: " + jda.getGuildCache().size());
        }
        catch (LoginException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event)
    {
        User author = event.getAuthor();
        MessageChannel channel = event.getChannel();

        Message message = event.getMessage();

        if (event.isFromGuild())
        {
            logger.info(String.format("[%s] [%#s] %#s: %s\n",
                    event.getGuild().getName(), // The name of the server the user sent the message in, this is generally referred to as "guild" in the API
                    channel, // The %#s makes use of the channel name and displays as something like #general
                    author,  // The %#s makes use of User#getAsTag which results in something like Minn#6688
                    message.getContentDisplay() // This removes any unwanted mention syntax and converts it to a readable string
            ));
        }
        else
        {
            String messageReceived = event.getMessage().getContentDisplay();
            logger.info(String.format("[direct] %s: %s\n",
                    author, // same as above
                    message.getContentDisplay()
            ));
            if(messageReceived.startsWith("!add")){
                addSubscription(event, message, messageReceived);
                return;
            }
            else if (messageReceived.startsWith("!remove")){
                unSubscribe(event, message, messageReceived);
                return;
            }
        }

        // Using specialization, you can check concrete types of the channel union

        if (channel.getType() == ChannelType.TEXT)
        {
            logger.info("The channel topic is " + message.getTextChannel().getTopic());
        }
        
        if (event.isFromThread())
        {
            logger.info("This thread is part of channel #" +
                    event.getThreadChannel()  // Cast the channel union to thread
                            .getParentChannel() // Get the parent of that thread, which is the channel it was created in (like forum or text channel)
                            .getName()          // And then print out the name of that channel
            );
        }
    }

    private void unSubscribe(@NotNull MessageReceivedEvent event, Message message, String messageReceived) {
        if (messageReceived.split(" ").length != 2){
            message.reply("!remove leagueUserName").queue();
            return;
        }
        String leagueUser = messageReceived.split(" ")[1];

        if(sLeagueSubscriptionService.unSubscribe(event.getAuthor().getAsTag(), leagueUser))
            message.reply("Unsubscribed successfully").queue();
        else
            message.reply("You are not subscribed to that summoner").queue();
    }

    private void addSubscription(@NotNull MessageReceivedEvent event, Message message, String messageReceived) {
        if (messageReceived.split(" ").length == 1){
            message.reply("\"!add leagueUserName\"").queue();
            return;
        }
        String leagueUser = messageReceived.replaceFirst("!add ","");

        if(sLeagueSubscriptionService.subscribe(event.getAuthor().getAsTag(), leagueUser))
            message.reply("Subscribed successfully").queue();
        else
            message.reply("League user does not exist or you are already subscribed to him").queue();
        return;
    }

    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event)
    {
        logger.info("event.getReactionEmote().getEmoji() = " + event.getReactionEmote().getEmoji());
        if (event.getReactionEmote().getEmoji().equals(HEART))
            logger.info("A user loved a message!");
    }
}
