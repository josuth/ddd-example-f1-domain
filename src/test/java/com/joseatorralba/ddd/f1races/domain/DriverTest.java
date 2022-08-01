package com.joseatorralba.ddd.f1races.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.joseatorralba.ddd.f1races.domain.exceptions.CarIsStoppedException;
import com.joseatorralba.ddd.f1races.domain.exceptions.TyredDriverException;

@ExtendWith(MockitoExtension.class)
public class DriverTest {
	
	@InjectMocks
	Driver driver;
	
	@BeforeEach
	public void init() {
		driver = new Driver("Fernando Alonso");
	}
	
	@Test
	public void givenDriver_whenDriverIsCreated_thenCheckMinInfo_test()	{
		assertEquals("Fernando Alonso", driver.getName());
		assertEquals(0, driver.getFatigue());
	}
	
	@Test
	public void givenDriver_whenDrives_thenDriverFatigueGrows_test() throws TyredDriverException, CarIsStoppedException	{
		driver.drive(10);
		
		assertTrue(driver.getFatigue() > 0.0F);
	}
	
	@Test
	public void givenDriver_whenDriverDrivesALot_thenDriverIsTyred_test() throws TyredDriverException, CarIsStoppedException	{
		TyredDriverException ex = assertThrows(TyredDriverException.class, () -> {
			driver.drive(400);	
		});
		
		assertEquals(driver.getName(), ex.getDriver().getName());
	}
}


