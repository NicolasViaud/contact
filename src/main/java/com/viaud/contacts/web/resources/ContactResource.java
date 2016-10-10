package com.viaud.contacts.web.resources;

import java.util.Objects;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author Viaud
 */
public class ContactResource extends ResourceSupport{
    
    private Long idContact;
    
    private String name;
    
    private String phone;
    
    private String street;
    
    private String city;

    public ContactResource() {
    }

    public ContactResource(Long idContact, String name, String phone, String street, String city) {
        this.idContact = idContact;
        this.name = name;
        this.phone = phone;
        this.street = street;
        this.city = city;
    }
    
    
    public Long getIdContact() {
        return idContact;
    }

    public void setIdContact(Long idContact) {
        this.idContact = idContact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
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

    @Override
    public String toString() {
        return "ContactResource{" + "idContact=" + idContact + ", name=" + name + ", phone=" + phone + ", street=" + street + ", city=" + city + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.idContact);
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.phone);
        hash = 17 * hash + Objects.hashCode(this.street);
        hash = 17 * hash + Objects.hashCode(this.city);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ContactResource other = (ContactResource) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.street, other.street)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.idContact, other.idContact)) {
            return false;
        }
        return true;
    }
    
    
}
