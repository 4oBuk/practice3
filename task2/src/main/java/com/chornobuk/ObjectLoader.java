package com.chornobuk;

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
            Map<Field, Object> fieldValueMap = new HashMap<>();
            Field[] fields = cls.getDeclaredFields();
            T object = cls.getDeclaredConstructor(null).newInstance(null);
            for (Field field : fields) {
                field.setAccessible(true);
                String property = getPropertyValue(field, properties);
                field.set(object, parseProperty(field, property));
            }
            return object;
            // cls.getConstructor(null);
            // todo: create an object and set the read values
            // I have to use default constructor (without parameters) of the object
            // if object doesn't have such constructor
            // load won't be possible
            // get all setters of the class
            // and use them to set necessary value
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {// todo:replace with particular exceptions
            e.printStackTrace();
        }
        return null;
    }

    // gets String value of the property, parses it and returns the result
    private static Object parseProperty(Field field, String value) {
        if (field.getType().equals(String.class)) {
            return value;
        } else if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println(value + "cannot be cast to Integer");
            }
        } else if (field.getType().equals(Instant.class)) {
            try {
                if (field.isAnnotationPresent(Property.class)) {
                    Property property = field.getAnnotation(Property.class);
                    if (!property.name().isEmpty()) {
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(property.name())
                                .withZone(ZoneId.systemDefault());
                        Instant instant = Instant.from(dateTimeFormatter.parse(value));
                        return instant;
                    }
                } else {
                    // todo: here should be instant formatter
                    return DateTimeFormatter.ISO_DATE_TIME.parse(value);
                }

            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException(field.getType().getTypeName() + " is not supported");
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
