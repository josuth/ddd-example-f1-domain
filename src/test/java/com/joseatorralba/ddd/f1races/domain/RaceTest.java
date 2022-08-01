package com.joseatorralba.ddd.f1races.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.joseatorralba.ddd.f1races.domain.enums.CarIncident;
import com.joseatorralba.ddd.f1races.domain.enums.TrackStatus;
import com.joseatorralba.ddd.f1races.domain.exceptions.CarIsStoppedException;
import com.joseatorralba.ddd.f1races.domain.exceptions.RaceAlreadyRunningException;
import com.joseatorralba.ddd.f1races.domain.exceptions.TyredDriverException;

@ExtendWith(MockitoExtension.class)
public class RaceTest {

	@Mock
	private Car car;
	
	@Mock 
	private Circuit circuit;
	
	@Mock 
	private Classification classification;
	
	@InjectMocks
	private Race race;
	
	@BeforeEach
	public void init()	{
		race = new Race(circuit, List.of(car));		
	}
	
	@Test 
	public void givenCarList_whenRaceIsCreated_thenCheckMinInfo_test()	{
		assertEquals(1, race.getCarList().size());
		assertTrue(!race.isFinished());
		assertTrue(!race.isRunning());
	}
	
	@Test
	public void givenRace_whenStart_thenRaceAndCarIsStarted_test()	{
		doNothing().when(car).start();
		when(car.isStarted()).thenReturn(Boolean.TRUE);
		
		race.start();
		
		assertTrue(race.isRunning());
		for (Car c : race.getCarList()) {
			assertTrue(c.isStarted());	
		}
		
	}
	
	@Test
	public void givenRunningRace_whenCarPassesFinishLine_thenCarPositionIsUpdated_test() throws CarIsStoppedException, TyredDriverException	{

		doNothing().when(car).move(any(), any());
		when(circuit.getLapLenght()).thenReturn(3);
		when(circuit.getTrackStatus()).thenReturn(TrackStatus.DRY);
		
		race.start();
		
		Position p = race.passFinishLine(car);

		assertNotNull(p);
		
	}
	
	@Test
	public void givenRunningRace_whenCarPassesFinishLineLastTime_thenWinnerExists_test() throws CarIsStoppedException, TyredDriverException, RaceAlreadyRunningException	{

		doNothing().when(car).move(any(), any());
		when(circuit.getLapLenght()).thenReturn(3);
		when(circuit.getTrackStatus()).thenReturn(TrackStatus.DRY);
		
		race.start();
		
		// Lap 1
		race.passFinishLine(car);
		assertTrue(!race.isFinished());
		assertThrows(RaceAlreadyRunningException.class, () -> {
			race.getWinner();
		});
				
		// Lap 2
		race.passFinishLine(car);
		assertTrue(!race.isFinished());
		assertThrows(RaceAlreadyRunningException.class, () -> {
			race.getWinner();
		});
		
		// Final Lap
		race.passFinishLine(car);
		assertTrue(race.isFinished());
		assertEquals(car, race.getWinner());
		
	}
	
	@Test
	public void givenNotRunningRace_whenCarPassesFinishLineLastTime_thenExceptionOccurs_test() throws CarIsStoppedException, TyredDriverException, RaceAlreadyRunningException	{
		
		CarIsStoppedException ex = assertThrows(CarIsStoppedException.class, () -> {
			race.passFinishLine(car);
		});
		assertEquals(CarIncident.STOPPED, ex.getReason());
		
	}
	
	@Test
	public void givenRace_whenGetClassification_thenClasificationIsReturned_test()	{
		List<Position> list = race.getClassification();
		assertNotNull(list);
	}
}
