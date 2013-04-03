package com.hungry_bubbles;

import java.util.Random;

import android.os.Handler;

/**
 * Factory class for the creation of new BubbleThreads with random bubble
 * starting positions and size around the outside of the screen in the 
 * virtual padding area.
 * 
 * @author Timothy Heard, Shaun DeVos, John O'Brien, Mustafa Al Salihi
 */
public class BubbleFactory
{
	private static final String TAG = "BubbleFactory";
	
	private enum SIDE {LEFT, RIGHT, TOP, BOTTOM}

	private static final int LEFT_ANGLE_OF_MOTION = 0;
	private static final int RIGHT_ANGLE_OF_MOTION = 180;
	private static final int TOP_ANGLE_OF_MOTION = 270;
	private static final int BOTTOM_ANGLE_OF_MOTION = 90;

	// A handle which references the GameBoard's built-in message queue 
	// provided by the Android OS; this handle is used by the BubbleThreads
	// created by the BubbleFactory to submit position update requests to the
	// GameBoard so that they can be rendered and displayed to the user
	private Handler messageHandler;
	
	private int screenHeight, screenWidth, virtualPadding;
	private Random random;
	
	/**
	 * Create a new BubbleFactory object.
	 * 
	 * 
	 * @param messageHandler	A {@link Handler} which will be used to send 
	 * 							position update requests to the game board
	 * 
	 * @param screenWidth		The width of the physical screen
	 * 	
	 * @param screenHeight		The height of the physical screen
	 * 
	 * @param virtualPadding	The amount of conceptual padding which 
	 * 							surrounds the physical screen to form the 
	 * 							complete game surface
	 */
	public BubbleFactory(Handler messageHandler, 
			int screenHeight , int screenWidth, int virtualPadding)	
	{
		this.messageHandler = messageHandler;
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.virtualPadding = virtualPadding;
		
		random = new Random(); 
	}
	
	/**
	 * Creates a new {@link UpdateRequest} which represents a request to add
	 * a bubble with the characteristics represented by the {@link BubbleData}
	 * which is encapsulated in the returned {@link UpdateRequest} whose motion
	 * is controlled by the {@link BubbleThread} which is also encapsulated in 
	 * the returned {@link UpdateRequest}.
	 * 
	 * @param 	color		The integer code which represents the starting 
	 * 						color of the bubble being created.
	 * 
	 * @param	maxRadius	The upper limit on the radius of the bubble to be
	 * 						created.
	 * 
	 * @return	A {@link UpdateRequest} containing a {@link BubbleData} which 
	 * 			characterizes the new bubble and a {@link BubbleThread} which
	 * 			will control the new bubble's motion.
	 */
	public UpdateRequest makeNewBubble(int color, int maxRadius)
	{
		SIDE side = pickRandomSide();
		BubbleData bubbleData = initRandomBubble(side, color, maxRadius);
		
		BubbleThread bubbleThread = new BubbleThread(messageHandler, bubbleData, 
			screenWidth, screenHeight, virtualPadding); 
		
		return new UpdateRequest(bubbleThread, bubbleData);
	}
	
	/**
	 * Pick a random side of the board for the bubble to spawn on.
	 */
	private SIDE pickRandomSide()
	{
		SIDE randomSide;
		switch(random.nextInt(4))
		{
		case 0:
			randomSide = SIDE.LEFT;
			break;
		case 1:
			randomSide = SIDE.RIGHT;
			break;
		case 2:
			randomSide = SIDE.TOP;
			break;
		case 3:
		default:
			randomSide = SIDE.BOTTOM;
		}
		
		return randomSide;
	}
	
	/**
	 * Creates and returns the data for a random bubble with the given 
	 * {@code color} which will have a radius greater than AppInfo.MIN_RADIUS
	 * and either {@code maxRadius} or AppInfo.MAX_RADIUS (whichever is 
	 * smaller) and a starting position within the virtual padding area
	 * surrounding the physical screen to whichever side of the screen is
	 * indicated by the {@code side} parameter in order to prevent the 
	 * bubble from appearing on top of the player's bubble.
	 * 
	 * @throws IllegalArgumentException	If the provided {@code maxRadius}
	 * value is less than AppInfo.MIN_RADIUS.
	 */
	private BubbleData initRandomBubble(SIDE side, int color, int maxRadius)
		throws IllegalArgumentException
	{
		GameUtils.throwIfLessThan(TAG, "maxRadius", maxRadius, AppInfo.MIN_RADIUS);
		
		// Generate a random starting radius from AppInfo.MIN_RADIUS to 
		// the effective maximum radius (either AppInfo.MAX_RADIUS or maxRadius
		// -whichever is smaller), with the call to random.nextInt() returning 
		// a pseudo-random integer from 0 (inclusive) to effectiveMaxRadius 
		// minus AppInfo.MIN_RADIUS (exclusive), which is why 
		// AppInfo.MIN_RADIUS must be added to arrive a value between 
		// AppInfo.MIN_RADIUS and effectiveMaxRadius
		
		int effectiveMaxRadius = maxRadius < AppInfo.MAX_RADIUS ? maxRadius : 
			AppInfo.MAX_RADIUS;
		
		int radius = random.nextInt(effectiveMaxRadius - AppInfo.MIN_RADIUS) + 
			AppInfo.MIN_RADIUS;
		
		float startX, startY;
		
		// Generate "random" starting coordinates for the new bubble on the
		// specified side of the board in the virtual padding region, with
		// no bubbles being spawned in the corners in order to simplify
		// giving the bubbles a starting direction which will take them out
		// of the spawning area and onto the screen. The bubble positions
		// will also be centered either horizontally or vertically in the
		// spawning area, depending on which direction is smaller (so
		// bubbles to the top and bottom of the screen will be centered
		// vertically and all other bubbles will be centered horizontally)
		// in order to ensure that the bubble will be fully contained in
		// the spawning area when it is created.
		switch(side)
		{
		case LEFT:
			 startX = virtualPadding / 2;
			 startY = (random.nextFloat() * screenHeight) + virtualPadding;
			break;
		case RIGHT:
			 startX = screenWidth + virtualPadding + 
			 	(virtualPadding / 2);
			 
			 startY = (random.nextFloat() * screenHeight) + virtualPadding;
			break;
		case TOP:
			 startX = (random.nextFloat() * screenWidth) + virtualPadding;
			 startY = virtualPadding / 2;
			break;
		case BOTTOM:
		default:
			 startX = (random.nextFloat() * screenWidth) + virtualPadding;
			 startY = screenHeight + virtualPadding + 
				(virtualPadding / 2);
		}
		
		return new BubbleData(color, startX, startY, radius, 
			getAngleOfMotionFromSide(side));
	}
	
	/**
	 * Returns the initial angle of motion that bubbles starting on the given
	 * {@code SIDE} should have. Note that angles based off of the positive 
	 * x-axis going clockwise, so a 0 degree angle will be towards the right of
	 * the screen while a 90 degree angle will be towards the bottom of the 
	 * screen.
	 */
	private int getAngleOfMotionFromSide(SIDE side)
	{
		int angle;
		
		switch(side)
		{
		case LEFT:
			angle = LEFT_ANGLE_OF_MOTION;
			break;
		case RIGHT:
			angle = RIGHT_ANGLE_OF_MOTION;
			break;
		case TOP:
			angle = TOP_ANGLE_OF_MOTION;
			break;
		case BOTTOM:
		default:
			angle = BOTTOM_ANGLE_OF_MOTION;
		}
		
		return angle;
	}
}
