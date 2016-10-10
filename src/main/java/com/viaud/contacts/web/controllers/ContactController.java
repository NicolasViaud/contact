package com.viaud.contacts.web.controllers;

import com.viaud.contacts.domain.entities.Contact;
import com.viaud.contacts.web.resources.ContactResource;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.viaud.contacts.web.services.ContactResourceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Viaud
 */
@RestController
@RequestMapping("/contacts")
public class ContactController {    
    
    private static final Logger LOG = LoggerFactory.getLogger(ContactController.class);
    
    @Autowired
    private ContactResourceService contactResourceService;
    
    @RequestMapping(method = RequestMethod.GET,value = "/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ContactResource getById(@PathVariable("id") long idContact){
        LOG.debug("REST GET request by id "+idContact);
        return contactResourceService.getById(idContact);
    }
    
    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PagedResources<ContactResource> getBySpecification(
            @And({
                    @Spec(params = "name",path = "name",spec = LikeIgnoreCase.class),
                    @Spec(params = "phone",path = "phoneNumber",spec = Equal.class)
            }) Specification<Contact> specification,
            Pageable pageable,
            PagedResourcesAssembler<Contact> pagedResourceAssembler){
        LOG.debug("REST GET request by params");
        return contactResourceService.getBySpecification(specification, pageable, pagedResourceAssembler);
    }
    
    /**
     * Update an existing contact
     * @param idContact
     * @param contactResource
     * @return 
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ContactResource put(@PathVariable("id") long idContact,@RequestBody ContactResource contactResource){
        LOG.debug("REST PUT request to "+idContact);
        return contactResourceService.update(idContact, contactResource);
    }
    
    /**
     * Create a new contact
     * @param contactResource
     * @return 
     */
    @RequestMapping(method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ContactResource post(@RequestBody ContactResource contactResource){
        LOG.debug("REST POST request");
        return contactResourceService.create(contactResource);
    }
    
    /**
     * Delete an existing contact
     * @param idContact
     */
    @RequestMapping(method = RequestMethod.DELETE,value = "/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long idContact){
        LOG.debug("REST DELETE request to "+idContact);
        contactResourceService.delete(idContact);
    }
}
