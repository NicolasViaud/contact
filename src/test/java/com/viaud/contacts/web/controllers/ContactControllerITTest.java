package com.viaud.contacts.web.controllers;

import com.viaud.contacts.domain.entities.Contact;
import com.viaud.contacts.web.resources.ContactResource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

/**
 * Integration test connect to a in memory H2 database. A sample of contacts entities are insert and drop for each test : see insert-contacts-1.sql
 * @author Viaud
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
    @Sql(value = "classpath:insert-contacts-1.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    @Sql(value = "classpath:drop-contacts.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@TestPropertySource("classpath:test.properties")
public class ContactControllerITTest {
    
    @LocalServerPort
    private int port;    
            
    @Autowired
    private TestRestTemplate testRestTemplate;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private static final ContactResource CONTACT_RESOURCE_1 = new ContactResource(1L,"toto","0600000000", "1 rue albert", "Cannes");
    private static final ContactResource CONTACT_RESOURCE_2 = new ContactResource(2L,"tata","0600000001", "2 rue albert", "Avignon");

    @Test
    public void testGet1() throws Exception{
        ResponseEntity<ContactResource> result = testRestTemplate.getForEntity("http://localhost:"+port+"/contacts/1", ContactResource.class);
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(result.getBody()).isEqualTo(CONTACT_RESOURCE_1);
    }
    
    @Test
    public void testGet2() throws Exception{        
        ResponseEntity<ContactResource> result = testRestTemplate.getForEntity("http://localhost:"+port+"/contacts/2", ContactResource.class);
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(result.getBody()).isEqualTo(CONTACT_RESOURCE_2);
    }
    
    @Test
    public void testGet3() throws Exception{        
        ResponseEntity<String> result = testRestTemplate.getForEntity("http://localhost:"+port+"/contacts/3", String.class);
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(result.getBody()).isEqualTo("javax.persistence.EntityNotFoundException: Unable to find com.viaud.contacts.domain.entities.Contact with id 3");
    }
    
    //Why content is not populate ?
//    @Test
//    public void testGetAll() throws Exception{
//        ResponseEntity<PagedResources<ContactResource>> result = testRestTemplate.exchange("http://localhost:"+port+"/contacts", HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<PagedResources<ContactResource>>(){});
//        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(result.getBody().getContent()).containsOnly(CONTACT_RESOURCE_1,CONTACT_RESOURCE_2);
//    }
//    
//    @Test
//    public void testGetByNameLike() throws Exception{
//        ResponseEntity<PagedResources<ContactResource>> result = testRestTemplate.exchange("http://localhost:"+port+"/contacts?name=ta", HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<PagedResources<ContactResource>>(){});
//        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(result.getBody().getContent()).containsOnly(CONTACT_RESOURCE_2);
//    }
//    
//    @Test
//    public void testGetByPhoneNumber() throws Exception{
//        ResponseEntity<PagedResources<ContactResource>> result = testRestTemplate.exchange("http://localhost:"+port+"/contacts?phone=0600000001", HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<PagedResources<ContactResource>>(){});
//        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(result.getBody().getContent()).containsOnly(CONTACT_RESOURCE_2);
//    }
//    
//    @Test
//    public void testGetByNameLikeAndPhoneNumber() throws Exception{
//        ResponseEntity<PagedResources<ContactResource>> result = testRestTemplate.exchange("http://localhost:"+port+"/contacts?name=ta&phone=0600000001", HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<PagedResources<ContactResource>>(){});
//        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(result.getBody().getContent()).containsOnly(CONTACT_RESOURCE_2);
//    }
//    
//    @Test
//    public void testGetAllSort() throws Exception{
//        ResponseEntity<PagedResources<ContactResource>> result = testRestTemplate.exchange("http://localhost:"+port+"/contacts?sort=name", HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<PagedResources<ContactResource>>(){});
//        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(result.getBody().getContent()).containsExactly(CONTACT_RESOURCE_2,CONTACT_RESOURCE_1);
//    }
    
    @Test
    public void testGetAllSize() throws Exception{
        ResponseEntity<PagedResources<ContactResource>> result = testRestTemplate.exchange("http://localhost:"+port+"/contacts?size=1", HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<PagedResources<ContactResource>>(){});
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(result.getBody().getMetadata().getSize()).isEqualTo(1);
    }
        
    @Test
    public void testPut() throws Exception{
        ContactResource newContactResource = new ContactResource(0L,"titi","0600000002", "3 rue albert", "Cannes");
        HttpEntity input = new HttpEntity(newContactResource);
        ResponseEntity<ContactResource> result = testRestTemplate.exchange("http://localhost:"+port+"/contacts/1", HttpMethod.PUT,input,ContactResource.class);        
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);        
        
        Contact contact1 = entityManager.find(Contact.class, 1L);
        Assertions.assertThat(contact1.getName()).isEqualTo(newContactResource.getName());
        Assertions.assertThat(contact1.getPhoneNumber()).isEqualTo(newContactResource.getPhone());
        Assertions.assertThat(contact1.getAddress().getStreet()).isEqualTo(newContactResource.getStreet());
        Assertions.assertThat(contact1.getAddress().getCity()).isEqualTo(newContactResource.getCity());
        
    }
    
    @Test
    public void testPost() throws Exception{
        ContactResource newContactResource = new ContactResource(0L,"titi","0600000002", "3 rue albert", "Cannes");
        
        ResponseEntity<ContactResource> result = testRestTemplate.postForEntity("http://localhost:"+port+"/contacts",newContactResource,ContactResource.class);
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        
        Contact contact3 = entityManager.find(Contact.class, 3L);        
        Assertions.assertThat(contact3.getName()).isEqualTo(newContactResource.getName());
        Assertions.assertThat(contact3.getPhoneNumber()).isEqualTo(newContactResource.getPhone());
        Assertions.assertThat(contact3.getAddress().getStreet()).isEqualTo(newContactResource.getStreet());
        Assertions.assertThat(contact3.getAddress().getCity()).isEqualTo(newContactResource.getCity());
        
    }
    
}
