package com.chornobuk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import com.chornobuk.testclasses.Info;
import com.chornobuk.testclasses.InfoCustomInstantFormat1;
import com.chornobuk.testclasses.InfoCustomInstantFormat2;
import com.chornobuk.testclasses.InfoIncorrectFormat;

public class ObjectLoaderTest {

    private final static String PATH_TO_PROPERTIES = "src/test/java/com/chornobuk/testdata";

    @Test
    public void unparsableInt() throws Exception {
        Path path = getPathForFile("unparsableInt.properties");
        assertThrows(NumberFormatException.class, () -> ObjectLoader.loadFromProperties(Info.class, path));
    }

    @Test
    public void unparsableInteger() throws Exception {
        Path path = getPathForFile("unparsableInteger.properties");
        assertThrows(NumberFormatException.class, () -> ObjectLoader.loadFromProperties(Info.class, path));
    }

    @Test
    public void unparsableInstantPropertyIsNotDate() throws Exception {
        Path path = getPathForFile("instantPropertyIsNotDate.properties");
        assertThrows(DateTimeParseException.class, () -> ObjectLoader.loadFromProperties(Info.class, path));
    }

    @Test
    public void unparsableInstantIncorrectFormatInAnnotation() throws Exception {
        Path path = getPathForFile("instantPropertyIncorrectFormat.properties");
        assertThrows(DateTimeParseException.class,
                () -> ObjectLoader.loadFromProperties(InfoIncorrectFormat.class, path));
    }

    @Test
    public void unparsableInstantWrongFormat() throws Exception {
        Path path = getPathForFile("info.properties");
        assertThrows(DateTimeParseException.class,
                () -> ObjectLoader.loadFromProperties(InfoCustomInstantFormat1.class, path));
    }

    @Test
    public void defaultInstantFormatting() throws Exception {
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
    }

    @Test
    public void customFormat1() throws Exception {
        InfoCustomInstantFormat1 expected = new InfoCustomInstantFormat1();
        LocalDateTime dateTime = LocalDateTime.of(2022, 12, 10, 10, 10);
        expected.setAge(12);
        expected.setHeight(150);
        expected.setSurname("Testovchuk");
        expected.setValue("myValue");
        expected.setInstant(Instant.ofEpochSecond(dateTime.toEpochSecond(ZoneOffset.UTC)));
        Path path = getPathForFile("customFormat1.properties");
        InfoCustomInstantFormat1 parsed = ObjectLoader.loadFromProperties(InfoCustomInstantFormat1.class, path);
        assertEquals(expected, parsed);
    }

    @Test
    public void customFormat2() throws Exception {
        InfoCustomInstantFormat2 expected = new InfoCustomInstantFormat2();
        LocalDateTime dateTime = LocalDateTime.of(2022, 12, 10, 10, 10, 10);
        expected.setAge(12);
        expected.setHeight(150);
        expected.setSurname("Testovchuk");
        expected.setValue("myValue");
        expected.setInstant(Instant.ofEpochSecond(dateTime.toEpochSecond(ZoneOffset.UTC)));
        Path path = getPathForFile("customFormat2.properties");
        InfoCustomInstantFormat2 parsed = ObjectLoader.loadFromProperties(InfoCustomInstantFormat2.class, path);
        assertEquals(expected, parsed);
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
