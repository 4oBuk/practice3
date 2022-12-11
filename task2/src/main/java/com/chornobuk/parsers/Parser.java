package com.chornobuk.parsers;

import java.lang.reflect.Field;

@FunctionalInterface
public interface Parser {
    Object parse(Field field, String value);
}
