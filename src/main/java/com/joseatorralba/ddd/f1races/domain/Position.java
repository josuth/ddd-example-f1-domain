package com.joseatorralba.ddd.f1races.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@AllArgsConstructor
@Getter
public class Position implements Comparable<Position>	{
	
	private float time;
	private int lapsCompleted;
	private int positionNumber;
	private Car car;
	
	@Override
	public int compareTo(Position p) {
		if (this.lapsCompleted != p.getLapsCompleted())	{
			return p.getLapsCompleted() - this.lapsCompleted;
		}
		
		return (int) (this.time - p.time);
	}
	
}
