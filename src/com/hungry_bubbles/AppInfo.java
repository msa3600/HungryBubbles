package com.hungry_bubbles;

import android.app.Application;

public class AppInfo extends Application
{
	public static final int MAX_RADIUS = 100; 
	public static final int MAX_BUBBLES = 4;
	
	@Override
	public void onCreate()
	{
		super.onCreate();

		// TODO: Load past game data (if any) here 
	}
	
	@Override
	public void onTerminate()
	{
		super.onTerminate();
		
		// TODO: Make sure any persistent data gets stored
	}
}
