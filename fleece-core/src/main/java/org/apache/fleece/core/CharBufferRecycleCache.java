package org.apache.fleece.core;

public class CharBufferRecycleCache extends BufferRecycleCache<char[]> {

	@Override
	protected char[] newValue(int bufferSize) {
		return new char[bufferSize];
	}

	@Override
	protected int getSize(char[] obj) {
		return obj.length;
	}

	public CharBufferRecycleCache() {
		super();
	}

	public CharBufferRecycleCache(int initalObjects,
			int expectedDifferntBufferSize) {
		super(initalObjects, expectedDifferntBufferSize);
	}

	public CharBufferRecycleCache(int initalObjects) {
		super(initalObjects);
	}

}
