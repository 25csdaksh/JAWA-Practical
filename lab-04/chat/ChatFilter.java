public class ChatFilter {
    public static boolean containsKeyword(String message, String keyword) {
        if (message == null || keyword == null) {
            return false;
        }
        return message.toLowerCase().contains(keyword.toLowerCase());
    }
}
