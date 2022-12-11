package com.chornobuk.parsers;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ParsersFactory {

    private static ParsersFactory instant;
    private Map<Class, Parser> classParserMap;

    private ParsersFactory() {
        classParserMap = new HashMap<>();
        classParserMap.put(Integer.class, (field, value) -> Integer.parseInt(value));
        classParserMap.put(int.class, (field, value) -> Integer.parseInt(value));
        classParserMap.put(String.class, (field, value) -> value);
        classParserMap.put(Instant.class, new InstantParser());
    }

    public static ParsersFactory getInstance() {
        if (instant == null) {
            instant = new ParsersFactory();
        }
        return instant;
    }

    public Parser get(Class cls) {
        return classParserMap.get(cls);
    }
}
