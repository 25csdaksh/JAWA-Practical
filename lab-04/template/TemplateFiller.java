import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateFiller {
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{(\\w+)\\}");

    public static String fill(String template, String[] names, String[] values) {
        if (template == null) {
            return null;
        }

        Matcher matcher = PLACEHOLDER_PATTERN.matcher(template);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            String key = matcher.group(1);
            String replacement = "[?]";

            // Parallel lookup
            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(key)) {
                    // Check if index is in bounds of values array
                    if (i < values.length) {
                        replacement = values[i];
                    }
                    break;
                }
            }

            // We must quote the replacement to prevent issues with special characters (like $ and \)
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);

        return sb.toString();
    }
}
