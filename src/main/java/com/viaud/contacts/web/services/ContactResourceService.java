package com.viaud.contacts.web.services;

import com.viaud.contacts.domain.entities.Contact;
import com.viaud.contacts.web.resources.ContactResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;

/**
 * This service is a facade that transform resource to entity and call domains services. It manage transaction.
 * The goal of this service is to avoid LazyLoadingException in more complex case (OneToMany, ManyToOne or ManyToMany in entity) when transform entity to resource.
 * @author Viaud
 */
public interface ContactResourceService {
    
    public ContactResource getById(long id);
    public PagedResources<ContactResource> getBySpecification(Specification<Contact> specification,Pageable pageable,PagedResourcesAssembler<Contact> pagedResourceAssembler);
    public ContactResource create(ContactResource contactResource);
    public ContactResource update(long id,ContactResource contactResource);
    public ContactResource delete(long id);
   
    
}
