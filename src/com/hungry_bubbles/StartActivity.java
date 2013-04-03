package com.hungry_bubbles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * The {@link Activity} subclass which represents the start screen for the 
 * application.
 * 
 * @author Timothy Heard, Shaun DeVos, John O'Brien, Mustafa Al Salihi
 */
public class StartActivity extends Activity 
{
	protected static final int STATS_VIEW_REQUEST_CODE = 0;
	private Button startGameButton, viewStatsButton, quitButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.start_screen);
        
        startGameButton = (Button) findViewById(R.id.start_game_button);
        viewStatsButton = (Button) findViewById(R.id.game_stats_button);
        quitButton = (Button) findViewById(R.id.quit_button);
        
        startGameButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// Transition to the game screen by starting the HungryBubblesActivity
				Intent startGameIntent = new Intent(StartActivity.this, 
					HungryBubblesActivity.class);
				
				startActivity(startGameIntent);
			}
		});
        
        viewStatsButton.setOnClickListener(new OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
				// Transition to the game stats screen by starting the GameStatsActivity
        		Intent viewStatsIntent = new Intent(StartActivity.this,
        			GameStatsActivity.class);
        		
        		// startActivityForResult() is used instead of startActivity()
        		// to allow the GameStatsActivity to navigate back to this 
        		// activity by calling the finish() method (inherited from 
        		// android.app.Activity) when the user is done viewing the
        		// game statistics
				startActivityForResult(viewStatsIntent, 
					STATS_VIEW_REQUEST_CODE);
        	}
        });
        
        quitButton.setOnClickListener(new OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		// Calls the finish method on StartActivity, which is a method
        		// inherited from the Activity class which declares an activity
        		// to be finished and causes it to be shutdown by Android
        		finish();
        	}
        });
    }
}
