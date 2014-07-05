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
		// TODO Auto-generated constructor stub
	}

	public ByteBufferRecycleCache(int initalObjects,
			int expectedDifferntBufferSize) {
		super(initalObjects, expectedDifferntBufferSize);
		// TODO Auto-generated constructor stub
	}

	public ByteBufferRecycleCache(int initalObjects) {
		super(initalObjects);
		// TODO Auto-generated constructor stub
	}

}
