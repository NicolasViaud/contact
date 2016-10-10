package com.viaud.contacts.domain.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Viaud
 */
@Embeddable
public class Address implements Serializable {

    @Column(name="ADDRESS_STREET",nullable = false)    
    private String street;
    
    @Column(name="ADDRESS_CITY",nullable = false)
    private String city;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    
}
