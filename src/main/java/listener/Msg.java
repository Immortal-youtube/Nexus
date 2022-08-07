package listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Msg extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        ArrayList<String> list = new ArrayList<>();
        list.add("cum");
        list.add("cunt");
        list.add("pussy");
        list.add("vagina");
        list.add("boobs");
        list.add("bitch");
        list.add("fuck");
        list.add("squirt");
        list.add("porn");
        list.add("dick");
        list.add("penis");
        list.add("booty");
        list.add("harassment");
        list.add("hoe");
        list.add("asshole");
        list.add("sugardaddy");
        list.add("sex");

        for (String wo:list){
            if(message.toLowerCase().contains(wo)){
                event.getChannel().sendMessage("Stop swearing :angry: !!!!").queue();
            }
        }
    }
}
