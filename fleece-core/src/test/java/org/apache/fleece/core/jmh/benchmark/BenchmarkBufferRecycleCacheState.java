package org.apache.fleece.core.jmh.benchmark;

import org.apache.fleece.core.BufferRecycleCache;
import org.apache.fleece.core.ByteBufferRecycleCache;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Thread)
public class BenchmarkBufferRecycleCacheState {
 
	
	private final BufferRecycleCache<byte[]> buf = new ByteBufferRecycleCache(5,10);
	
	
	@Benchmark
    public void singleAllocate5xNative512k(Blackhole bh) throws Exception
		{
			byte[] c1= new byte[1024*512];
	    	bh.consume(c1);
	    	c1= new byte[1024*512];
	    	bh.consume(c1);
	    	c1= new byte[1024*512];
	    	bh.consume(c1);
	    	c1= new byte[1024*512];
	    	bh.consume(c1);
	    	c1= new byte[1024*512];
	    	bh.consume(c1);
		}
	
	@Benchmark
    public void singleAllocate5xRecycled512k(Blackhole bh) throws Exception
		{
		    		
			byte[] c1= buf.getBuffer(1024*512);
	    	bh.consume(c1);
	    	buf.releaseBuffer(c1);

	    	c1= buf.getBuffer(1024*512);
	    	bh.consume(c1);
	    	buf.releaseBuffer(c1);
	    	
	    	c1= buf.getBuffer(1024*512);
	    	bh.consume(c1);
	    	buf.releaseBuffer(c1);
	    	
	    	c1= buf.getBuffer(1024*512);
	    	bh.consume(c1);
	    	buf.releaseBuffer(c1);
	    	
	    	c1= buf.getBuffer(1024*512);
	    	bh.consume(c1);
	    	buf.releaseBuffer(c1);
		}
	
	
	
	@Benchmark
    public void singleAllocate10xRecycled512k(Blackhole bh) throws Exception
		{
		   		
			byte[] c1= buf.getBuffer(1024*512);
	    	bh.consume(c1);
	    	buf.releaseBuffer(c1);

	    	c1= buf.getBuffer(1024*512);
	    	bh.consume(c1);
	    	buf.releaseBuffer(c1);
	    	
	    	c1= buf.getBuffer(1024*512);
	    	bh.consume(c1);
	    	buf.releaseBuffer(c1);
	    	
	    	c1= buf.getBuffer(1024*512);
	    	bh.consume(c1);
	    	buf.releaseBuffer(c1);
	    	
	    	c1= buf.getBuffer(1024*512);
	    	bh.consume(c1);
	    	buf.releaseBuffer(c1);
	    	c1= buf.getBuffer(1024*512);
	    	bh.consume(c1);
	    	buf.releaseBuffer(c1);

	    	c1= buf.getBuffer(1024*512);
	    	bh.consume(c1);
	    	buf.releaseBuffer(c1);
	    	
	    	c1= buf.getBuffer(1024*512);
	    	bh.consume(c1);
	    	buf.releaseBuffer(c1);
	    	
	    	c1= buf.getBuffer(1024*512);
	    	bh.consume(c1);
	    	buf.releaseBuffer(c1);
	    	
	    	c1= buf.getBuffer(1024*512);
	    	bh.consume(c1);
	    	buf.releaseBuffer(c1);
		}

	@Benchmark
	public void singleAllocate10xNative512k(Blackhole bh) throws Exception
		{
			byte[] c1= new byte[1024*512];
	    	bh.consume(c1);
	    	c1= new byte[1024*512];
	    	bh.consume(c1);
	    	c1= new byte[1024*512];
	    	bh.consume(c1);
	    	c1= new byte[1024*512];
	    	bh.consume(c1);
	    	c1= new byte[1024*512];
	    	bh.consume(c1);
	    	 c1= new byte[1024*512];
		    	bh.consume(c1);
		    	c1= new byte[1024*512];
		    	bh.consume(c1);
		    	c1= new byte[1024*512];
		    	bh.consume(c1);
		    	c1= new byte[1024*512];
		    	bh.consume(c1);
		    	c1= new byte[1024*512];
		    	bh.consume(c1);
		}

    
}
