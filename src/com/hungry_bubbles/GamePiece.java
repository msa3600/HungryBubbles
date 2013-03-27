package com.hungry_bubbles;

import android.graphics.Color;

public class GamePiece
{
	private AppManager appManager;
	private Color pieceColor;
	private float x, y;
	private int level, mass, radius;

	public GamePiece(AppManager appManager, Color pieceColor, float x, float y, int mass, int radius, int level)
	{
		this.appManager = appManager;
		this.pieceColor = pieceColor;
		this.x = x;
		this.y = y;
		this.level = level;
		this.mass = mass;
		this.radius = radius;
	}

	public void consume(GamePiece piece)
	{
		mass += piece.getMass();
		
		if(mass >= appManager.minLevelMass(level + 1))
		{
			level++;
			radius = appManager.levelRadius(level);
			pieceColor = appManager.levelColor(level);
		}
		
		if(level == appManager.getWinLevel())
		{
			appManager.endGame(AppManager.GameState.WIN);
		}
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Color getColor() {
		return pieceColor;
	}

	public void setColor(Color color) {
		this.pieceColor = color;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getMass() {
		return mass;
	}

	public void setMass(int mass) {
		this.mass = mass;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
}
