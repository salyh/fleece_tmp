package org.apache.fleece.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BufferRecycleCache<T> {

	private final Map<Integer, List<T>> cache;
	private final int initalObjects;

		
	public BufferRecycleCache(final int initalObjects, final int expectedDifferentBufferSize) {
		super();
		if(initalObjects < 0 || expectedDifferentBufferSize<=0) throw new IllegalArgumentException();
		this.initalObjects = initalObjects;
		cache = new HashMap<Integer, List<T>>(expectedDifferentBufferSize);
	}

	public BufferRecycleCache() {
		this(5,10);
	}
	
	public BufferRecycleCache(final int initalObjects) {
		this(initalObjects,10);
	}

	public T getBuffer (final int size)
	{				
			if(size<0) throw new IllegalArgumentException("size must be zero or more");
		
			List<T> objects=null;
		
			if((objects=cache.get(size)) == null) 
			{			
				cache.put(size, objects = new ArrayList<T>(10));
				
				for(int i=0;i<initalObjects;i++)
				{
					objects.add(newValue(size)); //O(1) amortized
				}
				
				
				return newValue(size);
				
				
			}else
			{
				if(objects.size() == 0) //??
				{
					return newValue(size);
				}
				else
				{
					return objects.remove(0); //?? last is O(1)
					
				}
			}
	}
	
	public void releaseBuffer(final  T obj)
	{
		if(obj==null) return;
		
		List<T> objects=null;
		final int size = getSize(obj);
		if((objects=cache.get(size)) != null)
		{				
			if(!objects.contains(obj)) //?
			{
				objects.add(obj); //O(1)
			}
		}
	}
	
	public void reset()
	{
		cache.clear();
	}
	
	protected abstract T newValue(final int bufferSize);
	protected abstract int getSize(final T obj);
	
}
