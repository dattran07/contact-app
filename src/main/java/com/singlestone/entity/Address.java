package com.singlestone.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable 
public class Address implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String street;
	private String city;
	private String state;
	private String zip;

}
