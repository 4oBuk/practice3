package com.chornobuk.testclasses;

import java.time.Instant;

import com.chornobuk.Property;

public class InfoIncorrectFormat {
    private String value;

    @Property(name = "lastname")
    private String surname;

    private int age;

    private Integer height;

    @Property(format = "dddd.MMMM.yyyy tt:mm")
    private Instant instant;
}
