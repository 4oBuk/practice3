package com.chornobuk.testclasses;

public class WithoutFields {

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof WithoutFields;
    }
    
}
