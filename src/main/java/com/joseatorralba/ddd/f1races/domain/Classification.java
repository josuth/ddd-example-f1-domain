package com.joseatorralba.ddd.f1races.domain;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
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
		Position position = table.get(car);
		if (position == null)	{
			position = Position.builder()
			.car(car)
			.time(time)
			.lapsCompleted(0)
			.positionNumber(null)
			.build();
		}
		position.setLapsCompleted(position.getLapsCompleted()+1);
		position.setTime(time);
		table.put(car, position);
	}
	
	private void updateTable()	{
		List<Position> orderedList = orderCars();
		int positionNumber = 1;
		for (Position p : orderedList)	{
			p.setPositionNumber(positionNumber++);
			table.put(p.getCar(), p);			
		}
	}

	private List<Position> orderCars() {
		List<Position> orderedList = table.values().stream()
				.sorted()
				.collect(Collectors.toList());
		return orderedList;
	}
}
