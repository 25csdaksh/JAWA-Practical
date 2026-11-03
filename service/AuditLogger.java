package service;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import model.annotation.Audit;

public class AuditLogger {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String log(Class<?> clazz, String methodName, Object... args) {
        String actionName = methodName;
        try {
            for (Method m : clazz.getDeclaredMethods()) {
                if (m.getName().equals(methodName) && m.isAnnotationPresent(Audit.class)) {
                    Audit audit = m.getAnnotation(Audit.class);
                    if (!audit.action().isEmpty()) {
                        actionName = audit.action();
                    }
                    break;
                }
            }
        } catch (Exception ignored) {}

        String timestamp = LocalDateTime.now().format(FORMATTER);
        String logEntry = String.format("[%s] AUDIT LOG -> Action: %-15s | Method: %-12s | Args: %s",
                timestamp, actionName, methodName, Arrays.toString(args));
        System.out.println(logEntry);
        return logEntry;
    }
}
