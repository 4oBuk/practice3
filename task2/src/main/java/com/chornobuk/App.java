package com.chornobuk;

import java.lang.reflect.Field;
import java.util.Arrays;

public class App {

    @Property(name = "firstname")
    private static String name = "sfdsf";
    
    public static void main(String[] args) {
        // Method[] methods = String.class.getMethods();
        // Type t = String.class;
        // Field[] fields = Test.class.getDeclaredFields();
        // System.out.println(String.class.getTypeName());
        // System.out.println(int.class.getTypeName());
        // System.out.println(Integer.class.getTypeName());
        // System.out.println(t.getTypeName());
        try {
            Field f = App.class.getDeclaredField("name");
            System.out.println(Arrays.toString(f.getAnnotations()));
            if(f.isAnnotationPresent(Property.class)) {
                Property property = f.getAnnotation(Property.class);
                System.out.println(property.format());
                System.out.println(property.name());
            }
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // todo:
        // []1. create an annotation
        // []2. create class for reading properties file (read in map)
        // []3. create method for parsing a property from map
        // []4. create method for loadiing data
        // [] tests
        // [] refactoring
    }

}
