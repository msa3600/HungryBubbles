package com.hungry_bubbles;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Manages the game logic.
 */
public class GameBoard implements GameRenderer
{
	private GameView view;
	private BubbleData player;
	private List<BubbleData> opponents;
	
	// TODO: Define this value as a public constant in AppInfo instead
	private static final int PLAYER_RADIUS = 50;
	
	// TODO: Use BubbleData fields instead
	private int playerRadius;
	private boolean initialized = false;
	private int width, height;
	private float playerX, playerY;
	
	public GameBoard()
	{
		// TODO: Add safety checks to ensure nothing happens until a view is registered
		this.view = null;
		player = null;
		opponents = new ArrayList<BubbleData>();
	}

	@Override
    public void renderGame(Canvas canvas)
    {
	    // TODO Auto-generated method stub

		
		if(!initialized)
		{
			// TODO: Initialize playerRadius properly
			playerRadius = PLAYER_RADIUS;
			
			width = canvas.getWidth();
			height = canvas.getHeight();
			playerX = width / 2;
			playerY = height / 2;
			
			player = new BubbleData(Color.BLACK, playerX, playerY, playerRadius);
			drawPlayer(canvas);
			initialized = true;
		}
		
		drawPlayer(canvas);
    }
	
	private boolean outOfBounds(float x, float y, float radius)
	{
		if((x + radius < 0) || (x - radius < 0) || (x + radius > width) || (x - radius > width) ||
		   (y + radius < 0) || (y - radius < 0) || (y + radius > height) || (y - radius > height))
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
	@Override
	public boolean isValidPlayerTouch(MotionEvent e)
	{
		float eventX = e.getX();
		float eventY = e.getY();
		
		if((Math.abs(playerX - eventX) <= PLAYER_RADIUS) && (Math.abs(playerY - eventY) <= PLAYER_RADIUS) &&
		   !outOfBounds(eventX, eventY, playerRadius))
		{
			return true;
		}
		
		return false;
	}
	
	private void drawPlayer(Canvas c)
	{
		Paint circlePaint = new Paint();
		circlePaint.setColor(Color.BLACK);
		c.drawCircle(playerX, playerY, PLAYER_RADIUS, circlePaint);
	}

	@Override
    public void registerView(GameView view)
    {
		this.view = view;   
    }
}
