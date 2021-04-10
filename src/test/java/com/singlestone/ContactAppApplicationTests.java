package com.singlestone;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.singlestone.entity.Address;
import com.singlestone.entity.CallList;
import com.singlestone.entity.Contact;
import com.singlestone.entity.Name;
import com.singlestone.entity.Phone;
import com.singlestone.entity.Phone.Type;
import com.singlestone.repository.ContactRepository;
import com.singlestone.service.ContactService;
import com.singlestone.util.ContactUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
class ContactAppApplicationTests {

	@Autowired
	private ContactService service;
	
	@Autowired
	ContactUtil contactUtil;

	@MockBean
	private ContactRepository repository;

	@Test
	void getContactsTest() {
		when(repository.findAll()).thenReturn(init());
		assertEquals(2, service.getContacts().size());
	}
	
	@Test
	void getContactByIdTest() {
		List<Contact> contactList = init();
		Optional<Contact> contact = Optional.of(contactList.get(1));
		when(repository.findById(2)).thenReturn(contact);
		assertEquals("Chris", contact.get().getName().getFirst());
		assertEquals("Ohio", contact.get().getAddress().getState());
	}
	
	@Test
	void createContactTest() {
		Contact contact = createContact();
		when(repository.save(contact)).thenReturn(contact);
		assertEquals(contact, service.createContact(contact));
	}
	
	@Test
	void updateContactTest() {
		List<Contact> contactList = init();
		Contact contact = contactList.get(1);
		
		Name name = new Name();
		name.setFirst("Abigail");
		name.setLast("Wands");
		contact.setName(name);
		
		when(repository.save(contact)).thenReturn(contact);
		assertEquals("Abigail", contactList.get(1).getName().getFirst());
		assertEquals("Wands", contactList.get(1).getName().getLast());
	}
	
	@Test
	void deleteContactTest() {
		List<Contact> contactList = init();
		Optional<Contact> contact = Optional.of(contactList.get(1));
		when(repository.findById(2)).thenReturn(contact);
		
		service.deleteContact(2);
		verify(repository, times(1)).delete(contact.get());
	}
	
	@Test
	void getCallListTest() {
		List<Contact> filteredList = init().stream()
				.filter(c -> c.getPhone().stream()
						.anyMatch(p -> p.getType().equals(Type.HOME)))
				.collect(Collectors.toList());
		
		List<Contact> sortedList = contactUtil.sortNames(filteredList);
		List<CallList> resultList = new ArrayList<>();
		
		for (Contact contact : sortedList) {
			CallList callList = new CallList();
			
			for(Phone phone : contact.getPhone()) {
				if (phone.getType().equals(Type.HOME) && StringUtils.isNotBlank(phone.getNumber())) {
					callList.setPhone(phone.getNumber());
				}
			}
			
			resultList.add(callList);
		}
		
		when(service.getCallList()).thenReturn(resultList);
		assertEquals("654-234-6521", resultList.get(0).getPhone());
		assertEquals("982-342-1375", resultList.get(1).getPhone());
	}
	
	public List<Contact> init() {

		List<Contact> contactList = new ArrayList<>();

		Contact contact1 = new Contact();

		Name name1 = new Name();
		name1.setFirst("Cynthy");
		name1.setMiddle("Glennis");
		name1.setLast("Balch");

		Address address1 = new Address();
		address1.setStreet("931 Calypso Center");
		address1.setCity("Salt Lake City");
		address1.setState("Utah");
		address1.setZip("76914");

		List<Phone> phoneList1 = new ArrayList<>();
		Phone phone1 = new Phone();
		phone1.setNumber("654-234-6521");
		phone1.setType(Type.HOME);
		phoneList1.add(phone1);

		Phone phone2 = new Phone();
		phone2.setNumber("345-645-1231");
		phone2.setType(Type.WORK);
		phoneList1.add(phone2);

		contact1.setId(1);
		contact1.setName(name1);
		contact1.setAddress(address1);
		contact1.setPhone(phoneList1);
		contact1.setEmail("gbalch0@delicious.com");

		contactList.add(contact1);

		Contact contact2 = new Contact();

		Name name2 = new Name();
		name2.setFirst("Chris");
		name2.setMiddle("Jacky");
		name2.setLast("Mayne");

		Address address2 = new Address();
		address2.setStreet("24150 Washington Trail");
		address2.setCity("Cleveland");
		address2.setState("Ohio");
		address2.setZip("65133");

		List<Phone> phoneList2 = new ArrayList<>();
		Phone phone3 = new Phone();
		phone3.setNumber("982-342-1375");
		phone3.setType(Type.HOME);
		phoneList2.add(phone3);

		Phone phone4 = new Phone();
		phone4.setNumber("543-534-7854");
		phone4.setType(Type.MOBILE);
		phoneList2.add(phone4);

		contact2.setId(2);
		contact2.setName(name2);
		contact2.setAddress(address2);
		contact2.setPhone(phoneList2);
		contact2.setEmail("birons6@harvard.edu");

		contactList.add(contact2);

		return contactList;
	}
	
	public Contact createContact() {
		
		int id = init().size() + 1;
		Contact contact = new Contact();

		Name name = new Name();
		name.setFirst("Amber");
		name.setMiddle("Pip");
		name.setLast("Wands");

		Address address = new Address();
		address.setStreet("21906 Southridge Street");
		address.setCity("Chicago");
		address.setState("Illinois");
		address.setZip("97700");

		List<Phone> phoneList = new ArrayList<>();
		Phone phone1 = new Phone();
		phone1.setNumber("789-312-4564");
		phone1.setType(Type.HOME);
		phoneList.add(phone1);

		Phone phone2 = new Phone();
		phone2.setNumber("234-464-6314");
		phone2.setType(Type.WORK);
		phoneList.add(phone2);

		contact.setId(id);
		contact.setName(name);
		contact.setAddress(address);
		contact.setPhone(phoneList);
		contact.setEmail("gmerwood0@mapquest.com");
		
		return contact;
	}

}
