package com.joseatorralba.ddd.f1races.domain;

import lombok.Getter;

@Getter
public enum Tyre {
	
	DRY (0),
	MEDIUM (1),
	RAIN (2);
	
	private int index;
	
	Tyre(int index)	{
		this.index = index;		
	}
}
