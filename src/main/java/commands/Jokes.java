package commands;

import io.chucknorris.client.ChuckNorrisClient;
import io.chucknorris.client.Joke;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Jokes extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals("joke")){
            ChuckNorrisClient client = new ChuckNorrisClient();
            Joke joke = client.getRandomJoke();
            event.deferReply().queue();
            event.getHook().sendMessage(joke.getValue()).queue();
        }
    }
}
