package com.chornobuk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ObjectLoader {
    public static <T> T loadFromProperties(Class<T> cls, Path propertiesPath) {
        try {
            Properties properties = loadPropertiesFromFile(propertiesPath);
            Map<String, Object> fieldValueMap = new HashMap<>();
            Field[] fields = cls.getFields();
            for (Field field : fields) {
                String property = getPropertyValue(field, properties);
                fieldValueMap.put(field.getName(), parseProperty(field, property));
            }
            System.out.println(fieldValueMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // gets String value of the property, parses it and returns the result
    private static Object parseProperty(Field field, String value) {
        if (field.getClass().equals(String.class)) {
            return value;
        } else if (field.getClass().equals(Integer.class) || field.getClass().equals(int.class)) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println(value + "cannot be cast to Integer");
            }
        } else if (field.getClass().equals(Instant.class)) {
            try {
                if (field.isAnnotationPresent(Property.class)) {
                    Property property = field.getAnnotation(Property.class);
                    if (!property.name().isEmpty()) {
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(property.name())
                                .withZone(ZoneId.systemDefault());
                        Instant instant = Instant.from(dateTimeFormatter.parse(value));
                        return instant;
                    }
                    return DateTimeFormatter.ISO_INSTANT.parse(value);
                }

            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException(field.getClass().getTypeName() + " is not supported");
        }
        // todo: refactor it
        return null;
    }

    // get property value by the name of the field or the name of Property
    // annotation
    // returns null if the property wasn't found
    private static String getPropertyValue(Field field, Properties properties) {
        String propertyName = field.getName();
        if (field.isAnnotationPresent(Property.class)) {
            Property property = field.getAnnotation(Property.class);
            if (!property.name().isEmpty()) {
                propertyName = property.name();
            }
        }
        return properties.getProperty(propertyName);
    }

    private static Properties loadPropertiesFromFile(Path path) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(path.toFile()));
        return properties;
    }
}
