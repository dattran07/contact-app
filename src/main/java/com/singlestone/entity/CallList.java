package com.singlestone.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class CallList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Name name;
	private String phone;
	
}
