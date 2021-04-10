package com.singlestone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.singlestone.entity.CallList;
import com.singlestone.entity.Contact;
import com.singlestone.service.ContactService;

@RestController
public class ContactController {
	
	@Autowired
	ContactService contactService;
	
	@GetMapping("/contacts")
	public ResponseEntity<List<Contact>> getContacts() {
		return ResponseEntity.ok(contactService.getContacts());
	}
	
	@GetMapping("/contacts/{id}")
	public ResponseEntity<Contact> getContactById(@PathVariable("id") int id) {
		return ResponseEntity.ok(contactService.getContactById(id));
	}
	
	@PostMapping("/contacts")
	public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
		return ResponseEntity.ok(contactService.createContact(contact));
	}
	
	@PutMapping("/contacts/{id}")
	public ResponseEntity<Contact> updateContact(@PathVariable("id") int id, @RequestBody Contact contact) {
		return ResponseEntity.ok(contactService.updateContact(id, contact));
	}
	
	@DeleteMapping("/contacts/{id}")
	public String deleteContact(@PathVariable("id") int id) {
		return contactService.deleteContact(id);
	}
	
	@GetMapping("/contacts/call-list")	
	public ResponseEntity<List<CallList>> getCallList() {
		return ResponseEntity.ok( contactService.getCallList());
	}
	
}
