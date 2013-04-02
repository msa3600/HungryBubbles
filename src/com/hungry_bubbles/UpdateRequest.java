package com.hungry_bubbles;

/**
 * POJO (Plain Old Java Object) representation of a request to update the
 * position of a bubble.
 * 
 * @author Timothy Heard
 */
public class UpdateRequest
{
	private final BubbleThread requester;
	private final BubbleData newPosition;
	
	public UpdateRequest(BubbleThread requester, BubbleData newPosition)
	{
		this.requester = requester;
		this.newPosition = newPosition;
	}

	// TODO: Be aware of the fact that this will result in a mutable reference
	// to the BubbleThread who is requesting the position update to escape
	public BubbleThread getRequester()
	{
		return requester;
	}

	public BubbleData getNewPosition()
	{
		return newPosition;
	}
}
