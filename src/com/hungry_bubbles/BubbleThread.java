package com.hungry_bubbles;

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
	public BubbleThread(GameBoard board, int color, float startX, 
		float startY, int radius)
		throws IllegalArgumentException
	{
		GameUtils.throwIfNull("board", "GameBoard", board);
		this.bubbleData = new BubbleData(color, startX, startY, radius);
	}

	/**
	 * TODO: Complete comment
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
