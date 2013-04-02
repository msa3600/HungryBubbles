/**
 * 
 */
package com.hungry_bubbles;

/**
 * Collection of general utility methods. 
 * 
 * @author Timothy Heard
 */
public class GameUtils
{
	public static void throwIfNull(String argName, String className, Object obj)
		throws IllegalArgumentException
	{
		if(obj == null)
		{
			throw new IllegalArgumentException("In " + className + ": " +
				" '" + argName + "' cannot be null");
		}
	}
}
