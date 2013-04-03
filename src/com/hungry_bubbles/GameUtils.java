package com.hungry_bubbles;

/**
 * Collection of general utility methods. 
 * 
 * @author Timothy Heard, Shaun DeVos, John O'Brien, Mustafa Al Salihi
 */
public class GameUtils
{
	/**
	 * Utility method for validating parameters and constructor arguments which
	 * must not be null.
	 * 
	 * @throws IllegalArgumentException		If the provided {@code Object} is
	 * 										null.
	 */
	public static void throwIfNull(String className, String argName, Object obj)
		throws IllegalArgumentException
	{
		if(obj == null)
		{
			throw new IllegalArgumentException("In " + className + ": " +
				"'" + argName + "' cannot be null");
		}
	}

	/**
	 * Utility method for validating parameters and constructor arguments which
	 * must be a valid angle from 0to 360 degrees (inclusive).
	 * 
	 * @throws IllegalArgumentException	  If the provided {@code angle} is not
	 * 									  between the values of 0 and 360
	 * 									  (inclusively).
	 */
	public static void throwIfInvalidAngle(String className, String argName, 
		int angle)
		throws IllegalArgumentException
	{
		if(angle < 0 || angle > 360)
		{
			throw new IllegalArgumentException("In " + className + ": " +
				"The value of '" + argName + "' was " + angle +   
				" is not a valid angle. Angle values must be between 0 " +
				"and 360 inclusively");
		}
	}

	/**
	 * Utility method for validating parameters and constructor arguments which
	 * must be greater than or equal to a set {@code lowerBound} value.
	 * 
	 * @throws IllegalArgumentException	  If the provided {@code val} is less
	 * 									  than {@code lowerBound}.
	 */
	public static void throwIfLessThan(String className, String argName, 
		int val, int lowerBound)
		throws IllegalArgumentException
	{
		if(val < lowerBound)
		{
			throw new IllegalArgumentException("In " + className + ": " +
				"The value of '" + argName + "' cannot be less than " + 
				lowerBound);
		}
	}
}
