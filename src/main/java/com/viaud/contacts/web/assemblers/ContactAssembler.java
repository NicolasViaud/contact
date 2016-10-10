package com.viaud.contacts.web.assemblers;

import com.viaud.contacts.domain.entities.Contact;
import com.viaud.contacts.web.controllers.ContactController;
import com.viaud.contacts.web.resources.ContactResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 *
 * @author Viaud
 */
@Component
public class ContactAssembler extends ResourceAssemblerSupport<Contact, ContactResource>{

    public ContactAssembler() {
        super(ContactController.class, ContactResource.class);
    }

    @Override
    public ContactResource toResource(Contact t) {
        ContactResource contactResource = createResourceWithId(t.getId(), t);
        return contactResource;
    }
    
    @Override
    protected ContactResource instantiateResource(Contact t){
        //For bigger project, use other framework for mapping like Dozer for example
        ContactResource contactResource = new ContactResource();
        contactResource.setIdContact(t.getId());
        contactResource.setName(t.getName());
        contactResource.setPhone(t.getPhoneNumber());
        if(t.getAddress()!=null){            
            contactResource.setStreet(t.getAddress().getStreet());
            contactResource.setCity(t.getAddress().getCity());
        }
        return contactResource;    
    }
    
}
