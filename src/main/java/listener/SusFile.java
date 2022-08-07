package listener;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class SusFile extends ListenerAdapter {
    Dotenv dotenv = Dotenv.configure().load();
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        List<String> choices = Arrays.stream(dotenv.get("SUS_ARRAY").split(",")).toList();
        String msg = event.getMessage().getContentRaw();
        TextChannel channel = event.getChannel().asTextChannel();
        for(String choice : choices){
            if(msg.replace(" ","").toLowerCase().contains(choice)){
                channel.sendMessage(":angry:").queue();
            }
        }


    }
}
