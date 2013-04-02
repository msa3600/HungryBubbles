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
	private static int X_Move , Y_Move ; 
	private static int Value_Change = 5;
	
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

	public BubbleThread(BlockingQueue<UpdateRequest> updatesQueue, int color, float startX, 
		float startY, int radius)
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
		// need the max and min value for the screen height and width 
	/*
		for (int i = 0; i < GameBoard.getScreenWidth()*2; i++){
	//	X_Move = GameBoard.getScreenWidth()* (int)Math.random() + Value_Change ;
	//	} 
	//for ( int i = 0 ; i <GameBoard.getScreenHeight()*2; i++ ){
	//Y_Move = GameBoard.getScreenHeight() * (int)Math.random() + Value_Change;*/
		}
		
		//bubbleData = (color , X_Move , Y_Move, radius);
		
		// TODO Auto-generated method stub

		// Generate a new position for the bubble and then make the following call
		// where newPosition is the BubbleData object representing the bubble's
		// new position. Note that we are going to have to play around with how
		// far the bubble moves every time, so just pick some fixed value (defined
		// as a constant) and go with that for now (5 would be my best guess)
		
		// updatesQueue.put(newPosition);
	}


