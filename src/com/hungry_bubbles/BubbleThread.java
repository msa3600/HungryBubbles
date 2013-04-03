package com.hungry_bubbles;

import java.util.concurrent.BlockingQueue;

/**
 * Drives the movement and actions of the computer-controlled bubbles.
 *  
 * @author Timothy Heard
 */
public class BubbleThread implements Runnable 
{
	// TODO: Clean up commmented out code
	
	private BubbleData bubbleData;
	private  int X_Move , Y_Move ; 
	private final static int Movement_Interval = 5;
	private final static int Sleep_Time = 5 ;
	boolean eaten; 
	
	/**
	 * Creates a new BubbleThread with the given starting data.
	 * 
	 * @param bubbleData
	 * @throws IllegalArgumentException
	 */
	/* TODO: Uncomment or remove
	public BubbleThread(GameBoard board, int color, float startX, 
		float startY, int radius)
		throws IllegalArgumentException
	{
		GameUtils.throwIfNull("board", "BubbleThread", board);
		this.bubbleData = new BubbleData(color, startX, startY, radius);
	}
	*/

	public BubbleThread(BlockingQueue<UpdateRequest> updatesQueue, int screenHeight , int screenWidth, int virtualPadding)
	
		throws IllegalArgumentException
	{
		GameUtils.throwIfNull("updatesQueue", "BubbleThread", updatesQueue);
		this.bubbleData = new BubbleData(color, startX, startY, radius);
	}

	/**
	 * TODO: Complete comment
	 */
	@Override
	public void run() {
		
	
		
		while (true){
		int bubbleRadius = bubbleData.getRadius();
		float minX = 0 + bubbleRadius;
		float minY = 0 + bubbleRadius;
		float maxX = screenWidth - bubbleRadius;
		float maxY = screenHeight - bubbleRadius;
		
		while (X_Move + bubbleData.getRadius() && Y_Move + bubbleData.getRadius() < screenWidth && screenHeight ){
		X_Move = Movement_Interval + (int)Math.random();
		Y_Move = Movement_Interval + (int)Math.random();
		}
		
		Thread.sleep(Sleep_Time);
		}
	//	bubbleData = (color , Y_Move , Y_Move, radius);
		}
	
		
		
		// TODO Auto-generated method stub

		// Generate a new position for the bubble and then make the following call
		// where newPosition is the BubbleData object representing the bubble's
		// new position. Note that we are going to have to play around with how
		// far the bubble moves every time, so just pick some fixed value (defined
		// as a constant) and go with that for now (5 would be my best guess)
		
		// updatesQueue.put(newPosition);
	
		
	
	/*
	 *  Check if bubbles that have the same coordinates,if true check for  
	 *  different radius and mark one with smaller radius as eaten  (kill bubble)
	 */
	public void wasEaten(){
		
		
		synchronized(this){

			eaten=true;
			
		}
	}
}


