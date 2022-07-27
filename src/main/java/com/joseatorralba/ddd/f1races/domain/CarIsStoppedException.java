package com.joseatorralba.ddd.f1races.domain;

import lombok.Getter;

public class CarIsStoppedException extends Exception {

	private static final long serialVersionUID = 5075956279625268509L;
	
	@Getter private Car car;
	@Getter private CarIncident reason;

	public CarIsStoppedException(Car car, CarIncident reason)	{
		super(String.format("The car {} has been stopped. Cause: {}", car.getNumber(), reason));
		this.car = car;
		this.reason = reason;
	}
	
}
