package com.joseatorralba.ddd.f1races.domain.entities;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.joseatorralba.ddd.f1races.domain.objectvalues.Position;

public class Classification {

	private HashMap<Car, Position> table;
	
	private long startTime;
	
	public Classification()	{
		this.table = new HashMap<Car, Position>();
	}
	
	public void start()	{
		this.startTime = System.currentTimeMillis();
	}
	
	public synchronized Position passToFinishLine(Car car)	{
		long time = System.currentTimeMillis() - this.startTime;
		updateCarPosition(car, time);
		updateTable();
		Position p = getPosition(car);
		return p;
	}
	
	public Position getPosition(Car car) {
		return table.get(car);
	}
	
	public List<Position> getClassification()	{
		return orderCars();
	}

	private void updateCarPosition(Car car, long time) {
		Position p = table.get(car);
		if (p == null)	{
			table.put(car, Position.builder()
					.car(car)
					.time(time)
					.lapsCompleted(1)
					.positionNumber(null)
					.build());
		
		} else {
			table.put(car, Position.builder()
					.car(car)
					.time(time)
					.lapsCompleted(p.getLapsCompleted()+1)
					.positionNumber(p.getPositionNumber())
					.build());
		}
	}
	
	private void updateTable()	{
		List<Position> orderedList = orderCars();
		int positionNumber = 1;
		for (Position p : orderedList)	{
			table.put(p.getCar(), Position.builder()
					.car(p.getCar())
					.time(p.getTime())
					.lapsCompleted(p.getLapsCompleted())
					.positionNumber(positionNumber++)
					.build());			
		}
	}

	private List<Position> orderCars() {
		List<Position> orderedList = table.values().stream()
				.sorted()
				.collect(Collectors.toList());
		return orderedList;
	}
}
