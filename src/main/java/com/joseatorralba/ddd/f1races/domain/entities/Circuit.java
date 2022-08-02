package com.joseatorralba.ddd.f1races.domain.entities;

import com.joseatorralba.ddd.f1races.domain.objectvalues.TrackStatus;

import lombok.Getter;

@Getter
public class Circuit {
	private String name;
	private Integer lapLenght;
	private Integer laps;
	private TrackStatus trackStatus;
	
	public Circuit(String name, Integer lapLenght, Integer laps) {
		this.name = name;
		this.lapLenght = lapLenght;
		this.laps = laps;
		this.trackStatus = TrackStatus.DRY;
	}
	
	public void changeTrackStatus(TrackStatus status)	{
		this.trackStatus = status;
	}

}
