package lairaction;

import java.util.ArrayList;

public record Config(ArrayList<EmojiReplyConfig> emojiAutoReplies)
{
    public static record EmojiReplyConfig(ArrayList<String> targetedUsers, String emojiID, EmojiType emojiType)
    {
        public static enum EmojiType {
            CUSTOM, UNICODE
        }
    }
}
