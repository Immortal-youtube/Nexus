package commands;

import commands.MongoDB.JokesConnector;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Jokes extends ListenerAdapter {
    String str;
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals("joke")){
            String fin = JokesConnector.joke();

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Joke");
            embedBuilder.addField(fin, "", false);
            embedBuilder.setColor(Color.GREEN);
            embedBuilder.setThumbnail("https://media.gq.com/photos/5720e7496a2af99a11a1dc8a/16:9/w_2560%2Cc_limit/Obama-Laugh.jpg");
            event.deferReply().queue();
            event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();

        }

    }
}
