package com.github.weebkun.events;

import com.github.weebkun.Bot;
import com.weebkun.ListUtil;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.stream.Collectors;

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
            Bot.getJda().getUserById(268359975993081859L).openPrivateChannel().flatMap(channel -> channel.sendMessageFormat("%s-%s: %s: %#s",
                    event.getGuild().getName(), event.getChannel(), event.getAuthor(), event.getMessage())).queue();
        }
    }

    @Override
    public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {
        super.onPrivateMessageReceived(event);

        // msg format: @[server] @[channel] msg
        if(event.getAuthor().isBot()) return;
        else {
            String[] msg = event.getMessage().getContentDisplay().split(" ");
            if(msg[0].contains("@") && msg[1].contains("@") && msg.length >= 3) {
                // get server from name
                // assume its the 1st result
                Guild guild = Bot.getJda().getGuildsByName(msg[0].replaceFirst("@", ""), false).get(0);
                // get channel and send msg
                ListUtil.map(ListUtil.filter(guild.getChannels(true),
                        channel -> channel.getName().equals(msg[1].replaceFirst("@", ""))),
                        channel -> guild.getTextChannelById(channel.getId()))
                        .get(0).sendMessage(String.join(" ", Arrays.copyOfRange(msg, 2, msg.length))).queue();
            }
        }
    }
}
