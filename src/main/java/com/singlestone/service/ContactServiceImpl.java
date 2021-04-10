package com.singlestone.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.singlestone.entity.CallList;
import com.singlestone.entity.Contact;
import com.singlestone.entity.Name;
import com.singlestone.entity.Phone;
import com.singlestone.entity.Phone.Type;
import com.singlestone.exception.ResourceNotFoundException;
import com.singlestone.repository.ContactRepository;
import com.singlestone.util.ContactUtil;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	ContactUtil contactUtil;

	private static final String NOT_FOUND = "Contact not found with id: ";

	@Override
	public List<Contact> getContacts() {
		return contactRepository.findAll();
	}

	@Override
	public List<CallList> getCallList() {
		
		List<Contact> filteredList = contactRepository.findAll().stream()
				.filter(c -> c.getPhone().stream()
						.anyMatch(p -> p.getType().equals(Type.HOME)))
				.collect(Collectors.toList());
		
		List<Contact> sortedList = contactUtil.sortNames(filteredList);
		List<CallList> resultList = new ArrayList<>();

		for (Contact contact : sortedList) {
			
			Name name = new Name();

			if (StringUtils.isNotBlank(contact.getName().getFirst())) {
				name.setFirst(contact.getName().getFirst());
			}

			if (StringUtils.isNotBlank(contact.getName().getMiddle())) {
				name.setMiddle(contact.getName().getMiddle());
			}

			if (StringUtils.isNotBlank(contact.getName().getLast())) {
				name.setLast(contact.getName().getLast());
			}
			
			for(Phone phone : contact.getPhone()) {
				CallList callList = new CallList();
				callList.setName(name);
				
				if (phone.getType() != null && phone.getType().equals(Type.HOME) && StringUtils.isNotBlank(phone.getNumber())) {
					callList.setPhone(phone.getNumber());
					resultList.add(callList);
				}
				
			}
		}

		return resultList;
	}

	@Override
	public Contact getContactById(int id) {
		Optional<Contact> contactDb = contactRepository.findById(id);

		if (contactDb.isPresent()) {
			return contactDb.get();
		}
		throw new ResourceNotFoundException(NOT_FOUND + id);
	}

	@Override
	public Contact createContact(Contact contact) {
		return contactRepository.save(contact);
	}

	@Override
	public Contact updateContact(int id, Contact contact) {
		Optional<Contact> contactDb = contactRepository.findById(id);

		if (contactDb.isPresent()) {
			Contact updatedContact = contactDb.get();
			contact.setId(id);
			BeanUtils.copyProperties(contact, updatedContact, contactUtil.getNullPropertyNames(contact));
			contactRepository.save(updatedContact);
			return updatedContact;
		}
		throw new ResourceNotFoundException(NOT_FOUND + id);
	}

	@Override
	public String deleteContact(int id) {
		Optional<Contact> contactDb = contactRepository.findById(id);

		if (contactDb.isPresent()) {
			contactRepository.delete(contactDb.get()); 
			return "Contact have been deleted with id: " + id;
		}
		throw new ResourceNotFoundException(NOT_FOUND + id);
	}

}
