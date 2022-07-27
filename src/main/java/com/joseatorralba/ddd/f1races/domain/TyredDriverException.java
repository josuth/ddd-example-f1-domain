package com.joseatorralba.ddd.f1races.domain;

import lombok.Getter;

public class TyredDriverException extends Exception {

	private static final long serialVersionUID = 5580384951211145604L;

	@Getter private Driver driver;
	
	public TyredDriverException(Driver driver)	{
		super(String.format("The driver {} is tyred", driver.getName()));
		this.driver = driver;
	}
}
