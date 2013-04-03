package com.hungry_bubbles;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * The {@link Activity} subclass which represents the game screen.
 * 
 * @author Timothy Heard, Shaun DeVos, John O'Brien, Mustafa Al Salihi
 */
public class HungryBubblesActivity extends Activity
{
	private GameBoard gameBoard;

	/**
	 * Android application lifecycle method which is called when the activity 
	 * is first created.
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game_screen);
        
        startGame();
    }
	
	/**
	 * Android application lifecycle method which is called whenever the 
	 * activity is restarted after being stopped (i.e. the app is no longer
	 * visible to the user).
	 */
	@Override
	public void onRestart()
	{
		super.onRestart();
		gameBoard.resume();
	}
	
	/**
	 * Android application lifecycle method which is called whenever the 
	 * activity is paused (app is inactive but still visible).
	 */
	@Override
	public void onPause()
	{
		super.onPause();
		gameBoard.suspend();
	}

	/**
	 * Android application lifecycle method which is called whenever the 
	 * activity is destroyed (the app is completely shut down and all of
	 * its resources are released).
	 */
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		gameBoard.stop();
	}
	
	/**
	 * Starts a new game.
	 */
	public void startGame()
	{
        gameBoard = new GameBoard(this);
        
        LinearLayout layout = (LinearLayout) findViewById(R.id.main_layout);  
        layout.removeAllViews();
        layout.addView(gameBoard);
	}
	
	/**
	 * Stop playing the game and transition back to the start screen.
	 */
	public void quit()
	{
		finish();
	}
}