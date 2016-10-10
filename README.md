# contact
Example of REST Api with Spring boot

###Summary

To run the project, simply execute the jar `viaud-contacts-1.0-RELEASE.jar`.  
The listen port use is 8080 by default. It's possible to change this property in application.properties file.

Actually, the local database contains 2 contacts :  
`{"idContact": 1, "name": "name1", "phone": "0600000001", "street": "1 avenue toto", "city": "Paris"}`  
`{"idContact": 2, "name": "name2", "phone": "0600000002", "street": "2 avenue toto", "city": "Sophia"}`

###Usage

- Read a contact by Id :  
GET `localhost:8080/contacts/{id}`  

- Read a list of contacts by name or phone number with pagination supported :  
GET `localhost:8080/contacts?name={name}&phone={phone}&sort={sort}&size={size}&page={page}`  

- Update an existing contact :  
PUT `localhost:8080/contacts/{id}`  
Header:`Content-Type: application/json`  
Body:`{"name":"{name}","phone":"{phone}","street":"{street}","city":"{city}"}`  

- Create a new contact :  
POST `localhost:8080/contacts`  
Header:`Content-Type: application/json`  
Body:`{"name":"{name}","phone":"{phone},"street":"{street}","city":"{city}"}`  

- Remove an existing contact :  
DELETE `localhost:8080/contacts/{id}`  