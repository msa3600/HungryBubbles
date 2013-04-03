package com.hungry_bubbles;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Drives the movement and actions of the computer-controlled bubbles.
 *  
 * @author Timothy Heard, Shaun DeVos, John O'Brien, Mustafa Al Salihi
 */
public class BubbleThread extends Thread
{
	// How far the bubble will be moved at a time
	private static final int MOVE_INCREMENT = 5;
	
	// How long the thread should sleep for between bubble position updates
	// in milliseconds
	private static final int SLEEP_TIME = 50;
	
	// "Tag" used for logging information and events within this class, such as
	// the absence if invalid constructor arguments are provided 
	private static final String TAG = "updatesQueue";
	
	// TODO: Update comment
	// Shared data structure used to submit position update requests which will
	// then be read by the GameBoard and rendered
	private Handler messageHandler;
	
	private BubbleData bubbleData;
	private int screenWidth, screenHeight;
	
	// TODO: Add comment
	private int virtualPadding;
	
	// Keeps track of whether or not this bubble has been "eaten" by another
	// bubble
	private boolean eaten;

	public BubbleThread(Handler messageHandler, 
			BubbleData startingData, int screenWidth, 
			int screenHeight, int virtualPadding)
		throws IllegalArgumentException
	{
		//GameUtils.throwIfNull(TAG, "updatesQueue", updatesQueue);
		GameUtils.throwIfNull(TAG, "messageHandler", messageHandler);
		GameUtils.throwIfNull(TAG, "startingData", startingData);
		
		//this.updatesQueue = updatesQueue;
		this.messageHandler = messageHandler;
		this.bubbleData = startingData;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.virtualPadding = virtualPadding;
		this.eaten = false;
	}

	/**
	 * Continuously comes up with a new position for the bubble controlled 
	 * by this {@code BubbleThread} instance and submits it to the 
	 * {@code updatesQueue} before sleeping for SLEEP_TIME milliseconds. 
	 */
	@Override
	public void run()
	{
		// TODO: Implement changes to bubble's angle of motion
		
		while(true)
		{
			synchronized(this)
			{
				if(eaten)
				{
					return;
				}
			}
			
			// Move the bubble
			BubbleData newPosition = BubbleData.move(bubbleData, MOVE_INCREMENT);
			
			if(!canMoveTo(newPosition))
			{
				// Reverse angle of motion
				int angleOfMotion = bubbleData.getAngleOfMotion();
				angleOfMotion = angleOfMotion >= 180 ? angleOfMotion - 180 : 
					angleOfMotion + 180;
				
				newPosition = BubbleData.move(
					BubbleData.updateAngleOfMotion(bubbleData, angleOfMotion), 
					MOVE_INCREMENT);
			}
			
			this.bubbleData = newPosition;
			
			// TODO
			//updatesQueue.put(new UpdateRequest(this, newPosition));
			
			Message updateMessage = new Message();
			updateMessage.obj = new UpdateRequest(this, newPosition);
			messageHandler.sendMessage(updateMessage);
			
			try
			{
				sleep(SLEEP_TIME);
			} catch (InterruptedException e)
			{
				Log.e(TAG, "InterruptedException occurred in BubbleThread.run()");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Used to determine whether or not the position represented by the given
	 * {@link BubbleData} object is valid. If the position is valid then this
	 * function will return {@code true}; otherwise it will return 
	 * {@code false}.
	 */
	private boolean canMoveTo(BubbleData position)
	{
		float x = position.getX();
		float y = position.getY();
		int radius = position.getRadius();
		
		return !((x - radius < 0) || (x + radius > screenWidth + (2 * virtualPadding)) ||
				(y - radius < 0) || (y + radius) > screenHeight + (2 * virtualPadding));
	}

	public void wasEaten()
	{
		synchronized(this)
		{
			eaten = true;
		}
	}
	
	public void stopThread()
	{
		this.wasEaten();
	}
}


