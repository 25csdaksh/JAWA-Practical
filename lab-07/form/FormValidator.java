package form;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FormValidator {
    public static List<String> validate(Object obj) {
        List<String> errors = new ArrayList<>();
        if (obj == null) {
            errors.add("Validation target object is null");
            return errors;
        }

        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);

                // 1. Check @NotBlank
                if (field.isAnnotationPresent(NotBlank.class)) {
                    NotBlank notBlank = field.getAnnotation(NotBlank.class);
                    if (value == null || value.toString().trim().isEmpty()) {
                        errors.add(String.format("Field '%s': %s", field.getName(), notBlank.message()));
                    }
                }

                // 2. Check @MaxLength
                if (field.isAnnotationPresent(MaxLength.class)) {
                    MaxLength maxLength = field.getAnnotation(MaxLength.class);
                    if (value != null && value.toString().length() > maxLength.value()) {
                        errors.add(String.format("Field '%s': %s (Current length: %d, Max allowed: %d)", 
                            field.getName(), maxLength.message(), value.toString().length(), maxLength.value()));
                    }
                }
            } catch (IllegalAccessException e) {
                errors.add("Error accessing field '" + field.getName() + "': " + e.getMessage());
            }
        }

        return errors;
    }
}
