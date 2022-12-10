package com.chornobuk.testclasses;

import java.time.Instant;
import java.util.Objects;

import com.chornobuk.Property;

public class InfoCompositeProperties {
    @Property(name = "my.value")
    private String value;

    @Property(name = "my.lastname")
    private String surname;

    @Property(name = "my.age")
    private int age;

    @Property(name = "my.height")
    private Integer height;

    @Property(name = "my.instant")
    private Instant instant;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, surname, age, height, instant);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof InfoCompositeProperties info))
            return false;
        return value.equals(info.value) && surname.equals(info.surname) && age == info.age
                && height.equals(info.height) && instant.equals(info.instant);
    }
}
