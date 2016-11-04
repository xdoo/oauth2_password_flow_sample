package com.example;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Hello Bean.
 * 
 * @author straubec
 */
public class Hello {

    public Hello() { }

    public Hello(String name, String city) {
        this.name = name;
        this.city = city;
    }

    @Pattern(regexp = "[a-zA-Z -]*")
    @Size(min = 2, max = 50)
    private String name;
    
    @Pattern(regexp = "[a-zA-Z-]*")
    @Size(min = 3, max = 60)
    private String city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
}
