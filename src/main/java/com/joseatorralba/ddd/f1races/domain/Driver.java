package com.joseatorralba.ddd.f1races.domain;

import lombok.Getter;

public class Driver {

	private static final double FATIGUE_PER_KM = 0.3;

	private static final float FATIGUE_BOUND = 80.0F;
	
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
	
	public void update(Integer kilometers, TrackStatus trackStatus) throws TyredDriverException, CarIsStoppedException	{
		updateFatigue(kilometers);
		car.update(kilometers, trackStatus);
	}

	private void updateFatigue(Integer kilometers) throws TyredDriverException {
		this.fatigue += FATIGUE_PER_KM * kilometers;
		if (this.fatigue >= FATIGUE_BOUND)	{
			throw new TyredDriverException(this);
		}
	}
}
