package com.chornobuk.parsers;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import com.chornobuk.Property;

public class InstantParser implements Parser {

    @Override
    public Object parse(Field field, String value) {
        if (field.isAnnotationPresent(Property.class)) {
            Property property = field.getAnnotation(Property.class);
            if (!property.format().isEmpty()) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(property.format())
                        .withZone(ZoneId.of("UTC"));
                Instant instant = Instant.from(dateTimeFormatter.parse(value));
                return instant;
            } else {
                TemporalAccessor temporalAccessor = DateTimeFormatter.ISO_INSTANT
                        .withZone(ZoneId.of("UTC"))
                        .parse(value);
                return Instant.from(temporalAccessor);
            }
        } else {
            TemporalAccessor temporalAccessor = DateTimeFormatter.ISO_INSTANT
                    .withZone(ZoneId.of("UTC"))
                    .parse(value);
            return Instant.from(temporalAccessor);
        }
    }

}
