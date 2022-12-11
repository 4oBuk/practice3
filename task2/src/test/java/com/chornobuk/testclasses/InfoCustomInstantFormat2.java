package com.chornobuk.testclasses;

import java.time.Instant;
import java.util.Objects;

import com.chornobuk.Property;

public class InfoCustomInstantFormat2 {

    @Property(format = "dd/MM/yyyy HH:mm:ss")
    private Instant instant;

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    @Override
    public int hashCode() {
        return Objects.hash(instant);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof InfoCustomInstantFormat2 info))
            return false;
        return instant.equals(info.instant);
    }
}
