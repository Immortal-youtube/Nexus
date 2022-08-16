package Utils;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class LogicRPS {

    public static void check(ButtonInteractionEvent event, String choiceUser,String choiceBot) {
        Emoji user = null;
        Emoji bot = null;
        if (choiceUser.equals("rock")) {
            user = Emoji.fromUnicode("\uD83E\uDEA8");
        } else if (choiceUser.equals("paper")) {
            user = Emoji.fromUnicode("\uD83E\uDDFB");
        } else if (choiceUser.equals("scissors")) {
            user = Emoji.fromUnicode("✂");
        }

        if (choiceBot.equals("rock")) {
            bot = Emoji.fromUnicode("\uD83E\uDEA8");
        } else if (choiceBot.equals("paper")) {
            bot = Emoji.fromUnicode("\uD83E\uDDFB");
        } else if (choiceBot.equals("scissors")) {
            bot = Emoji.fromUnicode("✂");
        }


        String WinOrLose = decide(choiceUser, choiceBot);
        Message message = new MessageBuilder()
                .append("You chose " + (user).getFormatted() + " and the bot chose " + (bot).getFormatted() + " so you " + WinOrLose)
                .build();
        event.deferReply().queue();
        event.getHook().sendMessage(message).queue();
    }

    public static String decide(String choiceUser,String choiceBot){
            String WinOrLose = "";
            if (choiceBot.equals(choiceUser)) {
                WinOrLose = "draw";
            } else if (choiceBot.equals("rock")) {
                if (choiceUser.equals("paper")) {
                    WinOrLose = "win";
                } else {
                    WinOrLose = "lose";
                }
            } else if (choiceBot.equals("paper")) {
                if (choiceUser.equals("scissors")) {
                    WinOrLose = "win";
                } else {
                    WinOrLose = "lose";
                }
            } else if (choiceBot.equals("scissors")) {
                if (choiceUser.equals("rock")) {
                    WinOrLose = "win";
                } else {
                    WinOrLose = "lose";
                }
            }
            return WinOrLose;
    }
}
