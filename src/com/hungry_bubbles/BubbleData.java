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
	private final int color;
	private final float x, y;
	private final int radius;

	public BubbleData(int color, float x, float y, int radius)
	{
		this.color = color;
		this.x = x;
		this.y = y;
		this.radius = radius;
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

	public static boolean bubblesAreTouching(BubbleData bubble1, BubbleData bubble2)
	{
	    double a = bubble1.getRadius() + bubble2.getRadius();
	    double dx = bubble1.getX() + bubble2.getX();
	    double dy = bubble1.getY() + bubble2.getY();

	    return a * a > (dx * dx + dy * dy);
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
	
	public static BubbleData consume(BubbleData consumer, BubbleData victim)
	{
		// If the given bubble information indicates that the other bubble 
		// is larger than this one then the other piece will not be consumed.
		if(consumer == null || victim == null || consumer.getRadius() < victim.getRadius())
		{
			return null;
		}
		
		return updateRadius(consumer, addAreas(consumer, victim));
	}

	public static BubbleData updateColor(BubbleData bubble, int newColor)
	{
		return new BubbleData(newColor, bubble.getX(), bubble.getY(), 
			bubble.getRadius());
	}
	
	public static BubbleData updateX(BubbleData bubble, float x)
	{
		return new BubbleData(bubble.getColor(), x, bubble.getY(), 
				bubble.getRadius());
	}

	public static BubbleData updateY(BubbleData bubble, float y)
	{
		return new BubbleData(bubble.getColor(), y, bubble.getY(), 
			bubble.getRadius());
	}

	public static BubbleData updateRadius(BubbleData bubble, int radius) 
	{
		return new BubbleData(bubble.getColor(), bubble.getX(), bubble.getY(), 
			radius);
	}

	public static BubbleData updatePosition(BubbleData bubble, float x,
            float y)
    {
		return new BubbleData(bubble.getColor(), x, y, bubble.getRadius());
    }

	public static BubbleData move(BubbleData bubble, int angleOfMotion, int distance)
	{
		// Opposite over hypotenuse 
		double sine = Math.sin(Math.toRadians(angleOfMotion));
		
		// Adjacent over hypotenuse 
		double cosine = Math.cos(Math.toRadians(angleOfMotion));
		
		// Note that the hypotenuse is equal to the distance parameter
		int changeInX = (int) (cosine * distance);
		int changeInY = (int) (sine * distance);
		
		return updatePosition(bubble, bubble.getX() + changeInX, bubble.getY() + changeInY);
	}
}
