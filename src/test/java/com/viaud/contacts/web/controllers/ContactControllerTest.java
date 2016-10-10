package com.viaud.contacts.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viaud.contacts.web.resources.ContactResource;
import javax.persistence.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.viaud.contacts.web.services.ContactResourceService;
import org.springframework.http.MediaType;

/**
 *
 * @author Viaud
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ContactController.class)
public class ContactControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private ContactResourceService contactResourceService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void testGetOk() throws Exception{
        ContactResource contactResource = new ContactResource();
        contactResource.setIdContact(1L);
        contactResource.setName("toto");
        contactResource.setPhone("0600000000");
        contactResource.setCity("Paris");
        contactResource.setStreet("1 avenue");
        
        given(contactResourceService.getById(contactResource.getIdContact())).willReturn(contactResource);
        mvc.perform(get("/contacts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name",equalTo(contactResource.getName())))
                .andExpect(jsonPath("phone",equalTo(contactResource.getPhone())))
                .andExpect(jsonPath("street",equalTo(contactResource.getStreet())))
                .andExpect(jsonPath("city",equalTo(contactResource.getCity())));
    }
    
    @Test
    public void testGet404() throws Exception{
        given(contactResourceService.getById(1)).willThrow(EntityNotFoundException.class);
        mvc.perform(get("/contacts/1")).andExpect(status().isNotFound());
    }
    
    @Test
    public void testPutOk() throws Exception{
        ContactResource contactResource = new ContactResource();
        contactResource.setIdContact(1L);
        contactResource.setName("toto");
        contactResource.setPhone("0600000000");
        contactResource.setCity("Paris");
        contactResource.setStreet("1 avenue");
        
        given(contactResourceService.update(contactResource.getIdContact(),contactResource)).willReturn(contactResource);
        mvc.perform(put("/contacts/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(contactResource)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name",equalTo(contactResource.getName())))
                .andExpect(jsonPath("phone",equalTo(contactResource.getPhone())))
                .andExpect(jsonPath("street",equalTo(contactResource.getStreet())))
                .andExpect(jsonPath("city",equalTo(contactResource.getCity())));
    }
    
    @Test
    public void testPut404() throws Exception{
        ContactResource contactResource = new ContactResource();
        contactResource.setIdContact(1L);
        contactResource.setName("toto");
        contactResource.setPhone("0600000000");
        contactResource.setCity("Paris");
        contactResource.setStreet("1 avenue");
        
        given(contactResourceService.update(contactResource.getIdContact(),contactResource)).willThrow(EntityNotFoundException.class);        
        mvc.perform(put("/contacts/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(contactResource))).andExpect(status().isNotFound());
    }
    
    @Test
    public void testPostOk() throws Exception{
        ContactResource contactResource = new ContactResource();
        contactResource.setIdContact(1L);
        contactResource.setName("toto");
        contactResource.setPhone("0600000000");
        contactResource.setCity("Paris");
        contactResource.setStreet("1 avenue");
        
        given(contactResourceService.create(contactResource)).willReturn(contactResource);
        mvc.perform(post("/contacts").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(contactResource)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name",equalTo(contactResource.getName())))
                .andExpect(jsonPath("phone",equalTo(contactResource.getPhone())))
                .andExpect(jsonPath("street",equalTo(contactResource.getStreet())))
                .andExpect(jsonPath("city",equalTo(contactResource.getCity())));
    }
    
    @Test
    public void testDeleteOk() throws Exception{
        ContactResource contactResource = new ContactResource();
        contactResource.setIdContact(1L);
        contactResource.setName("toto");
        contactResource.setPhone("0600000000");
        contactResource.setCity("Paris");
        contactResource.setStreet("1 avenue");
        
        given(contactResourceService.getById(contactResource.getIdContact())).willReturn(contactResource);
        mvc.perform(delete("/contacts/1")).andExpect(status().isNoContent());
    }
    
    @Test
    public void testDelete404() throws Exception{
        given(contactResourceService.delete(1)).willThrow(EntityNotFoundException.class);        
        mvc.perform(delete("/contacts/1")).andExpect(status().isNotFound());
    }
        
}
