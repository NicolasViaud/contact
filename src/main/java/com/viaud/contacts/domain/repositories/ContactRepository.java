package com.viaud.contacts.domain.repositories;

import com.viaud.contacts.domain.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author Viaud
 */
public interface ContactRepository extends JpaRepository<Contact, Long>,JpaSpecificationExecutor<Contact>{
        
}
