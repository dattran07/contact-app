package com.singlestone.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import com.singlestone.entity.Contact;

@Component
public class ContactUtil {

	public String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	public List<Contact> sortNames(List<Contact> contactList) {
		Collections.sort(contactList, new Comparator<Contact>() {
			public int compare(Contact c1, Contact c2) {
				int res = c1.getName().getLast().compareToIgnoreCase(c2.getName().getLast());
				if (res != 0)
					return res;
				return c1.getName().getFirst().compareToIgnoreCase(c2.getName().getFirst());
			}
		});
		
		return contactList;
	}

}
