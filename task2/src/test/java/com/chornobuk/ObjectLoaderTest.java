package com.chornobuk;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import com.chornobuk.testClasses.Info;

public class ObjectLoaderTest {

    @Test
    public void loadFromProperties() throws Exception {
        Info info = new Info();
        info.setAge(12);
        info.setHeight(150);
        info.setSurname("Testovchuk");
        info.setValue("myValue");
        Path path = Paths.get("src/test/java/com/chornobuk/testClasses/info.properties");
        Info info2 = ObjectLoader.loadFromProperties(Info.class, path);
        System.out.println("done");
    }
}
