package com.joseatorralba.ddd.f1races.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
public class Position implements Comparable<Position>	{
	
	@Setter private float time;
	@Setter private int lapsCompleted;
	@Setter private Integer positionNumber;
	private Car car;
	
	@Override
	public int compareTo(Position p) {
		if (this.lapsCompleted != p.getLapsCompleted())	{
			return p.getLapsCompleted() - this.lapsCompleted;
		}
		
		return (int) (this.time - p.time);
	}
	
}
