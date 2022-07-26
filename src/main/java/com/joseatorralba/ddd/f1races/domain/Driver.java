package com.joseatorralba.ddd.f1races.domain;

import lombok.Getter;

public class Driver {

	@Getter private String name;
	@Getter private Car car;	
	
	@Getter private int position;
	@Getter private float fatigue;
	
	public Driver(String name, Car car, int startingGridPosition) {
		super();
		this.name = name;
		this.car = car;
		this.position = startingGridPosition;
		
		this.fatigue = 0.0F;
	}
	
	public void update(Integer kilometers, TrackStatus trackStatus)	{
		updateFatigue(kilometers);
		car.update(kilometers, trackStatus);
	}

	private void updateFatigue(Integer kilometers) {
		this.fatigue -= 0.3 * kilometers;
	}
}
