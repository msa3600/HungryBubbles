package com.hungry_bubbles;


/**
 * Manages the periodic creation of new BubbleThreads.
 */
public class BubbleFactory implements Runnable
{
	// TODO: Clean up
	//private GameBoard gameBoard;
	NonBlockingReadQueue<UpdateRequest> updatesQueue;
	
	/*
	public BubbleFactory(GameBoard gameBoard) 
	{
		this.gameBoard = gameBoard;
	}
	*/
	
	public BubbleFactory(NonBlockingReadQueue<UpdateRequest> updateRequests) 
	{
		//this.gameBoard = gameBoard;
		this.updatesQueue = updateRequests;
	}

	public void startNewBubble()
	{
		// TODO: Implement
	}

	@Override
    public void run()
    {
	    // TODO Auto-generated method stub
	    
    }
}
