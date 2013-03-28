package com.hungry_bubbles;

import android.graphics.Color;

/**
 * Drives the movement and actions of the computer-controlled bubbles.
 *  
 * @author Timothy Heard
 */
public class BubbleThread implements Runnable 
{
	private BubbleData bubbleData;
	
	/**
	 * Creates a new BubbleThread with the given starting data.
	 * 
	 * @param bubbleData
	 * @throws IllegalArgumentException
	 */
	public BubbleThread(AppManager appManager, Color pieceColor, float startX, 
		float startY, int radius)
		throws IllegalArgumentException
	{
		if(bubbleData == null)
		{
			String className = this.getClass().getName();
			throw new IllegalArgumentException("In " + className + ": " +
				" construction argument 'bubbleData' cannot be null");
		}
		
		this.bubbleData = bubbleData;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
