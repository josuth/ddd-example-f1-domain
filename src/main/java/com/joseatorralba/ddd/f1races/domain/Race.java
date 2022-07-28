package com.joseatorralba.ddd.f1races.domain;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Race {

	public void start(Classification classification, List<Car> carList)	{
		classification.start();
		carList.stream().forEach(c -> c.start());
	}
	
	public Position passFinishLine(Classification classification, Car car) {
		return classification.passToFinishLine(car);
	}
	
}
 