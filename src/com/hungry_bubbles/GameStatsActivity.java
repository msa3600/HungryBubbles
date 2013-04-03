package com.hungry_bubbles;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GameStatsActivity extends Activity
{
	private AppInfo appInfo;
	private TextView totalWinsDisplay, totalGamesDisplay;
	private Button returnButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.stats_screen);
        
        // Gets a reference to the Application object which represents this
        // Android application (since the android:name property in the 
        // application tag within the AndroidManifest file is set to .AppInfo
        // the custom Application subclass AppInfo will be used by Android
        // for this purpose
        appInfo = (AppInfo) getApplication();
        
        totalWinsDisplay = (TextView) findViewById(R.id.total_wins_display);
        totalGamesDisplay = (TextView) findViewById(R.id.total_games_display);
        returnButton = (Button) findViewById(R.id.return_button);
        
        returnButton.setOnClickListener(new OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
				// Transition back to the start screen
        		finish();
        	}
        });
    }
    
    @Override
    public void onStart()
    {
    	super.onStart();
    	
    	totalWinsDisplay.setText(Integer.toString(appInfo.getTotalWins()));
    	totalGamesDisplay.setText(Integer.toString(appInfo.getTotalGames()));
    }
}
