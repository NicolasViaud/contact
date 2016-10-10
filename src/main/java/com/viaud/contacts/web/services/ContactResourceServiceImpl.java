package com.viaud.contacts.web.services;

import com.viaud.contacts.domain.entities.Address;
import com.viaud.contacts.domain.entities.Contact;
import com.viaud.contacts.domain.repositories.ContactRepository;
import com.viaud.contacts.web.assemblers.ContactAssembler;
import com.viaud.contacts.web.resources.ContactResource;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Service;

/**
 *
 * @author Viaud
 */
@Service
public class ContactResourceServiceImpl implements ContactResourceService{
    
    private static final Logger LOG = LoggerFactory.getLogger(ContactResourceServiceImpl.class);
    
    @Autowired
    private ContactRepository contactRepository;
    
    @Autowired
    private ContactAssembler contactAssembler;
    
    @Transactional
    @Override
    public ContactResource getById(long id){
        Contact contact = contactRepository.getOne(id);
        return contactAssembler.toResource(contact);
    }
    
    @Transactional
    @Override
    public PagedResources<ContactResource> getBySpecification(Specification<Contact> specification,Pageable pageable,PagedResourcesAssembler<Contact> pagedResourceAssembler){
        Page<Contact> contacts = contactRepository.findAll(specification,pageable);
        return pagedResourceAssembler.toResource(contacts, contactAssembler);
    }
    
    @Transactional
    @Override
    public ContactResource create(ContactResource contactResource){
        Contact contact = new Contact();        
        resourceToEntity(contactResource, contact);        
        Contact contactResult = contactRepository.saveAndFlush(contact);
        return contactAssembler.toResource(contactResult);
    }
    
    @Transactional
    @Override
    public ContactResource update(long id,ContactResource contactResource){
        Contact contact = contactRepository.getOne(id);     
        resourceToEntity(contactResource, contact);        
        Contact contactResult = contactRepository.saveAndFlush(contact);
        return contactAssembler.toResource(contactResult);
    }
    
    @Transactional
    @Override
    public ContactResource delete(long id){
        Contact contact = contactRepository.getOne(id);             
        contactRepository.delete(contact);       
        return contactAssembler.toResource(contact);
    }    
    
    /**
     * Copy all attributes from ContactResource to Contact except the id
     * @param contactResource 
     */
    private void resourceToEntity(ContactResource contactResource,Contact contact){
        contact.setName(contactResource.getName());
        contact.setPhoneNumber(contactResource.getPhone());
        Address address = new Address();
        contact.setAddress(address);
        address.setCity(contactResource.getCity());
        address.setStreet(contactResource.getStreet());
    }
    
}
