package com.chornobuk.testclasses;

import java.time.Instant;

import com.chornobuk.Property;

public class InfoIncorrectFormat {
    @Property(format = "dddd.MMMM.yyyy tt:mm")
    private Instant instant;
}
