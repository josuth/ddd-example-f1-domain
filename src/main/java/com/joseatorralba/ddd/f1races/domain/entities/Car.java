package com.joseatorralba.ddd.f1races.domain.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joseatorralba.ddd.f1races.domain.domainservices.TyreStatusCalculator;
import com.joseatorralba.ddd.f1races.domain.exceptions.CarIsStoppedException;
import com.joseatorralba.ddd.f1races.domain.exceptions.TyredDriverException;
import com.joseatorralba.ddd.f1races.domain.objectvalues.CarIncident;
import com.joseatorralba.ddd.f1races.domain.objectvalues.TrackStatus;
import com.joseatorralba.ddd.f1races.domain.objectvalues.Tyre;

import lombok.Getter;

/**
 * The Class Car.
 */
@Component
public class Car {
	
	@Getter private Integer number;
	
	@Getter private String teamName;
	
	/** The fuel comsumption per kilometre */
	private Float fuelConsumption;
	
	/** type of tyres */
	private Tyre tyres;
	
	@Getter private Driver driver;
		
	// Car status
	/** litres of fuel **/
	@Getter private Float fuel;
	
	/** degradation status percentage **/
	@Getter private Float tyresDegradation;
	
	/** true if the car is started */
	@Getter private boolean started;
	
	/** The tyres status calculator */
	@Autowired
	TyreStatusCalculator tyresCalculator;
	
	
	/**
	 * Instantiates a new car.
	 *
	 * @param number the number
	 * @param teamName the team name
	 * @param fuelComsumption the fuel comsumption
	 * @param tyres the tyres
	 * @param driver the driver
	 */
	public Car(Integer number, String teamName, Float fuelComsumption, Tyre tyres, Driver driver) {
		this.number = number;
		this.teamName = teamName;
		this.fuelConsumption = fuelComsumption;
		this.tyres = tyres;
		this.driver = driver;
		
		this.fuel = 100.0F;
		this.tyresDegradation = 0.0F;
		this.started = false;
	}
	
	/**
	 * Start.
	 */
	public void start() {
		this.started = true;
	}
	
	/**
	 * Stop.
	 */
	public void stop() {
		this.started = false;
	}
	
	/**
	 * Update the status of the car according to the kilometers traveled and the status of the track since the last update
	 *
	 * @param kilometers the kilometers traveled since the last update
	 * @param trackStatus the current track status
	 * @throws CarIsStoppedException 
	 * @throws TyredDriverException 
	 */
	public void move(Integer kilometers, TrackStatus trackStatus) throws CarIsStoppedException, TyredDriverException	{
		updateFuel(kilometers);
		updateTyreStatus(kilometers, trackStatus); 
		driver.drive(kilometers);
	}
	
	/**
	 * Gets the grip of the tyres
	 *
	 * @param trackStatus the track status
	 * @return the tyres grip
	 */
	public Float getTyresGrip(TrackStatus trackStatus)	{
		return tyresCalculator.calculateTyreGrip(tyres, trackStatus) - this.tyresDegradation;
	}

	private void updateFuel(Integer kilometers) throws CarIsStoppedException {
		this.fuel -= fuelConsumption * kilometers;
		if (this.fuel <= 0.0F)	{
			this.started = false;
			throw new CarIsStoppedException(this, CarIncident.LOW_FUEL);
		}
	}
	
	private void updateTyreStatus(Integer kilometers, TrackStatus trackStatus) {
		this.tyresDegradation += tyresCalculator.calculateTyreDegradation(tyres, trackStatus);
	}
	
}
