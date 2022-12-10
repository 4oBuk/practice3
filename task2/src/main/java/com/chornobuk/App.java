package com.chornobuk;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class App {
    public static void main(String[] args) {
        String s = "10.12.2022 10:10";
        TemporalAccessor dt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").withZone(ZoneId.systemDefault()).parse(s);
        System.out.println(Instant.from(dt));
        // todo:
        // [x]1. create an annotation
        // [x]2. create class for reading properties file (read in map)
        // [x]3. create method for parsing a property from map
        // [x]4. create method for loadiing data
        // [] tests
        // [] refactoring
    }

}
