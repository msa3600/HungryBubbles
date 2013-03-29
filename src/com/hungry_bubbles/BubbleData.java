package com.hungry_bubbles;

/**
 * Thread-safe representation of a "bubble" in the game which uses immutable
 * state to allow for safe sharing of BubbleData object among different 
 * threads.
 * 
 * @author Timothy Heard
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

	public static BubbleData consume(BubbleData consumer, BubbleData victim)
	{
		// If the given bubble information indicates that the other bubble 
		// is larger than this one then the other piece will not be consumed.
		if(consumer == null || victim == null || consumer.getRadius() < victim.getRadius())
		{
			return null;
		}
		
		return new BubbleData(consumer.getColor(), consumer.getX(), 
			consumer.getY(), (consumer.getRadius()+ victim.getRadius()));
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
}
