package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import model.annotation.MaxLength;
import model.annotation.Positive;

public class AnnotationValidator {
    public static String[] validate(Object obj) {
        if (obj == null) {
            return new String[] { "Target object is null" };
        }

        List<String> errors = new ArrayList<>();
        Class<?> clazz = obj.getClass();

        while (clazz != null && clazz != Object.class) {
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);

                    if (field.isAnnotationPresent(Positive.class)) {
                        Positive positive = field.getAnnotation(Positive.class);
                        if (value instanceof Number num) {
                            if (num.doubleValue() <= 0) {
                                errors.add(String.format("Field '%s' with value %s: %s",
                                        field.getName(), value, positive.message()));
                            }
                        } else if (value == null) {
                            errors.add(String.format("Field '%s': %s (value is null)",
                                    field.getName(), positive.message()));
                        }
                    }

                    if (field.isAnnotationPresent(MaxLength.class)) {
                        MaxLength maxLength = field.getAnnotation(MaxLength.class);
                        if (value != null) {
                            String strVal = value.toString();
                            if (strVal.length() > maxLength.value()) {
                                errors.add(String.format("Field '%s' (length %d): %s (max allowed: %d)",
                                        field.getName(), strVal.length(), maxLength.message(), maxLength.value()));
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    errors.add("Failed to read field '" + field.getName() + "': " + e.getMessage());
                }
            }
            clazz = clazz.getSuperclass();
        }

        return errors.toArray(new String[0]);
    }
}
