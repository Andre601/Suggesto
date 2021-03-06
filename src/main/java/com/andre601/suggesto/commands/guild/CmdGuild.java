package com.andre601.suggesto.commands.guild;

import com.andre601.suggesto.utils.EmbedUtil;
import com.andre601.suggesto.utils.MessageUtil;
import com.github.rainestormee.jdacommand.Command;
import com.github.rainestormee.jdacommand.CommandAttribute;
import com.github.rainestormee.jdacommand.CommandDescription;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import java.text.MessageFormat;
import java.time.LocalDateTime;

@CommandDescription(
        name = "Guild",
        description = "Gives information about the guild.",
        triggers = {"guild", "server"},
        attributes = {
                @CommandAttribute(key = "guild")
        }
)
public class CmdGuild implements Command {

    @Override
    public void execute(Message msg, String s) {
        Guild guild = msg.getGuild();
        TextChannel tc = msg.getTextChannel();

        EmbedBuilder guildInfo = EmbedUtil.getEmbed(msg.getAuthor())
                .setTitle(guild.getName())
                .setThumbnail(guild.getIconUrl())
                .addField("Users", String.format(
                        "**Total**: %d\n" +
                        "\n" +
                        "**Humans**: %d\n" +
                        "**Bots**: %d",
                        guild.getMembers().size(),
                        guild.getMembers().stream().filter(user -> !user.getUser().isBot()).count(),
                        guild.getMembers().stream().filter(user -> user.getUser().isBot()).count()
                ), true)
                .addField("Region", String.format(
                        "%s %s",
                        guild.getRegion().getEmoji(),
                        guild.getRegion().getName()
                ), true)
                .addField("Level", guild.getVerificationLevel().name().toLowerCase(), true)
                .addField("Owner", String.format(
                        "%s | %s",
                        guild.getOwner().getAsMention(),
                        MessageUtil.getTag(guild.getOwner().getUser())
                ), true)
                .addField("Created", String.format(
                        "`%s`",
                        MessageUtil.formatTime(LocalDateTime.from(guild.getCreationTime()))
                ), true);

        tc.sendMessage(guildInfo.build()).queue();
    }
}
