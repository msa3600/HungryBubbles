package com.hungry_bubbles;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View 
{
	private BubbleData player;
	private List<BubbleData> opponents;
	
	// TODO: Use GamePiece fields instead
	private static final int PLAYER_RADIUS = 50;
	private int playerRadius;
	private boolean initialized = false;
	private int width, height;
	private float playerX, playerY;
	
	public GameView(Context context, BubbleData playerPiece) {
		super(context);
		
		player = playerPiece;
		opponents = new ArrayList<BubbleData>();
	}

	@Override
	protected void onDraw(Canvas c)
	{
		super.onDraw(c);
		
		if(!initialized)
		{
			// TODO: Initialize playerRadius properly
			playerRadius = PLAYER_RADIUS;
			
			width = c.getWidth();
			height = c.getHeight();
			playerX = width / 2;
			playerY = height / 2;		
			drawPlayer(c);
			initialized = true;
		}
		
		drawPlayer(c);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		if(validPlayerTouch(e))
		{
			// TODO: Check for collisions with opponents and perform necessary actions 
			playerX = e.getX();
			playerY = e.getY();
			this.invalidate();
		}
		
		return true;
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
	private boolean validPlayerTouch(MotionEvent e)
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
}
