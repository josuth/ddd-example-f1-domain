package com.joseatorralba.ddd.f1races.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class ClassificationTest {

	@InjectMocks
	private Classification classification;
	
	@Test
	public void givenCar_whenCarPassesToFinishLine_thenCarIsTheFirst_test()	{
		Car car1 = new Car(8, "Renault", 1.2F, Tyre.DRY, new Driver("Fernando Alonso"));
		
		classification.passToFinishLine(car1);
		
		assertEquals(1, classification.getPosition(car1).getPositionNumber());
	}
	
	@Test
	public void givenMultiplesCars_whenCarsPassToFinishLine_thenCarsAreSorted_test()	{
		Car car1 = new Car(8, "Renault", 1.2F, Tyre.DRY, new Driver("Fernando Alonso"));
		Car car2 = new Car(23, "McLaren", 1.2F, Tyre.DRY, new Driver("Lewis Hamilton"));
		Car car3 = new Car(1, "Ferrari", 1.2F, Tyre.DRY, new Driver("Michael Schumacher"));
		
		classification.start();

		log.info("------ LAP 1 ------");
		assertEquals(1, classification.passToFinishLine(car3).getPositionNumber());
		sleep();
		assertEquals(2, classification.passToFinishLine(car2).getPositionNumber());
		sleep();
		assertEquals(3, classification.passToFinishLine(car1).getPositionNumber());
		sleep();
		
		log.info("------ LAP 2 ------");
		assertEquals(1, classification.passToFinishLine(car3).getPositionNumber());
		sleep();
		assertEquals(2, classification.passToFinishLine(car1).getPositionNumber());
		sleep();
		assertEquals(3, classification.passToFinishLine(car2).getPositionNumber());
		sleep();

		log.info("------ FINISH ------");
		assertEquals(1, classification.passToFinishLine(car1).getPositionNumber());
		sleep();
		assertEquals(2, classification.passToFinishLine(car3).getPositionNumber());
		sleep();
		assertEquals(3, classification.passToFinishLine(car2).getPositionNumber());
		sleep();
		
		// Check positions
		List<Position> orderedList = classification.getClassification();
		assertEquals(1, orderedList.get(0).getPositionNumber());
		assertEquals(2, orderedList.get(1).getPositionNumber());
		assertEquals(3, orderedList.get(2).getPositionNumber());

		// Check Times
		assertTrue(orderedList.get(0).getTime() < orderedList.get(1).getTime());
		assertTrue(orderedList.get(1).getTime() < orderedList.get(2).getTime());
		
		// Check Laps
		assertEquals(3, orderedList.get(0).getLapsCompleted());
		assertEquals(3, orderedList.get(1).getLapsCompleted());
		assertEquals(3, orderedList.get(2).getLapsCompleted());
	}

	private void sleep() {
		Random r = new Random(System.currentTimeMillis());
		try {
			TimeUnit.MILLISECONDS.sleep(r.nextInt(100));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
