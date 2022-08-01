package com.joseatorralba.ddd.f1races.domain.exceptions;

public class RaceAlreadyRunningException extends Exception {

	private static final long serialVersionUID = -3989530358903980594L;
	
	public RaceAlreadyRunningException()	{
		super("Race is already running");
	}

}
