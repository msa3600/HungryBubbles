package com.hungry_bubbles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Manages the game logic.
 */
public class GameBoard implements GameRenderer
{
	// The maximum number of update requests which can be pending at one time
	private static final int REQUEST_QUEUE_CAPACITY = 30;

	// The maximum amount of time that the UI thread will wait
	// when attempting to get exclusive access to the current
	// update requests (in milliseconds)
	private static final long MAX_UPDATE_WAIT_TIME = 500;

	// Tag which will appear as a part of any log messages generated within 
	// this class
	private static final String TAG = "GameBoard";

	private static final String WIN_MESSAGE = "Congratulations! You Won";
	private static final String LOSE_MESSAGE = "You were eaten!";
	
	private HungryBubblesActivity hostActivity;
	private GameView view;
	private BubbleData playerData;
	private List<BubbleData> opponents;
	private Map<BubbleThread, BubbleData> opponentData;
	private BubbleFactory bubbleFactory;
	
	// TODO: Uncomment or remove
	//private BlockingQueue<UpdateRequest> updateRequests;
	private NonBlockingReadQueue<UpdateRequest> updateRequests;
	
	private int screenWidth, screenHeight, boardWidth, boardHeight;
	private boolean initialized;

	private boolean playerAlive;
	
	private GameBoard(HungryBubblesActivity hostActivity)
		throws IllegalArgumentException
	{
		GameUtils.throwIfNull("hostActivity", "GameBoard", hostActivity);
		
		this.hostActivity = hostActivity;
		
		// A GameView must be registered with this GameBoard in order to 
		// assign a value to this field
		view = null;
		
		// Player is considered to be alive until it is eaten by another bubble
		// even though the player's game piece has not been initialized yet;
		// initialization is deferred until the first UI update because the
		// player's starting position is dependent on the size of the physical
		// screen which will not be know until the UI is ready to be drawn.
		playerAlive = true;		
		playerData = null;
		
		bubbleFactory = null;
		opponents = new ArrayList<BubbleData>();
		opponentData = new HashMap<BubbleThread, BubbleData>();
		
		// TODO: Remove this if NonBlockingReadQueue works
		//
		// Create a blocking queue which can hold up to 
		// REQUEST_QUEUE_CAPACITY update requests and enforces fairness (i.e. 
		// all threads which are attempting to add requests to the queue will
		// be guaranteed the opportunity to do so at some point)
		//updateRequests = new ArrayBlockingQueue<UpdateRequest>(
			//REQUEST_QUEUE_CAPACITY, true);
		
		updateRequests = new NonBlockingReadQueue<UpdateRequest>();
		
		initialized = false;
	}

	@Override
    public void renderGame(Canvas canvas)
    {
		if(!initialized)
		{
			// Stores the size of the physical screen. This value only needs
			// to be retrieved once since this activity is locked in horizontal
			// orientation
			screenWidth = canvas.getWidth();
			screenHeight = canvas.getHeight();
			
			// The actual, effective size of the game board is 2 * MAX_RADIUS
			// units larger than the physical screen in both directions. This
			// additional area is used as a spawning area for new 
			// computer-controlled bubbles so that bubbles will not spawn on 
			// top of the player.
			boardWidth = screenWidth + (2 * AppInfo.MAX_RADIUS); 
			boardHeight = screenHeight + (2 * AppInfo.MAX_RADIUS);
			
			// Start the player centered in the screen
			playerData = new BubbleData(Color.BLACK, screenWidth / 2, 
				screenHeight / 2, AppInfo.PLAYER_STARTING_RADIUS);
			initialized = true;
			playerAlive = true;
		}

		applyUpdates();
		resolveConflicts();
		
		if(!playerAlive)
		{
			endGame();
		}
		else
		{
			drawPlayer(canvas);
			drawOppponents(canvas);
		}
    }

	/**
	 * Ends the current game.
	 * 
	 * @throws IllegalStateException	If no {@link GameView} has been 
	 * 									registered with this {@link GameBoard}
	 * 									yet
	 */
	private void endGame()
		throws IllegalStateException
    {
		if(view == null)
		{
			throw new IllegalStateException("In GameBoard: a GameView must " +
				"be registered before endGame() can be called");
		}
		
		String message;
		
		if(playerAlive)
		{
			message = WIN_MESSAGE;
		}
		else
		{
			message = LOSE_MESSAGE;
		}
		
	    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());
	    dialogBuilder.setMessage(message);
	    dialogBuilder.setPositiveButton("Play Again", 
	    	new OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					// Restart game
					hostActivity.startGame();
				}
			});
	    
	    dialogBuilder.setNegativeButton("Quit", 
	    	new OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					hostActivity.quit();
				}
			});
	    
	    dialogBuilder.create().show();
    }

	@Override
    public void registerView(GameView view)
    {
		this.view = view;   
    }

	/**
	 * Handles a the {@link MotionEvent} generated as a result of a player 
	 * touch on the screen.
	 * 
	 * @throws IllegalStateException	If no {@link GameView} has been 
	 * 									registered with this {@link GameBoard}
	 * 									yet
	 */
	@Override
    public boolean handlePlayerTouch(MotionEvent e)
		throws IllegalStateException
    {
		if(view == null)
		{
			throw new IllegalStateException("In GameBoard: a GameView must " +
				"be registered before endGame() can be called");
		}
		
		if(!isValidPlayerTouch(e))
		{
			return false;
		}

		playerData = BubbleData.updatePosition(playerData, e.getX(), e.getY());

		// Causes the game view to be redrawn
		view.invalidate();
		
		return true;
    }
	
	public int getScreenWidth()
	{
		return screenWidth;
	}
	
	public int getScreenHeight()
	{
		return screenHeight;
	}
	
	public int getBoardWidth()
	{
		return boardWidth;
	}
	
	public int getBoardHeight()
	{
		return boardHeight;
	}

	private void setFactory(BubbleFactory bubbleFactory)
	{
		this.bubbleFactory = bubbleFactory;
	}

	/**
	 * Goes through all of the "bubbles" which are currently active in the game
	 * space and handles any collisions, with the larger bubble involved in the 
	 * collision consuming the smaller one, with consumed bubbles mass behind 
	 * added to the mass of the consumer (up to a the maximum bubble size). The
	 * player wins ties and ties between two computer-driven bubbles is 
	 * non-deterministic (i.e. one of the bubbles will eat the other, but there
	 * is no guarantee which one it will be). 
	 */
	private void resolveConflicts()
    {
		List<BubbleThread> deadThreads = new ArrayList<BubbleThread>();
		
		for(BubbleThread bubbleThread: opponentData.keySet())
		{
			BubbleData opponentPosition = opponentData.get(bubbleThread);
			
			if(BubbleData.bubblesAreTouching(playerData, opponentPosition))
			{
				// Note that the player wins in the case of ties
				if(playerData.getRadius() >= opponentPosition.getRadius())
				{
					playerData = BubbleData.consume(playerData, 
						opponentPosition);
					deadThreads.add(bubbleThread);
				}
				else
				{
					opponentData.put(bubbleThread, 
						BubbleData.consume(opponentPosition, playerData));
					
					playerAlive = false;
					break;
				}
			}
		}
		
		for(BubbleThread bubbleThread: deadThreads)
		{
			// TODO: Figure out how to stop the BubbleThreads that have been "eaten"
			opponentData.remove(bubbleThread);
		}
		
		// Check to see if any of the computer-controlled bubbles have
		// collided with each other
		BubbleThread[] bubbleThreads = (BubbleThread[]) opponentData.keySet().toArray(); 
		List<Integer> deadThreadIndices = new ArrayList<Integer>();
		for(int i = 0; i < bubbleThreads.length; i++)
		{
			if(deadThreadIndices.contains(i))
			{
				continue;
			}
			
			for(int j = i + 1; j < bubbleThreads.length; j++)
			{
				if(deadThreadIndices.contains(j))
				{
					continue;
				}
				
				BubbleData bubble1 = opponentData.get(bubbleThreads[i]); 
				BubbleData bubble2 = opponentData.get(bubbleThreads[j]); 
				if(BubbleData.bubblesAreTouching(bubble1, bubble2))
				{
					// Note that the player wins in the case of ties
					if(bubble1.getRadius() >= bubble2.getRadius())
					{
						opponentData.put(bubbleThreads[i], 
							BubbleData.consume(bubble1, bubble2));
						
						deadThreadIndices.add(j);
					}
					else
					{
						opponentData.put(bubbleThreads[j], 
							BubbleData.consume(bubble2, bubble1));
						
						deadThreadIndices.add(i);
					}
				}
				
			}
		}
		
		for(Integer index: deadThreadIndices)
		{
			// TODO: Figure out how to stop the BubbleThreads that have been "eaten"
			opponentData.remove(bubbleThreads[index]);
		}
    }

	/**
	 * Applies any pending updates to bubble positions, ignoring any positions 
	 * which are currently locked. 
	 */
	private void applyUpdates()
    {
		try
        {
	        List<UpdateRequest> updatesToApply = updateRequests.nonblockingPop(MAX_UPDATE_WAIT_TIME);
	        for(UpdateRequest request: updatesToApply)
	        {
	        	opponentData.put(request.getRequester(), request.getNewPosition());
	        }
	        
        } 
		catch (InterruptedException e)
        {
        	Log.d(TAG, "InterruptedException was thrown while attempting to " +
        		"apply pending UI updates. ");
        	e.printStackTrace();
        }
    }
	
	/**
	 * Used to determine whether or not the point with the given x and y
	 * coordinates is located on the physical screen.
	 */
	private boolean outOfBounds(float x, float y, float radius)
	{
		if((x + radius < 0) || (x - radius < 0) || (x + radius > screenWidth) || (x - radius > screenWidth) ||
		   (y + radius < 0) || (y - radius < 0) || (y + radius > screenHeight) || (y - radius > screenHeight))
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Determines whether or not the {@link MotionEvent} which resulted from a
	 * user touch action is valid (i.e. is contained within the player's 
	 * "bubble").
	 * 
	 * @param 	e	The {@link MotionEvent} caused by the player touching the 
	 * 				screen.
	 * 
	 * @return	{@code true} if the player touched within their "bubble" and 
	 * {@code false} otherwise.	
	 */
	private boolean isValidPlayerTouch(MotionEvent e)
	{
		float eventX = e.getX();
		float eventY = e.getY();
		int playerRadius = playerData.getRadius();
		
		if((Math.abs(playerData.getX() - eventX) <= playerRadius) && 
		   (Math.abs(playerData.getY() - eventY) <= playerRadius) &&
		   !outOfBounds(eventX, eventY, playerRadius))
		{
			return true;
		}
		
		return false;
	}
	
	private void drawBubble(Canvas canvas, BubbleData data)
	{
		Paint circlePaint = new Paint();
		circlePaint.setColor(data.getColor());
		canvas.drawCircle(data.getX(), data.getY(), data.getRadius(), circlePaint);
	}
	
	private void drawPlayer(Canvas canvas)
	{
		drawBubble(canvas, playerData);
		
		/* TODO: Remove
		Paint circlePaint = new Paint();
		circlePaint.setColor(playerData.getColor());
		canvas.drawCircle(playerData.getX(), playerData.getY(), playerData.getRadius(), circlePaint);
		*/
	}

	private void drawOppponents(Canvas canvas)
    {
		for(BubbleData bubbleData: opponentData.values())
		{
			drawBubble(canvas, bubbleData);
		}
    }
	
	public static GameBoard makeGameBoard(HungryBubblesActivity hostActivity)
	{
		// TODO: Resolve the double-reference issue by sharing a Java-synchronized queue of UpdateRequests
		// => Make sure that this is actually working
		GameBoard board = new GameBoard(hostActivity);
		board.setFactory(new BubbleFactory(board.updateRequests));
		//board.setFactory(new BubbleFactory(board));
		return board;
	}
}
