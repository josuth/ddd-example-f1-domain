package com.joseatorralba.ddd.f1races.domain.objectvalues;

import lombok.Getter;

@Getter
public enum TrackStatus {
	DRY (0),
	WET (1),
	DRENCHED (2);
	
	private int index;
	
	TrackStatus(int index)	{
		this.index = index;		
	}
}
