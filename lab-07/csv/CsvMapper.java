package csv;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CsvMapper {
    public static <T> T mapRow(Class<T> clazz, String[] headers, String[] data) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();

            // Map header names to column indexes
            Map<String, Integer> headerMap = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                headerMap.put(headers[i].trim().toLowerCase(), i);
            }

            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column col = field.getAnnotation(Column.class);
                    String colName = col.name().trim().toLowerCase();

                    field.setAccessible(true);

                    if (headerMap.containsKey(colName)) {
                        int index = headerMap.get(colName);
                        if (index < data.length) {
                            String rawValue = data[index].trim();
                            Object convertedVal = convertType(field.getType(), rawValue);
                            field.set(instance, convertedVal);
                        }
                    } else {
                        // Handle missing column
                        if (col.required()) {
                            System.out.println(String.format("[WARNING] Required column '%s' missing for field '%s'. Retaining default.",
                                    col.name(), field.getName()));
                        } else {
                            System.out.println(String.format("[INFO] Optional column '%s' not present in CSV. Using default value.", col.name()));
                        }
                    }
                }
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("CSV Mapping failed: " + e.getMessage(), e);
        }
    }

    private static Object convertType(Class<?> type, String value) {
        if (type == String.class) return value;
        if (type == int.class || type == Integer.class) return Integer.parseInt(value);
        if (type == long.class || type == Long.class) return Long.parseLong(value);
        if (type == double.class || type == Double.class) return Double.parseDouble(value);
        if (type == boolean.class || type == Boolean.class) return Boolean.parseBoolean(value);
        return value;
    }
}
