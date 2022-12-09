package com.chornobuk.testClasses;

import com.chornobuk.Property;

public class Info {

    private String value;

    @Property(name = "lastname")
    private String surname;

    private int age;
    private Integer height;
    
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

    
}
