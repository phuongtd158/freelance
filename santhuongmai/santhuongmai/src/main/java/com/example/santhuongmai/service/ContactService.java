package com.example.santhuongmai.service;

import java.util.List;

import com.example.santhuongmai.entity.Contact;
import com.example.santhuongmai.model.request.CreateContactRequest;

public interface ContactService {
	 	List<Contact> getList();
	    Contact getContact(long id);
	    Contact createContact(CreateContactRequest request);
	    Contact updateContact(long id,CreateContactRequest request);
	    void deleteContact(long id);
	    void enableContact(long id);
}
