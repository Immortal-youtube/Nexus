package commands.lavaplayer;

import commands.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URISyntaxException;

public class MusicPlayer extends ListenerAdapter {

    TextChannel channel;
    String link;
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
                link = mapping.getAsString();
                if(!isUrl(link)) {
                    if(PlayerManager.getINSTANCE().isPlaying()) {
                        PlayerManager.getINSTANCE().clear();
                    }
                    link = "ytsearch:" + link + " official audio";
                    PlayerManager.getINSTANCE().loadAndPlay((TextChannel) event.getChannel(),link);
                    final AudioManager manager = event.getGuild().getAudioManager();
                    final AudioChannel channel = event.getMember().getVoiceState().getChannel();
                    manager.openAudioConnection(channel);
                    event.deferReply().queue();
                    event.getHook().sendMessage("Now playing " + link).queue();
                }else{
                    if(PlayerManager.getINSTANCE().isPlaying()){
                        PlayerManager.getINSTANCE().clear();;
                    }
                    final AudioManager manager = event.getGuild().getAudioManager();
                    final AudioChannel channel = event.getMember().getVoiceState().getChannel();
                    PlayerManager.getINSTANCE().loadAndPlay((TextChannel) event.getChannel(),link);
                    manager.openAudioConnection(channel);
                    event.deferReply().queue();
                    event.getHook().sendMessage("Now playing " + link).queue();
                }
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
        if(event.getName().equals("pause")){
            if(event.getGuild().getAudioManager().isConnected()){
                PlayerManager.getINSTANCE().getGuildMusicManager(event.getGuild()).scheduler.pauseTrack();
                event.deferReply().queue();
                event.getHook().sendMessage("Paused the music").queue();
            }else{
                event.getHook().sendMessage("I am not playing anything").queue();
            }
        }
        if(event.getName().equals("resume")){
            if(event.getGuild().getAudioManager().isConnected()){
                PlayerManager.getINSTANCE().getGuildMusicManager(event.getGuild()).scheduler.resumeTrack();
                event.deferReply().queue();
                event.getHook().sendMessage("Resumed the music").queue();
            }else{
                event.getHook().sendMessage("I am not playing anything").queue();
            }
        }
    }

    public boolean isUrl(String url){try{
            new URI(url);
            return true;
        }catch (URISyntaxException e){
            return false;
        }
    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        if(event.getGuild().getAudioManager().getConnectedChannel().getMembers().size() == 1){
            event.getGuild().getAudioManager().closeAudioConnection();
            PlayerManager.getINSTANCE().getGuildMusicManager(event.getGuild()).scheduler.stopTrack();
            PlayerManager.getINSTANCE().getGuildMusicManager(event.getGuild()).scheduler.clearQueue();
        }
    }
}
