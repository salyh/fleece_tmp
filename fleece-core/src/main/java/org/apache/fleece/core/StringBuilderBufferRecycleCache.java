package org.apache.fleece.core;

import org.apache.fleece.core.StringBuilderBufferRecycleCache.FixedSizeAwareStringBuilder;

public class StringBuilderBufferRecycleCache extends BufferRecycleCache<FixedSizeAwareStringBuilder> {

    @Override
    protected FixedSizeAwareStringBuilder newValue(final int bufferSize) {
        return new FixedSizeAwareStringBuilder(new StringBuilder(bufferSize), bufferSize);
    }

    @Override
    protected int getSize(final FixedSizeAwareStringBuilder obj) {
        return obj.getInitalCapacity();
    }

    public StringBuilderBufferRecycleCache() {
        super();
    }

    public StringBuilderBufferRecycleCache(final int initalObjects, final int expectedDifferntBufferSize) {
        super(initalObjects, expectedDifferntBufferSize);
    }

    public StringBuilderBufferRecycleCache(final int initalObjects) {
        super(initalObjects);
    }

    public static class FixedSizeAwareStringBuilder {
        private final StringBuilder stringBuiilder;
        private final int initalCapacity;

        public FixedSizeAwareStringBuilder(final StringBuilder stringBuiilder, final int initalCapacity) {
            super();
            this.stringBuiilder = stringBuiilder;
            this.initalCapacity = initalCapacity;
        }

        public StringBuilder getStringBuiilder() {
            return stringBuiilder;
        }

        private int getInitalCapacity() {
            return initalCapacity;
        }

    }

}
