package commands;

import commands.MongoDB.MongoDBConnector;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class Sus extends ListenerAdapter {
    Random random = new Random();
    Dotenv dotenv = Dotenv.configure().load();
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals(dotenv.get("SUS"))){
            event.deferReply().queue();
            event.getHook().sendMessage(dotenv.get("SUS_MESSAGE")).queue();
            try {
                MongoDBConnector.update(event.getUser().getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else if(event.getName().equals("invite")){
            event.deferReply().queue();
            TextChannel channel = (TextChannel) event.getChannel();
            event.getHook().sendMessage("If you wish to invite someone: " + channel.createInvite().complete().getUrl()).queue();
        }
        else if(event.getName().equals("clear")){
            List<Role> r = event.getMember().getRoles();
            if(r.contains(event.getGuild().getRoleById(dotenv.get("GOD")))){
                List<Message> l = event.getChannel().getHistory().retrievePast(20).complete();
                event.reply("Done sir").queue();
                event.getChannel().asTextChannel().deleteMessages(l).queue();

            }else{
                event.deferReply().queue();
                event.getHook().sendMessage("You don't have the necessary permissions").queue();
            }
        }

        else if(event.getName().equals(dotenv.get("SUS_COUNT"))){
            try {
                MongoDBConnector.see(event.getUser().getId(),event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else if(event.getName().equals("rps")){
            List<String> choices = List.of("rock", "paper", "scissor");
            String choice = choices.get(random.nextInt(choices.size()));

            System.out.println(choice);

            OptionMapping m = event.getOption("name");
            System.out.println(m);
            if(m == null){
                event.reply("invalid choice").queue();
                return;
            }else{
                String userchoice = m.getAsString();
                System.out.println(userchoice);
                if(userchoice.equalsIgnoreCase("rock") && choice.equalsIgnoreCase("scissor")) {
                    event.deferReply().queue();
                    event.getHook().sendMessage("Bot: " + choice + ". You win").queue();
                }else if(userchoice.equalsIgnoreCase("rock") && choice.equalsIgnoreCase("paper")) {
                    event.deferReply().queue();
                    event.getHook().sendMessage( "Bot: " + choice +  ". You lose").queue();
                }else if(userchoice.equalsIgnoreCase("rock") && choice.equalsIgnoreCase("rock")) {
                    event.deferReply().queue();
                    event.getHook().sendMessage("Bot: " + choice + ". It's a tie").queue();
                }else if(userchoice.equalsIgnoreCase("paper") && choice.equalsIgnoreCase("rock")) {
                    event.deferReply().queue();
                    event.getHook().sendMessage( "Bot: " + choice + ". You win").queue();
                }else if(userchoice.equalsIgnoreCase("paper") && choice.equalsIgnoreCase("scissor")) {
                    event.deferReply().queue();
                    event.getHook().sendMessage("Bot: " + choice + ". You lose").queue();
                }else if(userchoice.equalsIgnoreCase("paper") && choice.equalsIgnoreCase("paper")) {
                    event.deferReply().queue();
                    event.getHook().sendMessage("Bot: " + choice + ". It's a tie").queue();
                }
                else if(userchoice.equalsIgnoreCase("scissor") && choice.equalsIgnoreCase("rock")) {
                    event.deferReply().queue();
                    event.getHook().sendMessage("Bot: " + choice + ". You lose").queue();
                }else if(userchoice.equalsIgnoreCase("scissor") && choice.equalsIgnoreCase("paper")) {
                    event.deferReply().queue();
                    event.getHook().sendMessage("Bot: " + choice + ". You win").queue();
                }else if(userchoice.equalsIgnoreCase("scissor") && choice.equalsIgnoreCase("scissor")) {
                    event.deferReply().queue();
                    event.getHook().sendMessage("Bot: " + choice + ". It's a tie").queue();
                }
                else{
                    event.reply("invalid choice").queue();
                }
            }
        }
    }
}
