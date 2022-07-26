package com.joseatorralba.ddd.f1races.domain;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;

/**
 * The Class Car.
 */
public class Car {
	
	@Getter private Integer number;
	
	@Getter private String teamName;
	
	/** The fuel comsumption per kilometre */
	private Float fuelConsumption;
	
	/** type of tyres */
	private Tyre tyres;
		
	// Car status
	/** litres of fuel **/
	@Getter private Float fuel;
	/** degradation status percentage **/
	@Getter private Float tyresDegradation;
	
	/** The tyres status calculator */
	@Autowired
	private TyreStatusCalculator tyresCalculator;
	
	
	/**
	 * Instantiates a new car.
	 *
	 * @param number the number
	 * @param constructor the constructor
	 * @param fuelComsumption the fuel comsumption
	 * @param tyres the tyres
	 */
	protected Car(Integer number, String constructor, Float fuelComsumption, Tyre tyres) {
		super();
		this.number = number;
		this.teamName = constructor;
		this.fuelConsumption = fuelComsumption;
		this.tyres = tyres;
	}
	
	/**
	 * Update the status of the car according to the kilometers traveled and the status of the track since the last update
	 *
	 * @param kilometers the kilometers traveled since the last update
	 * @param trackStatus the current track status
	 */
	protected void update(Integer kilometers, TrackStatus trackStatus)	{
		updateFuel(kilometers);
		updateTyreStatus(kilometers, trackStatus); 
	}
	
	/**
	 * Gets the grip of the tyres
	 *
	 * @param trackStatus the track status
	 * @return the tyres grip
	 */
	protected Float getTyresGrip(TrackStatus trackStatus)	{
		return tyresCalculator.calculateTyreGrip(tyres, trackStatus, this.tyresDegradation);
	}

	private void updateFuel(Integer kilometers) {
		this.fuel -= fuelConsumption * kilometers;
	}
	
	private void updateTyreStatus(Integer kilometers, TrackStatus trackStatus) {
		this.tyresDegradation = tyresCalculator.calculateTyreDegradation(tyres, trackStatus);
	}
	
}