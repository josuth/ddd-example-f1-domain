package com.joseatorralba.ddd.f1races.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DriverTest {
	
	@Mock
	Car car = new Car(1, "Ferrari", 0.10F, Tyre.DRY);
	
	@InjectMocks
	Driver driver = new Driver("Fernando Alonso", car, 1);
	
	@Test
	public void givenDriver_whenDriverIsCreated_thenCheckMinInfo_test()	{
		
		assertEquals("Fernando Alonso", driver.getName());
		assertEquals(0, driver.getFatigue());
		assertEquals(1, driver.getPosition());
		assertNotNull(driver.getCar());
		
	}
	
	@Test
	public void givenDriver_whenDrives_thenDriverFatigueGrows_test() throws TyredDriverException, CarIsStoppedException	{
		driver.update(10, TrackStatus.DRY);
		
		assertTrue(driver.getFatigue() > 0.0F);
	}
	
	@Test
	public void givenDriver_whenDriverDrivesALot_thenDriverIsTyred_test() throws TyredDriverException, CarIsStoppedException	{
		TyredDriverException ex = assertThrows(TyredDriverException.class, () -> {
			driver.update(400, TrackStatus.DRY);	
		});
		
		assertEquals(driver.getName(), ex.getDriver().getName());
	}
}


