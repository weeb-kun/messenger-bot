package com.github.weebkun;

import com.github.weebkun.events.MessageListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

/**
 * main bot class
 */
public class Bot {
    private static JDA jda;

    public static void main(String[] args) throws LoginException {
        jda = JDABuilder.createDefault(args[0]).build();
        jda.addEventListener(new MessageListener());
    }

    public static JDA getJda() {
        return jda;
    }
}
