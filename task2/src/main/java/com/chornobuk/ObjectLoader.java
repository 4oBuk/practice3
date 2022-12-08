package com.chornobuk;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Map;

public class ObjectLoader {
    public static <T> T loadFromProperties(Class<T> cls, Path propertiesPath) {
        return null;
    }

    // searcher for property in map, parse from string and return value
    private static Object parsePropertyFromMap(Field field, String property) {
        // this method only parse value
        if (field.getClass().equals(String.class)) {
            return property;
        } else if (field.getClass().equals(Integer.class) || field.getClass().equals(int.class)) {
            try {
                return Integer.parseInt(property);
            } catch (NumberFormatException e) {
                System.out.println(property + "cannot be cast to Integer");
            }
        } else if (field.getClass().equals(Instant.class)) {
            return null; // todo: parse instant
            // todo: throw another exception if date cannot be parsed
        } else {
            throw new IllegalArgumentException(field.getClass().getTypeName() + " is not supported");
        }
    }

    private Object getPropertyFromMap(Field field, Map<String, String> properties) {
        return null;
    }
}
