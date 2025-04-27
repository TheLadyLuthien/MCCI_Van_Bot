package lairaction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.emoji.KnownCustomEmoji;

import com.google.common.base.Charsets;
import com.google.gson.Gson;

public class App
{
    private static final Gson GSON = new Gson();
    public static final Config CONFIG = getConfig();

    private static Config getConfig()
    {
        try
        {
            return GSON.fromJson(Files.readString(Path.of("./config.json"), Charsets.UTF_8), Config.class);
        }
        catch (IOException e)
        {
            return new Config(new ArrayList<>());
        }
    }

    private static String loadToken()
    {
        final String tokenPath = "./bot_token.secret";
        try
        {
            String content = Files.readString(Path.of(tokenPath), Charsets.UTF_8);
            return content;
        }
        catch (IOException e)
        {
            return null;
        }
    }

    public String getGreeting()
    {
        return "Hello World!";
    }

    public static void main(String[] args)
    {
        // if (!DataManager.USERS.loadFromFile())
        // {
        // DataManager.USERS.writeToFile();
        // }

        // LairActionBot bot = new LairActionBot(App.loadToken());

        // while (true)
        // {

        // }

        DiscordApi api = new DiscordApiBuilder().setToken(loadToken())/* .addIntents(Intent.MESSAGE_CONTENT) */.login().join();

        // Add a listener which answers with "Pong!" if someone writes "!ping"
        api.addMessageCreateListener(event -> {
            String userId = event.getMessageAuthor().getIdAsString();
            for (var ear : CONFIG.emojiAutoReplies())
            {
                if (ear.targetedUsers().contains(userId))
                {
                    switch (ear.emojiType())
                    {
                        case CUSTOM:
                            Optional<KnownCustomEmoji> emoji = api.getCustomEmojiById(Long.parseLong(ear.emojiID()));
                            event.getMessage().addReaction(emoji.get());

                            break;
                        case UNICODE:
                            event.getMessage().addReaction(ear.emojiID());

                        break;
                    }
                }
            }
            // if (CONFIG.rahhReactingUserList().contains(event.getMessageAuthor().getIdAsString()))
            // {
            // }
            // if (CONFIG.heurrrrReactingUserList().contains(event.getMessageAuthor().getIdAsString()))
            // {
            // // Optional<KnownCustomEmoji> emoji = api.getCustomEmojiById(1354672630686290072L);
            // }
        });
    }
}
