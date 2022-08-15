package listener;

import net.dv8tion.jda.api.entities.emoji.CustomEmoji;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class SuggestionListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getChannel().getId().equals("1008709592680767498")){
            event.getMessage().addReaction(Emoji.fromFormatted("✅")).queue();
            event.getMessage().addReaction(Emoji.fromFormatted("❌")).queue();
            event.getMessage().addReaction(Emoji.fromFormatted("🤔")).queue();
        }
    }
}
