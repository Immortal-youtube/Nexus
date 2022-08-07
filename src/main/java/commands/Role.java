package commands;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.internal.requests.Route;
import org.jetbrains.annotations.NotNull;

import javax.swing.text.html.Option;

public class Role extends ListenerAdapter {
    Dotenv dotenv = Dotenv.load();
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals("mute")) {
            if(!event.getMember().getRoles().contains(event.getGuild().getRoleById(dotenv.get("GOD")))) {
                event.deferReply().queue();
                event.getHook().sendMessage("You don't have the necessary permissions").queue();
                return;
            }

            OptionMapping map = event.getOption("user");
            if(map == null){
                event.deferReply(true).queue();
                event.getHook().sendMessage("You need to specify a user to mute").queue();
            }else{
                User user = map.getAsUser();
                Member member = event.getGuild().getMember(user);
                if(member.getRoles().contains(event.getGuild().getRoleById(dotenv.get("MUTE")))){
                    event.deferReply(true).queue();
                    event.getHook().sendMessage("This user is already muted").queue();
                }else{
                    event.getGuild().addRoleToMember(member, event.getGuild().getRoleById(dotenv.get("MUTE"))).queue();
                    event.deferReply(true).queue();
                    event.getHook().sendMessage("Muted " + user.getAsMention()).queue();
                }
            }

        }
        else if(event.getName().equals("unmute")){
            if(!event.getMember().getRoles().contains(event.getGuild().getRoleById(dotenv.get("GOD")))) {
                event.deferReply(true).queue();
                event.getHook().sendMessage("You don't have the necessary permissions").queue();
                return;
            }
            OptionMapping map = event.getOption("user");
            if(map == null){
                event.deferReply(true).queue();
                event.getHook().sendMessage("You need to specify a user to unmute").queue();

            }else {
                User user = map.getAsUser();
                Member member = event.getGuild().getMember(user);
                if(event.getMember().getRoles().contains(event.getGuild().getRoleById(dotenv.get("MUTE")))){
                    event.deferReply(true).queue();
                    event.getHook().sendMessage("This user is not muted").queue();
                    return;
                }else{
                    event.getGuild().removeRoleFromMember(member, event.getGuild().getRoleById(dotenv.get("MUTE"))).queue();
                    event.deferReply(true).queue();
                    event.getHook().sendMessage("Unmuted " + user.getAsMention()).queue();
                }

            }
        }
    }
}
