/**
 * 
 */
package com.hungry_bubbles;

/**
 * Collection of general utility methods. 
 * 
 * @author Timothy Heard, Shaun DeVos, John O'Brien, Mustafa Al Salihi
 */
public class GameUtils
{
	public static void throwIfNull(String className, String argName, Object obj)
		throws IllegalArgumentException
	{
		if(obj == null)
		{
			throw new IllegalArgumentException("In " + className + ": " +
				"'" + argName + "' cannot be null");
		}
	}

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
}
