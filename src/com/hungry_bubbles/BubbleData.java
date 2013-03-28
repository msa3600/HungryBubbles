package com.hungry_bubbles;

import android.graphics.Color;

/**
 * Thread-safe representation of a "bubble" in the game which uses immutable
 * state to allow for safe sharing of BubbleData object among different 
 * threads.
 * 
 * @author Timothy Heard
 */
public class BubbleData
{
	private final Color color;
	private final float x, y;
	private final int radius;

	public BubbleData(Color color, float x, float y, int radius)
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

	public Color getColor()
	{
		return color;
	}
	
	public int getRadius()
	{
		return radius;
	}

	public static BubbleData consume(BubbleData consumer, BubbleData victim)
	{
		// If the given GamePiece information indicates that the other bubble 
		// is larger than this one then the other piece will not be consumed.
		if(consumer == null || victim == null || consumer.getRadius() < victim.getRadius())
		{
			return null;
		}
		
		return new BubbleData(consumer.getColor(), consumer.getX(), 
			consumer.getY(), (consumer.getRadius()+ victim.getRadius()));
		
		/* TODO: move to the caller
		if(radius >= appManager.getWinRadius())
		{
			appManager.endGame(AppManager.GameState.WIN);
		}
		*/
	}

	public static BubbleData updateColor(BubbleData bubble, Color newColor)
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
