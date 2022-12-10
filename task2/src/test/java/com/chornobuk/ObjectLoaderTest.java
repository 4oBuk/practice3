package com.chornobuk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

import com.chornobuk.testclasses.Info;

public class ObjectLoaderTest {

    private final static String PATH_TO_PROPERTIES = "src/test/java/com/chornobuk/testdata";

    @Test
    public void loadFromProperties() throws Exception {
        Info expected = new Info();
        LocalDateTime dateTime = LocalDateTime.of(2022, 12, 10, 10, 10, 10);
        expected.setAge(12);
        expected.setHeight(150);
        expected.setSurname("Testovchuk");
        expected.setValue("myValue");
        expected.setInstant(Instant.ofEpochSecond(dateTime.toEpochSecond(ZoneOffset.UTC)));
        Path path = getPathForFile("info.properties");

        Info parsed = ObjectLoader.loadFromProperties(Info.class, path);
        assertEquals(expected, parsed);
        // todo: create equals and hash code in Info class
    }// todo read about setAccessible

    @Test
    public void unparsableInt() throws Exception {

    }

    @Test
    public void unparsableInteger() throws Exception {

    }

    @Test
    public void unparsableInstantPropertyIsNotDate() throws Exception {

    }

    @Test
    public void unparsableInstantIncorrectFormat() throws Exception {

    }

    @Test
    public void unparsableInstantUnsupportedFormat() throws Exception {

    }

    @Test
    public void defaultInstantFormatting() throws Exception {
    
    }

    @Test
    public void surnamePropertyIsEmpty() throws Exception {

    }

    @Test
    public void intPropertyIsEmpty() throws Exception {

    }

    @Test
    public void instantPropertyIsEmpty() throws Exception {

    }

    @Test
    public void classWithUnsupportedFields() throws Exception {

    }

    @Test
    public void classHasOnlyUnsupportedFields() throws Exception {

    }

    @Test
    public void classWithoutFields() throws Exception {

    }

    @Test
    public void classWithoutDefaultConstructor() throws Exception {

    }

    @Test
    public void compositeProperties() throws Exception {

    }

    private static Path getPathForFile(String fileName) {
        return Paths.get(PATH_TO_PROPERTIES, fileName);
    }
}
