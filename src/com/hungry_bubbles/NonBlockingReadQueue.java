package com.hungry_bubbles;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A special queue implementation which allows for items to be popped off of 
 * the front while elements are being added to minimize how long readers
 * must block for when attempting to read data.
 * 
 * @author Timothy Heard, Shaun DeVos, John O'Brien, Mustafa Al Salihi
 */
public class NonBlockingReadQueue<T>
{
	private Lock queueLock;
	private LockingNode<T> head, tail;
	
	public NonBlockingReadQueue()
	{
		queueLock = new ReentrantLock();
		head = null;
		tail = null;
	}
	
	/**
	 * Insert a new item at the end of the queue.
	 * 
	 * @param 	value	The item to insert.
	 */
	public void put(T value)
	{
		queueLock.lock();
		
		if(head == null)
		{
			head = new LockingNode<T>(value);
			tail = head;
			queueLock.unlock();
		}
		else
		{
			tail.lockNode();
			queueLock.unlock();
			LockingNode<T> newNode = new LockingNode<T>(value);
			tail.setNext(newNode);
			LockingNode<T> lockedNode = tail;
			tail = newNode;
			lockedNode.unlockNode();
		}
	}
	
	/**
	 * Remove and return an item from the front of the queue.
	 * 
	 * @return	The first item in the queue or {@code null} if the queue is 
	 * 			empty.
	 */
	public T pop()
	{
		queueLock.lock();
		if(head == null)
		{
			queueLock.unlock();
			return null;
		}
		
		head.lockNode();
		LockingNode<T> oldHead = head; 
		LockingNode<T> newHead = head.getNext();
		head = newHead;
		queueLock.unlock();
		T retVal = oldHead.getValue();
		oldHead.unlockNode();
		
		return retVal;
	}
	
	/**
	 * Will attempt to pop values off of the queue until either the end of the
	 * queue is reached or a locked node is encountered.
	 * 
	 * @param timeInMilliseconds	The longest amount of time that the caller 
	 * 								is willing to wait
	 * 
	 * @return	A {@link List} of values which could be obtained without 
	 * 			blocking for longer than {@code timeInMilliseconds} 
	 * 			milliseconds. Will be {@code null} if the queue could not be 
	 * 			accessed within the specified amount of time or an empty list
	 * 			if the queue could be accessed but either the queue is empty or
	 * 			no elements within the queue could be reached without blocking.
	 * 
	 * @throws InterruptedException		If an {@link InterruptedException}
	 * 									is thrown while attempting to gain
	 * 									exclusive access to the queue.
	 */
	public List<T> nonblockingPop(long timeInMilliseconds) 
		throws InterruptedException
	{
		if(queueLock.tryLock(timeInMilliseconds, TimeUnit.MILLISECONDS))
		{
			List<T> immediatelyAvailableValues = new ArrayList<T>();
			
			// If the head node is not null (i.e. the end of the queue has 
			// not been reached) and the current head node can be locked 
			// without waiting then proceed with popping the head node off
			// and retrieving its value
			while(head != null && head.tryLockNoWait())
			{
				LockingNode<T> oldHead = head; 
				LockingNode<T> newHead = head.next;
				head = newHead;
				immediatelyAvailableValues.add(oldHead.getValue());
				oldHead.unlockNode();
			}

			queueLock.unlock();			
			return immediatelyAvailableValues;
		}
		
		return null;
	}
	
	/**
	 * A node in the queue which can be independently locked. 
	 */
	private class LockingNode<E>
	{
		private Lock nodeLock;
		private LockingNode<E> next;
		private E value;
		
		public LockingNode(E value)
		{
			this.value = value;
			next = null;
			nodeLock = new ReentrantLock();
		}
		
		public LockingNode<E> getNext()
		{
			return next;
		}
		
		public void setNext(LockingNode<E> next)
		{
			this.next = next;
		}
		
		public E getValue()
		{
			E retVal;
			nodeLock.lock();
			retVal = value;
			nodeLock.unlock();
			
			return retVal;
		}
		
		public void lockNode()
		{
			nodeLock.lock();
		}
		
		public boolean tryLockNoWait() 
			throws InterruptedException
		{
			return nodeLock.tryLock(0, TimeUnit.MILLISECONDS);
		}
		
		public void unlockNode()
		{
			if(Thread.holdsLock(nodeLock))
			{
				nodeLock.unlock();
			}
		}
	}
}
