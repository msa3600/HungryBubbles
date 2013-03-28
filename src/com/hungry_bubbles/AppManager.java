package com.hungry_bubbles;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.graphics.Color;

public class AppManager extends Application
{
	public enum GameState {WIN, LOSE};
	
	private Map<Integer, Color> levelColors;
	private Map<Integer, Integer> levelMasses;
	private Map<Integer, Integer> levelRadii;
	private BubbleData startingPlayerPiece;
	private int winLevel, numLevels, playerTouchRadius, playerStartingLevel;	
	
	@Override
	public void onCreate()
	{
		super.onCreate();

		levelColors = new HashMap<Integer, Color>();
		levelMasses = new HashMap<Integer, Integer>();
		levelRadii = new HashMap<Integer, Integer>();
		
		// TODO: Load application data from values.xml and colors.xml
		
		// TODO: Use setters instead of constructor
		// TODO: startingPlayerPiece = new GamePiece(this, levelColors.get(playerStartingLevel), 
		// centerX, centerY, levelMasses.get(playerStartingLevel), levelRadii.get(playerStartingLevel),
		// playerStartingLevel);
	}
	
	@Override
	public void onTerminate()
	{
		super.onTerminate();
		// TODO: Make sure any persistent data gets stored
	}
	
	public Color levelColor(int level)
		throws IllegalArgumentException
	{
		return levelColors.get(level);
	}
	
	public int minLevelMass(int level)
		throws IllegalArgumentException
	{
		return levelMasses.get(level);
	}
	
	public int levelRadius(int level)
		throws IllegalArgumentException
	{
		return levelRadii.get(level);
	}

	public int getWinLevel() {
		return winLevel;
	}

	public int getNumLevels()
	{
		return numLevels;
	}

	public int getPlayerTouchRadius()
	{
		return playerTouchRadius;
	}

	public int getPlayerStartingLevel()
	{
		return playerStartingLevel;
	}
	
	public BubbleData getStartingPlayer()
	{
		return startingPlayerPiece;
	}	
	
	public void endGame(GameState state) {
		// TODO Auto-generated method stub
		
	}		
	
	public static boolean intersecting(BubbleData piece1, BubbleData piece2)
	{
		// TODO: return piece1.contains(piece2);
		return false;
	}

	public int getWinRadius() {
		// TODO Auto-generated method stub
		return 0;
	}
}
