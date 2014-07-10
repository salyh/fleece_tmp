package org.apache.fleece.core;

public class ByteBufferRecycleCache extends BufferRecycleCache<byte[]> {

	@Override
	protected byte[] newValue(int bufferSize) {
		return new byte[bufferSize];
	}

	@Override
	protected int getSize(byte[] obj) {
		return obj.length;
	}

	public ByteBufferRecycleCache() {
		super();
	}

	public ByteBufferRecycleCache(int initalObjects,
			int expectedDifferntBufferSize) {
		super(initalObjects, expectedDifferntBufferSize);
	}

	public ByteBufferRecycleCache(int initalObjects) {
		super(initalObjects);
	}

}
