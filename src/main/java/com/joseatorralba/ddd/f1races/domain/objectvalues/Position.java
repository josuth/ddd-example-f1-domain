package com.joseatorralba.ddd.f1races.domain.objectvalues;

import com.joseatorralba.ddd.f1races.domain.entities.Car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Position implements Comparable<Position>	{
	
	private float time;
	private int lapsCompleted;
	private Integer positionNumber;
	private Car car;
	
	@Override
	public int compareTo(Position p) {
		if (this.lapsCompleted != p.getLapsCompleted())	{
			return p.getLapsCompleted() - this.lapsCompleted;
		}
		
		return (int) (this.time - p.time);
	}
	
}
