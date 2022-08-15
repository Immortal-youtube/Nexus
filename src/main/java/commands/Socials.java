package commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Socials extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals("github")){
            event.deferReply().queue();
            event.getHook().sendMessage("https://github.com/Immortal-youtube").queue();
        }
        if(event.getName().equals("bot")){
            event.deferReply().queue();
            event.getHook().sendMessage("https://github.com/Immortal-youtube/Nexus-BOT").queue();
        }
    }
}
