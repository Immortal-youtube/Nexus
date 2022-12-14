package listener;

import Utils.Welcome;
import commands.MongoDB.MongoDBConnector;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
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
        TextChannel channel = event.getJDA().getTextChannelById(System.getenv("WELCOME_CHAT"));
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Welcome " + event.getMember().getEffectiveName());
        builder.setThumbnail(event.getUser().getAvatarUrl());
        builder.setColor(event.getMember().getColor());
        channel.sendMessageEmbeds(builder.build()).queue();
        event.getGuild().addRoleToMember(event.getMember(),event.getGuild().getRoleById(System.getenv("MEMBER"))).complete();
        try {
            MongoDBConnector.add(event.getUser().getId());
            Welcome.onJoin(event);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        String name = event.getUser().getAsTag();
        TextChannel channel = event.getJDA().getTextChannelById(System.getenv("FAREWELL_CHAT"));
        System.out.println(name + " left");
        channel.sendMessage("Goodbye " + name).queue();
    }
}
