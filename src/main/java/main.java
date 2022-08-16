import commands.*;
import commands.lavaplayer.MusicPlayer;
import listener.JoinAndLeave;
import listener.SuggestionListener;
import listener.SusFile;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class main{
    static String token;

    public static void main(String[] args) throws LoginException, InterruptedException{
        token = System.getenv("TOKEN");
        JDA jda = JDABuilder.createDefault(token)
                .enableCache(CacheFlag.VOICE_STATE)
                .setActivity(Activity.playing("with yo Mama"))
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .enableIntents(GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.DIRECT_MESSAGES,
                        GatewayIntent.GUILD_VOICE_STATES)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build().awaitReady();
        jda.addEventListener(new JoinAndLeave());
        jda.addEventListener(new SusFile());
        jda.addEventListener(new Role());
        jda.addEventListener(new MusicPlayer());
        jda.addEventListener(new Sus());
        jda.addEventListener(new Jokes());
        jda.addEventListener(new Socials());
        jda.addEventListener(new ProjectIdeas());
        jda.addEventListener(new RPS());
        jda.addEventListener(new SuggestionListener());


        jda.upsertCommand(System.getenv("SUS"),"Sus").queue();
        jda.upsertCommand("invite","Wanna invite someone?").queue();
        jda.upsertCommand("clear","Clears first 20 messages").queue();
        jda.upsertCommand(System.getenv("SUS_COUNT"),"See the Sus amount").queue();
        jda.upsertCommand("mute","mute a player").addOption(OptionType.USER,"user","option pls",true).queue();
        jda.upsertCommand("unmute","unmute a user").addOption(OptionType.USER,"user","option pls",true).queue();
        jda.upsertCommand("play","play a song").addOption(OptionType.STRING,"link","option pls",true).queue();
        jda.upsertCommand("stop","stop the music").queue();
        jda.upsertCommand("joke","tell a joke").queue();
        jda.upsertCommand("github","github link").queue();
        jda.upsertCommand("bot","bot code").queue();
        jda.upsertCommand("idea","project idea").addOption(OptionType.STRING,"idea","option pls",true).queue();
        jda.upsertCommand("rps","play rock paper scissors").queue();
    }


}
