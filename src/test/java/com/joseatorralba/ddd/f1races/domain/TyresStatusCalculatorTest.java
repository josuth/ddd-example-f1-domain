package com.joseatorralba.ddd.f1races.domain;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TyresStatusCalculatorTest {

	@InjectMocks
	private TyreStatusCalculator domainService;
	
	@Test
	public void givenTyreAndTrackStatus_whenTrackIsDry_thenDryTyreIsLessDegradated_test()	{
		Float dryTyreDegradation = domainService.calculateTyreDegradation(Tyre.DRY, TrackStatus.DRY);
		Float mediumTyreDegradation = domainService.calculateTyreDegradation(Tyre.MEDIUM, TrackStatus.DRY);
		Float rainTyreDegradation = domainService.calculateTyreDegradation(Tyre.RAIN, TrackStatus.DRY);
		
		assertTrue( dryTyreDegradation <= mediumTyreDegradation );
		assertTrue( mediumTyreDegradation <= rainTyreDegradation );
	}
	
	@Test
	public void givenTyreAndTrackStatus_whenTrackIsWet_thenMediumOrRainTyresAreLessDegradated_test()	{
		Float dryTyreDegradation = domainService.calculateTyreDegradation(Tyre.DRY, TrackStatus.WET);
		Float mediumTyreDegradation = domainService.calculateTyreDegradation(Tyre.MEDIUM, TrackStatus.WET);
		Float rainTyreDegradation = domainService.calculateTyreDegradation(Tyre.RAIN, TrackStatus.WET);
		
		assertTrue( mediumTyreDegradation <= dryTyreDegradation );
		assertTrue( rainTyreDegradation <= dryTyreDegradation );
	}
	
	@Test
	public void givenTyreAndTrackStatus_whenTrackIsDrenched_thenRainTyreIsLessDegradated_test()	{
		Float dryTyreDegradation = domainService.calculateTyreDegradation(Tyre.DRY, TrackStatus.DRENCHED);
		Float mediumTyreDegradation = domainService.calculateTyreDegradation(Tyre.MEDIUM, TrackStatus.DRENCHED);
		Float rainTyreDegradation = domainService.calculateTyreDegradation(Tyre.RAIN, TrackStatus.DRENCHED);
		
		assertTrue( rainTyreDegradation <= mediumTyreDegradation );
		assertTrue( mediumTyreDegradation <= dryTyreDegradation );
	}
	
	@Test
	public void givenTyreAndTrackStatus_whenTrackIsDry_thenDryTyreIsGripper_test()	{
		Float dryTyreGrip = domainService.calculateTyreGrip(Tyre.DRY, TrackStatus.DRY);
		Float mediumTyreGrip = domainService.calculateTyreGrip(Tyre.MEDIUM, TrackStatus.DRY);
		Float rainTyreGrip = domainService.calculateTyreGrip(Tyre.RAIN, TrackStatus.DRY);
		
		assertTrue( dryTyreGrip >= mediumTyreGrip );
		assertTrue( mediumTyreGrip >= rainTyreGrip );
	}
	
	@Test
	public void givenTyreAndTrackStatus_whenTrackIsWet_thenMediumTyreIsGripper_test()	{
		Float dryTyreGrip = domainService.calculateTyreGrip(Tyre.DRY, TrackStatus.WET);
		Float mediumTyreGrip = domainService.calculateTyreGrip(Tyre.MEDIUM, TrackStatus.WET);
		Float rainTyreGrip = domainService.calculateTyreGrip(Tyre.RAIN, TrackStatus.WET);
		
		assertTrue( mediumTyreGrip >= dryTyreGrip );
		assertTrue( mediumTyreGrip >= rainTyreGrip );
	}
	
	@Test
	public void givenTyreAndTrackStatus_whenTrackIsDrenched_thenRainTyreIsGripper_test()	{
		Float dryTyreGrip = domainService.calculateTyreGrip(Tyre.DRY, TrackStatus.DRENCHED);
		Float mediumTyreGrip = domainService.calculateTyreGrip(Tyre.MEDIUM, TrackStatus.DRENCHED);
		Float rainTyreGrip = domainService.calculateTyreGrip(Tyre.RAIN, TrackStatus.DRENCHED);
		
		assertTrue( dryTyreGrip <= mediumTyreGrip );
		assertTrue( mediumTyreGrip <= rainTyreGrip );
	}
}
