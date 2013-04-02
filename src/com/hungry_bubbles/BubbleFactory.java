package com.hungry_bubbles;

import java.util.Random;

import android.graphics.Color;


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
	
	public BubbleFactory(NonBlockingReadQueue<UpdateRequest> updateRequests, int Screen_Height , int Screen_Width, int Virtual_padding)
	
	{
		//this.gameBoard = gameBoard;
		this.updatesQueue = updateRequests;
	}
	
	public void startNewBubble( )
	{
		 int Max = AppInfo.MAX_RADIUS;
		 int Min = AppInfo.MIN_RADIUS;
		 int Range_Size = Min + (int)(Math.random() * ((Max - Min) + 1));
		 //  Virtual board
		int Y_Position = (int)(Math.random() * (AppInfo.MAX_RADIUS)); 
		int X_Position = (int)(Math.random() * (AppInfo.MAX_RADIUS));
		
		 // update color
		 BubbleData bubble = new BubbleData(Color.BLACK, X_Position, Y_Position, Range_Size);
		 
		 if (Range_Size > AppInfo.PLAYER_STARTING_RADIUS){
				BubbleData.updateColor( bubble  , Color.RED);
			 	}
			 if (Range_Size < AppInfo.PLAYER_STARTING_RADIUS){
				 BubbleData.updateColor( bubble  , Color.BLUE);
				 }
		 
		 
		// TODO: Implement
	}
	
	@Override
    public void run()
    {
		
	    // TODO Auto-generated method stub
	    
    }
}
