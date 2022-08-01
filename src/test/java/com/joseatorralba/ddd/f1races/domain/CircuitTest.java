package com.joseatorralba.ddd.f1races.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.joseatorralba.ddd.f1races.domain.enums.TrackStatus;

@ExtendWith(MockitoExtension.class)
public class CircuitTest {

	@InjectMocks
	Circuit circuit;
	
	@BeforeEach
	public void init()	{
		circuit = new Circuit("Imola", 80, 62);
	}
	
	@Test
	public void givenInitData_whenCircuitIsCreated_thenCheckData_test()	{
		assertEquals("Imola", circuit.getName());
		assertEquals(80, circuit.getLapLenght());
		assertEquals(62, circuit.getLaps());
	}
	
	@Test
	public void givencircuit_whenWeatherChanges_thenTrackStatusIsChanged_test()	{
		circuit.changeTrackStatus(TrackStatus.DRENCHED);
		
		assertEquals(TrackStatus.DRENCHED, circuit.getTrackStatus());
	}
}
