package com.hungry_bubbles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends Activity 
{
	private AppInfo appInfo;
	private Button startGameButton, viewStatsButton, quitButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.start_screen);
        
        // Gets a reference to the Application object which represents this
        // Android application (since the android:name property in the 
        // application tag within the AndroidManifest file is set to .AppInfo
        // the custom Application subclass AppInfo will be used by Android
        // for this purpose
        appInfo = (AppInfo) getApplication();
        
        startGameButton = (Button) findViewById(R.id.start_game_button);
        viewStatsButton = (Button) findViewById(R.id.game_stats_button);
        quitButton = (Button) findViewById(R.id.quit_button);
        
        startGameButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent startGameIntent = new Intent(StartActivity.this, HungryBubblesActivity.class);
				startActivity(startGameIntent);
			}
		});
        
        viewStatsButton.setOnClickListener(new OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		// TODO: Navigate to the game stats screen (this requires that 
        		// a game stats screen (xml layout file) and corresponding 
        		// Activity to be created first 
        	}
        });
        
        quitButton.setOnClickListener(new OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		// First ensure that all persistent game data gets stored
        		// properly
        		
        		// TODO: Make the appropriate call on AppInfo to tell it to 
        		// write all of its data to persistent storage
        		
        		// Calls the finish method on StartActivity, which is a method
        		// inherited from the Activity class which declares an activity
        		// to be finished and causes it to be shutdown by Android
        		finish();
        	}
        });
    }
}
