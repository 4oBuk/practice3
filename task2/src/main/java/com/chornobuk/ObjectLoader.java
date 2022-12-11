package com.chornobuk;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Properties;

import com.chornobuk.parsers.Parser;
import com.chornobuk.parsers.ParsersFactory;

public class ObjectLoader {
    public static <T> T loadFromProperties(Class<T> cls, Path propertiesPath) throws Exception {
        Properties properties = loadPropertiesFromFile(propertiesPath);
        Field[] fields = cls.getDeclaredFields();
        T object = cls.getDeclaredConstructor().newInstance();
        for (Field field : fields) {
            field.setAccessible(true);
            String property = getPropertyValue(field, properties);
            field.set(object, parseProperty(field, property));
        }
        return object;
    }

    // gets String value of the property, parses it and returns the result
    // IllegalArgumentExcpetion will be thrown if type is not supported
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
