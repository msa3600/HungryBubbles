package com.hungry_bubbles;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Interface for a class which is capable of drawing the current visual state
 * of the game on the provided {@link Canvas} object.
 * 
 * @author Timothy Heard
 */
public interface GameRenderer
{
	public void registerView(GameView view);
	public void renderGame(Canvas canvas);
	public boolean handlePlayerTouch(MotionEvent e);
}
