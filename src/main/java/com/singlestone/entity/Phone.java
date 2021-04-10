package com.singlestone.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Phone implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int HOME = 0;
	public static final int WORK = 1;
	public static final int MOBILE = 2;
	
	private String number;
	private Type type;
	
	public enum Type {
		HOME, WORK, MOBILE
	}

}
