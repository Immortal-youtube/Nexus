package commands.lavaplayer;

import com.sun.tools.javac.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

public class MusicPlayer extends ListenerAdapter {

    TextChannel channel;
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals("play")){
            channel = event.getChannel().asTextChannel();
            if(!event.getMember().getVoiceState().inAudioChannel()){
                event.deferReply().queue();
                event.getHook().sendMessage("You must be in a voice channel to use this command.").queue();
            }else if(!event.getGuild().getSelfMember().hasPermission(Permission.VOICE_CONNECT)){
                event.deferReply().queue();
                event.getHook().sendMessage("I don't have permission to connect to a voice channel.").queue();
            }else{
                OptionMapping mapping = event.getOption("link");
                String link = mapping.getAsString();
                final AudioManager manager = event.getGuild().getAudioManager();
                final AudioChannel channel = event.getMember().getVoiceState().getChannel();
                PlayerManager.getINSTANCE().loadAndPlay((TextChannel) event.getChannel(),link);
                manager.openAudioConnection(channel);
                event.deferReply().queue();
                event.getHook().sendMessage("Now playing " + link).queue();
            }
        }
        if(event.getName().equals("stop")){
            if(event.getGuild().getAudioManager().isConnected()){
                PlayerManager.getINSTANCE().getGuildMusicManager(event.getGuild()).scheduler.stopTrack();
                PlayerManager.getINSTANCE().getGuildMusicManager(event.getGuild()).scheduler.clearQueue();
                event.getGuild().getAudioManager().closeAudioConnection();
                event.deferReply().queue();
                event.getHook().sendMessage("Stopped the music").queue();
            }else{
                event.getHook().sendMessage("I am not playing anything").queue();
            }
        }
    }
}
