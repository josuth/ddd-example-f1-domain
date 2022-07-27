package com.joseatorralba.ddd.f1races.domain;

import org.springframework.stereotype.Component;

/**
 * The Class TyreStatusCalculator.
 */
@Component
public class TyreStatusCalculator {

	/** The degradation matrix. type of tyre X track status */
	private float degradationMatrix[][] = {
			{  5.0F,  6.0F, 6.0F },
			{ 10.0F,  4.0F, 5.0F },
			{ 10.0F,  5.0F, 4.0F }};
	
	/** The grip matrix. Type of tyre X track status  */
	private float gripMatrix[][] = {
			{ 97.0F, 85.0F, 60.0F },
			{ 90.0F, 95.0F, 80.0F },
			{ 80.0F, 90.0F, 95.0F }};
	
	/**
	 * Calculate tyre degradation.
	 *
	 * @param tyre the tyre
	 * @param trackStatus the track status
	 * @return the float
	 */
	public Float calculateTyreDegradation(Tyre tyre, TrackStatus trackStatus)	{
		return degradationMatrix[tyre.getIndex()][trackStatus.getIndex()];
	}
	
	/**
	 * Calculate tyre grip.
	 *
	 * @param tyre the tyre
	 * @param trackStatus the track status
	 * @return the float
	 */
	public Float calculateTyreGrip(Tyre tyre, TrackStatus trackStatus)	{
		return gripMatrix[tyre.getIndex()][trackStatus.getIndex()];
	}
	
}
