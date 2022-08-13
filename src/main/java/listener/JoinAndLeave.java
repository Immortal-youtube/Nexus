package listener;

import commands.MongoDB.MongoDBConnector;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class JoinAndLeave extends ListenerAdapter {


    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        String name = event.getMember().getAsMention();
        TextChannel channel = event.getJDA().getTextChannelById(System.getenv("WELCOME"));
        channel.sendMessage("Welcome " + name).queue();
        event.getGuild().addRoleToMember(event.getMember(),event.getGuild().getRoleById(System.getenv("MEMBER"))).complete();
        try {
            MongoDBConnector.add(event.getUser().getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        String name = event.getUser().getAsTag();
        TextChannel channel = event.getJDA().getTextChannelById(System.getenv("FAREWELL"));
        System.out.println(name + " left");
        channel.sendMessage("Goodbye " + name).queue();
    }
}
