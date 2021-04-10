package com.singlestone.service;

import java.util.List;

import com.singlestone.entity.CallList;
import com.singlestone.entity.Contact;

public interface ContactService {
	
	List<Contact> getContacts();
	List<CallList> getCallList();
	Contact getContactById(int id);
	Contact createContact(Contact contact);
	Contact updateContact(int id, Contact contact);
	String deleteContact(int id);

}
