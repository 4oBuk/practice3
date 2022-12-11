package com.chornobuk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.Test;

import com.chornobuk.testclasses.Info;
import com.chornobuk.testclasses.InfoCompositeProperties;
import com.chornobuk.testclasses.InfoCustomInstantFormat1;
import com.chornobuk.testclasses.InfoCustomInstantFormat2;
import com.chornobuk.testclasses.InfoIncorrectFormat;
import com.chornobuk.testclasses.InfoWithUnsupported;
import com.chornobuk.testclasses.OnlyUnsupported;
import com.chornobuk.testclasses.WithoutDefaultConstructor;
import com.chornobuk.testclasses.WithoutFields;

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
        assertThrows(IllegalArgumentException.class,
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
    public void lastnamePropertyIsEmpty() throws Exception {
        Info expected = new Info();
        LocalDateTime dateTime = LocalDateTime.of(2022, 12, 10, 10, 10, 10);
        expected.setAge(12);
        expected.setHeight(150);
        expected.setSurname("");
        expected.setValue("myValue");
        expected.setInstant(Instant.ofEpochSecond(dateTime.toEpochSecond(ZoneOffset.UTC)));
        Path path = getPathForFile("lastnamePropertyIsEmpty.properties");
        Info parsed = ObjectLoader.loadFromProperties(Info.class, path);
        assertEquals(expected, parsed);
    }

    @Test
    public void intPropertyIsEmpty() throws Exception {
        Path path = getPathForFile("intPropertyIsEmpty.properties");
        assertThrows(NumberFormatException.class, () -> ObjectLoader.loadFromProperties(Info.class, path));

    }

    @Test
    public void instantPropertyIsEmpty() throws Exception {
        Path path = getPathForFile("instantPropertyIsEmpty.properties");
        assertThrows(DateTimeParseException.class, () -> ObjectLoader.loadFromProperties(Info.class, path));
    }

    @Test
    public void classWithUnsupportedFields() throws Exception {
        Path path = getPathForFile("infoWithUnsupported.properties");
        assertThrows(IllegalArgumentException.class,
                () -> ObjectLoader.loadFromProperties(InfoWithUnsupported.class, path));
    }

    @Test
    public void classHasOnlyUnsupportedFields() throws Exception {
        Path path = getPathForFile("infoWithUnsupported.properties");
        assertThrows(IllegalArgumentException.class,
                () -> ObjectLoader.loadFromProperties(OnlyUnsupported.class, path));
    }

    @Test
    public void classWithoutFields() throws Exception {
        Path path = getPathForFile("info.properties");
        WithoutFields expected = new WithoutFields();
        assertEquals(expected, ObjectLoader.loadFromProperties(WithoutFields.class, path));
    }

    @Test
    public void classWithoutDefaultConstructor() throws Exception {
        Path path = getPathForFile("info.properties");
        assertThrows(NoSuchMethodException.class,
                () -> ObjectLoader.loadFromProperties(WithoutDefaultConstructor.class, path));
    }

    @Test
    public void compositeProperties() throws Exception {
        InfoCompositeProperties expected = new InfoCompositeProperties();
        LocalDateTime dateTime = LocalDateTime.of(2022, 12, 10, 10, 10, 10);
        expected.setAge(12);
        expected.setHeight(150);
        expected.setSurname("Testovchuk");
        expected.setValue("myValue");
        expected.setInstant(Instant.ofEpochSecond(dateTime.toEpochSecond(ZoneOffset.UTC)));
        Path path = getPathForFile("composite.properties");

        InfoCompositeProperties parsed = ObjectLoader.loadFromProperties(InfoCompositeProperties.class, path);
        assertEquals(expected, parsed);
    }

    @Test
    public void unexistedFile() throws Exception {
        Path path = getPathForFile("unexisted.properties");
        assertThrows(FileNotFoundException.class, () -> ObjectLoader.loadFromProperties(Info.class, path));
    }

    private static Path getPathForFile(String fileName) {
        return Paths.get(PATH_TO_PROPERTIES, fileName);
    }
}
