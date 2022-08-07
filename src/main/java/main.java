import commands.Role;
import commands.Sus;
import io.github.cdimascio.dotenv.Dotenv;
import listener.JoinAndLeave;
import listener.Msg;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;

public class main {

    public static void main(String[] args) throws LoginException, InterruptedException, SQLException {
        Dotenv dotenv = Dotenv.configure().load();
        String token = dotenv.get("TOKEN");
        JDA jda = JDABuilder.createDefault(token)
                .setActivity(Activity.playing("with yo Mama"))
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .enableIntents(GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.DIRECT_MESSAGES)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build().awaitReady();
        jda.addEventListener(new JoinAndLeave());
        jda.addEventListener(new Msg());
        jda.addEventListener(new Role());
        jda.addEventListener(new Sus());

        jda.upsertCommand(dotenv.get("SUS"),"Sus").queue();
        jda.upsertCommand("invite","Wanna invite someone?").queue();
        jda.upsertCommand("clear","Clears first 20 messages").queue();
        jda.upsertCommand(dotenv.get("SUS_COUNT"),"See the Sus amount").queue();
        jda.upsertCommand("rps","rock,paper,scissor anyone? ").addOption(OptionType.STRING,"name","enter your option",true).queue();
        jda.upsertCommand("mute","mute a player").addOption(OptionType.USER,"user","option pls",true).queue();
        jda.upsertCommand("unmute","unmute a user").addOption(OptionType.USER,"user","option pls",true).queue();

    }

}
