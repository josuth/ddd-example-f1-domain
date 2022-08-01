package com.joseatorralba.ddd.f1races.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import com.joseatorralba.ddd.f1races.domain.enums.CarIncident;
import com.joseatorralba.ddd.f1races.domain.exceptions.CarIsStoppedException;
import com.joseatorralba.ddd.f1races.domain.exceptions.RaceAlreadyRunningException;
import com.joseatorralba.ddd.f1races.domain.exceptions.TyredDriverException;

import lombok.Getter;

@Component
public class Race {

	private Circuit circuit;
	private Classification classification;
	@Getter private List<Car> carList;
	@Getter private boolean running;
	@Getter private boolean finished;
	
	public Race(Circuit circuit, List<Car> carList) {
		super();
		this.classification = new Classification();
		this.circuit = circuit;
		this.carList = carList;
		this.running = false;
		this.finished = false;
	}

	public void start()	{
		classification.start();
		carList.stream().forEach(c -> c.start());
		running = true;
	}
	
	public Position passFinishLine(Car car) throws CarIsStoppedException, TyredDriverException {
		if (this.running)	{
			car.move(circuit.getLapLenght(), circuit.getTrackStatus());
			Position p = this.classification.passToFinishLine(car);
			if (p.getLapsCompleted() >= circuit.getLapLenght())	{
				// car is winning. race is finish
				this.finished = true;
			}
			
			return p;
		}	
		throw new CarIsStoppedException(car, CarIncident.STOPPED); 
	}
	
	public List<Position> getClassification()	{
		return this.classification.getClassification();
	}
	
	public Car getWinner() throws RaceAlreadyRunningException {
		if (this.finished)	{
			List<Position> list = this.classification.getClassification();
			return list.get(0).getCar();
		}
		throw new RaceAlreadyRunningException();
	}
	
}
 