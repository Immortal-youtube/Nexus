package commands;

import commands.MongoDB.MongoDBConnector;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class Sus extends ListenerAdapter {
    Random random = new Random();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(System.getenv("SUS"))){
            event.deferReply().queue();
            event.getHook().sendMessage(System.getenv("SUS_MESSAGE")).queue();
            try {
                MongoDBConnector.update(event.getUser().getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else if(event.getName().equals("invite")){
            event.deferReply().queue();
            event.getHook().sendMessage("If you wish to invite someone: " + event.getChannel().asTextChannel().createInvite().complete().getUrl()).queue();
        }
        else if(event.getName().equals("clear")){
            List<Role> r = event.getMember().getRoles();
            if(r.contains(event.getGuild().getRoleById(System.getenv("GOD")))){
                List<Message> l = event.getChannel().getHistory().retrievePast(20).complete();
                event.reply("Done sir").queue();
                event.getChannel().asTextChannel().deleteMessages(l).queue();

            }else{
                event.deferReply().queue();
                event.getHook().sendMessage("You don't have the necessary permissions").queue();
            }
        }

        else if(event.getName().equals(System.getenv("SUS_COUNT"))){
            try {
                MongoDBConnector.see(event.getUser().getId(),event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
