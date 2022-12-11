package com.chornobuk;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.chornobuk.parsers.Parser;
import com.chornobuk.parsers.ParsersFactory;

public class ObjectLoader {
    public static <T> T loadFromProperties(Class<T> cls, Path propertiesPath) throws Exception {
        // try {
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
        // } catch (IOException e) {
        // e.printStackTrace();
        // } catch (InstantiationException e) {
        // e.printStackTrace();
        // } catch (IllegalAccessException e) {
        // e.printStackTrace();
        // } catch (IllegalArgumentException e) {
        // e.printStackTrace();
        // } catch (InvocationTargetException e) {
        // e.printStackTrace();
        // } catch (NoSuchMethodException e) {
        // e.printStackTrace();
        // } catch (SecurityException e) {
        // e.printStackTrace();
        // }
        // return null;
    }

    // gets String value of the property, parses it and returns the result
    private static Object parseProperty(Field field, String value) {
        ParsersFactory parsersFactory = ParsersFactory.getInstance();
        Parser parser = parsersFactory.get(field.getType());
        if (parser != null) {
            return parser.parse(field, value);
        } else {
            throw new IllegalArgumentException(field.getType().getTypeName() + " is not supported");
        }
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
