package com.github.weebkun.events;

import com.github.weebkun.Bot;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

/**
 * listener for server messages
 */
public class MessageListener extends ListenerAdapter {

    //private static long dmId = 839874977733935154L;

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);

        // check if author is not this bot
        if(event.getAuthor().getIdLong() != 839869262827225118L) {
            // open a dm with weebkun and send msg to weebkun
            // msg format: [server]-[channel]: [author]: [msg]
            Bot.getJda().getUserById(268359975993081859L).openPrivateChannel().flatMap(channel -> channel.sendMessage(String.format("%s-%s: %s: %#s",
                    event.getGuild().getName(), event.getChannel(), event.getAuthor(), event.getMessage()))).queue();
        }
    }

    @Override
    public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {
        super.onPrivateMessageReceived(event);

        // msg format: @[server]@[channel] msg
        if(event.getAuthor().isBot()) return;
        else {
            //todo send msg
        }
    }
}
