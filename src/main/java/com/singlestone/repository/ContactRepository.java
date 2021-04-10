package com.singlestone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.singlestone.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

}
