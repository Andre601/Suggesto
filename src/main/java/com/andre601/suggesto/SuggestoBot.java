package com.andre601.suggesto;

import ch.qos.logback.classic.Logger;
import com.andre601.suggesto.listener.CommandListener;
import com.andre601.suggesto.listener.ChannelListener;
import com.andre601.suggesto.listener.GuildListener;
import com.andre601.suggesto.listener.ReadyListener;
import com.andre601.suggesto.utils.MessageUtil;
import com.andre601.suggesto.utils.config.GFile;
import me.diax.comportment.jdacommand.CommandHandler;
import net.dv8tion.jda.bot.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SuggestoBot {

    private static JDABuilder builder = new JDABuilder(AccountType.BOT);
    private static JDA jda;
    private static Logger logger = (Logger) LoggerFactory.getLogger(SuggestoBot.class);

    private static GFile file = new GFile();

    private static final CommandHandler COMMAND_HANDLER = new CommandHandler();
    private static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) throws Exception{
        file.make("config", "./config.json", "/config.json");
        SuggestoBot.scheduler.scheduleAtFixedRate(MessageUtil.updatePresence(), 1, 5, TimeUnit.MINUTES);

        COMMAND_HANDLER.registerCommands(new CommandRegister().getCommands());

        new DefaultShardManagerBuilder()
                .setToken(file.getItem("config", "token"))
                .addEventListeners(
                        new CommandListener(COMMAND_HANDLER),
                        new ChannelListener(),
                        new ReadyListener(),
                        new GuildListener()
                )
                .setShardsTotal(-1)
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setGame(Game.playing("Starting bot..."))
                .build();
    }

    public static Logger getLogger(){
        return logger;
    }
}