package service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import model.annotation.Id;

public class Repository<T> {

    private final Map<String, T> storage = new HashMap<>();

    public String extractId(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null.");
        }
        Class<?> current = entity.getClass();
        while (current != null && current != Object.class) {
            for (Field field : current.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    try {
                        Object val = field.get(entity);
                        if (val != null) {
                            return val.toString();
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed to access @Id field: " + field.getName(), e);
                    }
                }
            }
            current = current.getSuperclass();
        }
        throw new IllegalArgumentException("Entity of type '" + entity.getClass().getName() + "' must declare a field annotated with @Id");
    }

    public T save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Cannot save null entity.");
        }
        String id = extractId(entity);
        storage.put(id, entity);
        return entity;
    }

    @SafeVarargs
    public final ArrayList<T> saveAll(T... items) {
        ArrayList<T> result = new ArrayList<>();
        if (items != null) {
            for (T item : items) {
                if (item != null) {
                    result.add(save(item));
                }
            }
        }
        return result;
    }

    public T findById(String id) {
        if (id == null) {
            return null;
        }
        return storage.get(id.trim());
    }

    public ArrayList<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public boolean delete(String id) {
        if (id == null) {
            return false;
        }
        return storage.remove(id.trim()) != null;
    }

    public int count() {
        return storage.size();
    }

    public int size() {
        return storage.size();
    }

    public boolean existsById(String id) {
        return id != null && storage.containsKey(id.trim());
    }

    public void clear() {
        storage.clear();
    }

    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            return 0.0;
        }
        double sum = 0.0;
        int count = 0;
        for (N val : values) {
            if (val != null) {
                sum += val.doubleValue();
                count++;
            }
        }
        return count == 0 ? 0.0 : sum / count;
    }
}

