package com.andre601.suggesto.utils;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;

import java.util.Map;

public class Database {

    private static final RethinkDB r = RethinkDB.r;

    private static Connection conn = r.connection()
            .hostname("localhost")
            .port(28015)
            .db("suggesto")
            .connect();

    private static String guildTable     = PermUtil.isBeta() ? "guilds_beta" : "guilds";
    private static String ticketsTable   = PermUtil.isBeta() ? "tickets_beta" : "tickets";
    private static String statsTable     = "stats";

    public static void createDB(Guild g) {
        r.table(guildTable).insert(
                r.array(
                        r.hashMap("id", g.getId())
                                .with("ticket_id", 1)
                                .with("ticket_channel", "none")
                                .with("ticket_category", "none")
                                .with("prefix", "t_")
                                .with("staff_role", "none")
                                .with("log_channel", "none")
                                .with("dm", "on")
                )
        ).optArg("conflict", "update").run(conn);
    }

    private static Map<String, Object> getGuild(Guild g){
        return r.table(guildTable).get(g.getId()).run(conn);
    }

    public static boolean hasGuild(Guild guild){
        return getGuild(guild) != null;
    }

    public static Long getNewTicketID(Guild guild){
        Map g = getGuild(guild);
        long id = Long.valueOf(g.get("ticket_id").toString());
        updateTicketID(guild, id);
        return id;
    }

    public static String getRoleID(Guild guild){
        Map g = getGuild(guild);
        return g.get("staff_role").toString();
    }

    public static void setRole(Guild guild, String roleID){
        r.table(guildTable).get(guild.getId()).update(r.hashMap("staff_role", roleID)).run(conn);
    }

    public static void saveTicket(String ChannelID, String msgID, String authorID, Long ticketID){
        r.table(ticketsTable).insert(
                r.array(
                        r.hashMap("id", ChannelID)
                                .with("message_id", msgID)
                                .with("author_id", authorID)
                                .with("ticket_id", ticketID)
                )
        ).optArg("conflict", "update").run(conn);
    }

    private static Map<String, Object> getTicket(String channelID){
        return r.table(ticketsTable).get(channelID).run(conn);
    }

    public static Long getCurrTicketID(Guild guild){
        Map g = getGuild(guild);
        return Long.valueOf(g.get("ticket_id").toString()) - 1;
    }

    private static void updateTicketID(Guild guild, Long id){
        long ticketID = id;
        ticketID++;
        updateTotalTickets();

        r.table(guildTable).get(guild.getId()).update(r.hashMap("ticket_id", ticketID)).run(conn);
    }

    public static void deleteTicket(String channelID){
        r.table(ticketsTable).get(channelID).delete().run(conn);
    }

    public static void deleteGuild(Guild guild){
        r.table(guildTable).get(guild.getId()).delete().run(conn);
    }

    public static String getMessageID(String channelID){
        Map channel = getTicket(channelID);
        return channel.get("message_id").toString();
    }

    public static String getAuthorID(String channelID){
        Map channel = getTicket(channelID);
        return channel.get("author_id").toString();
    }

    public static String getPrefix(Guild g){
        Map guild = getGuild(g);
        return guild.get("prefix").toString();
    }

    public static void setPrefix(Guild guild, String prefix){
        r.table(guildTable).get(guild.getId()).update(r.hashMap("prefix", prefix)).run(conn);
    }

    public static boolean hasPrefix(Message msg, Guild guild){
        if(msg.getContentRaw().startsWith(getPrefix(guild)))
            return true;

        return msg.getContentRaw().startsWith(guild.getSelfMember().getAsMention());
    }

    public static String getSupportChannel(Guild g){
        Map guild = getGuild(g);
        return guild.get("ticket_channel").toString();
    }

    public static void setSupportChannel(Guild guild, String id){
        r.table(guildTable).get(guild.getId()).update(r.hashMap("ticket_channel", id)).run(conn);
    }

    public static String getCategory(Guild g){
        Map guild = getGuild(g);
        return guild.get("ticket_category").toString();
    }

    public static void setCategory(Guild guild, String id){
        r.table(guildTable).get(guild.getId()).update(r.hashMap("ticket_category", id)).run(conn);
    }

    public static boolean hasTicket(String id){
        return getTicket(id) != null;
    }

    private static Map<String, Object> getStats(String key){
        return r.table(statsTable).get(key).run(conn);
    }

    public static boolean hasLogChannel(Guild guild){
        Map g = getGuild(guild);
        return g.get("log_channel") != null;
    }

    public static void setLogChannel(Guild guild, String id){
        r.table(guildTable).get(guild.getId()).update(r.hashMap("log_channel", id)).run(conn);
    }

    public static String getLogChannel(Guild guild){
        if(!hasLogChannel(guild)) return "none";

        Map g = getGuild(guild);
        return g.get("log_channel").toString();
    }

    public static String getDMSetting(Guild guild){
        Map g = getGuild(guild);

        if(g.get("dm") == null) return "on";

        return g.get("dm").toString();
    }

    public static void setDMSettings(Guild guild, String setting){
        r.table(guildTable).get(guild.getId()).update(r.hashMap("dm", setting)).run(conn);
    }

    public static String getTotalTickets(){
        Map stats = getStats("totalTickets");
        return stats.get(PermUtil.isBeta() ? "ticketsBeta" : "tickets").toString();
    }

    public static void updateTotalTickets(){
        Map stats = getStats("totalTickets");
        long tickets = Long.valueOf(stats.get(PermUtil.isBeta() ? "ticketsBeta" : "tickets").toString());
        tickets++;

        r.table(statsTable).get("totalTickets").update(r.hashMap(PermUtil.isBeta() ? "ticketsBeta" : "tickets",
                tickets)).run(conn);
    }
}
