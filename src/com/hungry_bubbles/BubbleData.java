package com.hungry_bubbles;

/**
 * Thread-safe representation of a "bubble" in the game which uses immutable
 * state to allow for safe sharing of BubbleData object among different 
 * threads.
 * 
 * @author Timothy Heard, Shaun DeVos, John O'Brien, Mustafa Al Salihi
 */
public class BubbleData
{
	// "tag" used for tagging log information and exception messages which
	// originate from this class
	private static final String TAG = "BubbleData";
	
	private final int color;
	private final float x, y;
	private final int radius;
	private final int angleOfMotion;

	public BubbleData(int color, float x, float y, int radius, int angleOfMotion)
		throws IllegalArgumentException
	{
		GameUtils.throwIfInvalidAngle(TAG, "angleOfMotion", angleOfMotion);
		
		this.color = color;
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.angleOfMotion = angleOfMotion;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY() 
	{
		return y;
	}

	public int getColor()
	{
		return color;
	}
	
	public int getRadius()
	{
		return radius;
	}
	
	public int getAngleOfMotion()
	{
		return angleOfMotion;
	}

	public static boolean bubblesAreTouching(BubbleData bubble1, 
		BubbleData bubble2)
	{
		double dx = Math.pow((bubble1.getX() - bubble2.getX()), 2);
		double dy = Math.pow((bubble1.getY() - bubble2.getY()), 2);
		double dist = Math.sqrt(dx + dy);
		
		// If the distance between the centers of the two bubbles is less than
		// or equal to the sum of the radii of the two bubbles then the bubbles
		// are touching
		return (dist <= (bubble1.getRadius() + bubble2.getRadius()));
	}
	
	/**
	 * Add the two bubbles represented by the given {@code BubbleData} objects
	 * and return the resulting radius. 
	 */
	public static int addAreas(BubbleData bubble1, BubbleData bubble2)
	{
		double area1 = Math.PI * Math.pow(bubble1.getRadius(), 2);
		double area2 = Math.PI * Math.pow(bubble2.getRadius(), 2);
		double combinedArea = area1 + area2;
		
		return (int) Math.sqrt(combinedArea / Math.PI);
	}
	
	/**
	 * Returns a new {@code BubbleData} object which has the area of the 
	 * {@code victim} bubble added to the area of the {@code consumer}
	 * bubble, with all of the other bubble properties (x/y coordinates,
	 * color, and angle of motion) will be the same as the {@code consumer}'s
	 * current values for those properties.
	 */
	public static BubbleData consume(BubbleData consumer, BubbleData victim)
	{
		// If the given bubble information indicates that the other bubble 
		// is larger than this one then the other piece will not be consumed.
		if(consumer == null || victim == null || 
		   consumer.getRadius() < victim.getRadius())
		{
			return null;
		}
		
		return updateRadius(consumer, addAreas(consumer, victim));
	}

	public static BubbleData updateColor(BubbleData bubble, int newColor)
	{
		return new BubbleData(newColor, bubble.getX(), bubble.getY(), 
			bubble.getRadius(), bubble.getAngleOfMotion());
	}
	
	public static BubbleData updateX(BubbleData bubble, float x)
	{
		return new BubbleData(bubble.getColor(), x, bubble.getY(), 
				bubble.getRadius(), bubble.getAngleOfMotion());
	}

	public static BubbleData updateY(BubbleData bubble, float y)
	{
		return new BubbleData(bubble.getColor(), y, bubble.getY(), 
			bubble.getRadius(), bubble.getAngleOfMotion());
	}

	public static BubbleData updateRadius(BubbleData bubble, int radius) 
	{
		return new BubbleData(bubble.getColor(), bubble.getX(), bubble.getY(), 
			radius, bubble.getAngleOfMotion());
	}

	public static BubbleData updatePosition(BubbleData bubble, float x,
            float y)
    {
		return new BubbleData(bubble.getColor(), x, y, bubble.getRadius(),
			bubble.getAngleOfMotion());
    }
	
	public static BubbleData updateAngleOfMotion(BubbleData bubble, int angle)
		throws IllegalArgumentException
	{
		GameUtils.throwIfInvalidAngle(TAG, "angle", angle);
		
		return new BubbleData(bubble.getColor(), bubble.getX(), bubble.getY(), 
			bubble.getRadius(), angle);
	}

	public static BubbleData move(BubbleData bubble, int distance)
	{
		// Opposite over hypotenuse 
		double sine = Math.sin(Math.toRadians(bubble.getAngleOfMotion()));
		
		// Adjacent over hypotenuse 
		double cosine = Math.cos(Math.toRadians(bubble.getAngleOfMotion()));
		
		// Note that the hypotenuse is equal to the distance parameter
		float changeInX = (float) (cosine * distance);
		float changeInY = (float) (sine * distance);
		
		return updatePosition(bubble, bubble.getX() + changeInX, bubble.getY() + changeInY);
	}
}
