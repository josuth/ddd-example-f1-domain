package com.joseatorralba.ddd.f1races.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.joseatorralba.ddd.f1races.domain.domainservices.TyreStatusCalculator;
import com.joseatorralba.ddd.f1races.domain.entities.Car;
import com.joseatorralba.ddd.f1races.domain.entities.Driver;
import com.joseatorralba.ddd.f1races.domain.exceptions.CarIsStoppedException;
import com.joseatorralba.ddd.f1races.domain.objectvalues.CarIncident;
import com.joseatorralba.ddd.f1races.domain.objectvalues.TrackStatus;
import com.joseatorralba.ddd.f1races.domain.objectvalues.Tyre;

@ExtendWith(MockitoExtension.class)
public class CarTest {
		
	@InjectMocks
	Car car;
	 
	@Mock
	Driver driver;
	
	@Mock
	TyreStatusCalculator tyresCalculator;
	
	@BeforeEach
	public void init()	{
		car = new Car(1, "Ferrari", 1.5F, Tyre.DRY, driver);
		ReflectionTestUtils.setField(car, "tyresCalculator", tyresCalculator);
	}
	
	@Test
	public void givenCar_whenCarIsCreated_thenCheckMinInfo_test()	{
		
		assertNotNull(car.getNumber());
		assertNotNull(car.getTeamName());
		assertEquals(100.0F, car.getFuel());
		assertEquals(0.0F, car.getTyresDegradation());
		assertEquals(false, car.isStarted());
		assertNotNull(car.getDriver());
	}

	@Test
	public void givenCar_whenCarIsCreated_thenReturnTyresDegradation_test()	{
		when(tyresCalculator.calculateTyreGrip(any(), any())).thenReturn(100.0F);
				
		assertEquals(100.0F, car.getTyresGrip(TrackStatus.DRY));
	}
	
	@Test
	public void givenCar_whenCarMovesForward_thenFuelIsConsumed_test() throws Exception	{
		car.move(1, TrackStatus.DRY);
		
		// As the fuel consumption car is defined at 1.5 per KM, if car moves 1Km, fuel is 98.5
		assertEquals(98.5F, car.getFuel());
	}
	
	@Test
	public void givenCar_whenCarMovesForward_thenTyresAreDegradated_test() throws Exception	{
		when(tyresCalculator.calculateTyreDegradation(any(), any())).thenReturn(0.05F); 
		
		car.move(10, TrackStatus.DRY);
		
		assertEquals(0.05F, car.getTyresDegradation());
	}
	
	@Test
	public void givenCar_whenCarMovesForwardALot_thenFuelIsEmptyAndCarStops_test() throws Exception	{
		
		CarIsStoppedException ex = assertThrows(CarIsStoppedException.class, () -> {
			car.move(200, TrackStatus.DRY);
		});
		
		assertEquals(CarIncident.LOW_FUEL, ex.getReason());
		assertEquals(car.getNumber(), ex.getCar().getNumber());
		assertEquals(false, car.isStarted());
	}
	
	@Test
	public void givenCar_whenStartTheCar_thenCarIsStarted_test()	{
		 car.start();
		 
		 assertTrue(car.isStarted());
	}
	
	@Test
	public void givenCar_whenStopTheCar_thenCarIsNotStarted_test()	{
		 car.stop();
		 
		 assert(!car.isStarted());
	}
}
