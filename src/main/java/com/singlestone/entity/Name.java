package com.singlestone.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable 
public class Name implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String first;
	private String middle;
	private String last;
	
}
