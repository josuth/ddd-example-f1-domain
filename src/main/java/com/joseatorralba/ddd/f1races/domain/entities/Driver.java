package com.joseatorralba.ddd.f1races.domain.entities;

import com.joseatorralba.ddd.f1races.domain.exceptions.TyredDriverException;

import lombok.Getter;

public class Driver {

	private static final double FATIGUE_PER_KM = 0.3;

	private static final float FATIGUE_BOUND = 80.0F;
	
	@Getter private String name;
	
	@Getter private float fatigue;
	
	public Driver(String name) {
		this.name = name;
		this.fatigue = 0.0F;
	}
	
	public void drive(Integer kilometers) throws TyredDriverException	{
		updateFatigue(kilometers);
	}

	private void updateFatigue(Integer kilometers) throws TyredDriverException {
		this.fatigue += FATIGUE_PER_KM * kilometers;
		if (this.fatigue >= FATIGUE_BOUND)	{
			throw new TyredDriverException(this);
		}
	}
}
