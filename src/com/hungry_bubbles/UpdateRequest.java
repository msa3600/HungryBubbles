package com.hungry_bubbles;

/**
 * POJO (Plain Old Java Object) representation of a request to update the
 * position of a bubble.
 * 
 * @author Timothy Heard, Shaun DeVos, John O'Brien, Mustafa Al Salihi
 */
public class UpdateRequest
{
	private final BubbleThread requester;
	private final BubbleData newPosition;
	
	/**
	 * Create a new {@code UpdateRequest} with the given {@code requester}
	 * and {@code newPosition}.
	 */
	public UpdateRequest(BubbleThread requester, BubbleData newPosition)
	{
		this.requester = requester;
		this.newPosition = newPosition;
	}

	/**
	 * Returns a reference to the {@link BubbleThread} which initiated this 
	 * {@code UpdateRequest}. Be aware of the fact that the caller is taking
	 * on shared ownership of this {@link BubbleThread} upon calling this
	 * method as the caller will have a mutable reference to the 
	 * {@link BubbleThread} which made the position update request.
	 */
	public BubbleThread getRequester()
	{
		return requester;
	}

	/**
	 * Get the position the requester wants to move its bubble to.
	 * 
	 * @return	The {@link BubbleData} object which represents the position
	 * 			the requester wants to move their bubble to.
	 */
	public BubbleData getPosition()
	{
		return newPosition;
	}
}
