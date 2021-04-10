package com.singlestone.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "contacts")
public class Contact implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Embedded
	@AttributeOverride( name = "first", column = @Column(name = "first_name"))
	@AttributeOverride( name = "middle", column = @Column(name = "middle_name"))
	@AttributeOverride( name = "last", column = @Column(name = "last_name"))
	private Name name;
	
	@Embedded
	@AttributeOverride( name = "street", column = @Column(name = "street_address"))
	@AttributeOverride( name = "city", column = @Column(name = "city"))
	@AttributeOverride( name = "state", column = @Column(name = "state"))
	@AttributeOverride( name = "zip", column = @Column(name = "zip"))
	private Address address;
	
	@ElementCollection
	@CollectionTable(
        name = "contacts_phone",
        joinColumns = @JoinColumn(name="contacts_id")
	)
	@AttributeOverride( name = "number", column = @Column(name = "phone_number"))
	@AttributeOverride( name = "type", column = @Column(name = "phone_type"))
	private List<Phone> phone;
	
	private String email;
	
}

