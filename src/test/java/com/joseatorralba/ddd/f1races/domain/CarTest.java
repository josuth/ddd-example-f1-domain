package com.joseatorralba.ddd.f1races.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CarTest {
	
	@InjectMocks
	Car car = new Car(1, "Ferrari", 1.5F, Tyre.DRY);
	
	@Mock
	TyreStatusCalculator tyresCalculator;
	
	@Test
	public void givenCar_whenCarIsCreated_thenCheckMinInfo_test()	{
		assertNotNull(car.getNumber());
		assertNotNull(car.getTeamName());
		assertEquals(100.0F, car.getFuel());
		assertEquals(0.0F, car.getTyresDegradation());
		assertEquals(false, car.isStarted());
	}

	@Test
	public void givenCar_whenCarIsCreated_thenReturnTyresDegradation_test()	{
		when(tyresCalculator.calculateTyreGrip(any(), any())).thenReturn(100.0F);
				
		assertEquals(100.0F, car.getTyresGrip(TrackStatus.DRY));
	}
	
	@Test
	public void givenCar_whenCarMovesForward_thenFuelIsConsumed_test() throws CarIsStoppedException	{
		car.update(1, TrackStatus.DRY);
		
		// As the fuel consumption car is defined at 1.5 per KM, if car moves 1Km, fuel is 98.5
		assertEquals(98.5F, car.getFuel());
	}
	
	@Test
	public void givenCar_whenCarMovesForward_thenTyresAreDegradated_test() throws CarIsStoppedException	{
		when(tyresCalculator.calculateTyreDegradation(any(), any())).thenReturn(0.05F); 
		
		car.update(10, TrackStatus.DRY);
		
		assertEquals(0.05F, car.getTyresDegradation());
	}
	
	@Test
	public void givenCar_whenCarMovesForwardALot_thenFuelIsEmptyAndCarStops_test() throws CarIsStoppedException	{
		
		CarIsStoppedException ex = assertThrows(CarIsStoppedException.class, () -> {
			car.update(200, TrackStatus.DRY);
		});
		
		assertEquals(CarIncident.LOW_FUEL, ex.getReason());
		assertEquals(car.getNumber(), ex.getCar().getNumber());
		assertEquals(false, car.isStarted());
	}
}
