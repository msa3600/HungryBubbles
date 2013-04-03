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
public class HungryBubblesActivity extends Activity {
	private GameBoard gameBoard;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game_screen);
        
        startGame();
    }
	
	public void startGame()
	{
        gameBoard = new GameBoard(this);
        
        LinearLayout layout = (LinearLayout) findViewById(R.id.main_layout);  
        layout.removeAllViews();
        layout.addView(gameBoard);
	}
	
	public void quit()
	{
		finish();
	}
}