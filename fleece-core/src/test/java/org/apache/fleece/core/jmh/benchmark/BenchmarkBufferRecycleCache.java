package org.apache.fleece.core.jmh.benchmark;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import javax.json.stream.JsonParserFactory;

import org.apache.fleece.core.BufferRecycleCache;
import org.apache.fleece.core.ByteBufferRecycleCache;
import org.apache.fleece.core.JsonCharBufferStreamParser;
import org.apache.fleece.core.JsonParserCurrent;
import org.apache.fleece.core.JsonSimpleStreamParser;
import org.apache.fleece.core.JsonStreamParser;
import org.apache.fleece.core.Strings;
import org.apache.fleece.core.Strings_Optimized;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Benchmark)
public class BenchmarkBufferRecycleCache {
 
	@Benchmark
    public void singleAllocateNative1k(Blackhole bh) throws Exception
		{
			byte[] c1= new byte[1024];
	    	bh.consume(c1);
		}
	
	@Benchmark
    public void singleAllocateNative4k(Blackhole bh) throws Exception
		{
			byte[] c1= new byte[1024*4];
	    	bh.consume(c1);
		}
	
	@Benchmark
    public void singleAllocateNative8k(Blackhole bh) throws Exception
		{
			byte[] c1= new byte[1024*8];
	    	bh.consume(c1);
		}
	
	@Benchmark
    public void singleAllocateNative16k(Blackhole bh) throws Exception
		{
			byte[] c1= new byte[1024*16];
	    	bh.consume(c1);
		}
	
	@Benchmark
    public void singleAllocateNative32k(Blackhole bh) throws Exception
		{
			byte[] c1= new byte[1024*16*2];
	    	bh.consume(c1);
		}
	
	@Benchmark
    public void singleAllocateNative64k(Blackhole bh) throws Exception
		{
			byte[] c1= new byte[1024*16*4];
	    	bh.consume(c1);
		}
	
	@Benchmark
    public void singleAllocateNative512k(Blackhole bh) throws Exception
		{
			byte[] c1= new byte[1024*512];
	    	bh.consume(c1);
		}
	
	
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
		    BufferRecycleCache<byte[]> buf = new ByteBufferRecycleCache(5,10);
		
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
    public void singleAllocate5xRecycled512kSynchronized(Blackhole bh) throws Exception
		{
		    BufferRecycleCache<byte[]> buf = BufferRecycleCache.synchronizedBufferRecycleCache(new ByteBufferRecycleCache(5,10));
		
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
	
	@Benchmark
    public void singleAllocate10xRecycled512k(Blackhole bh) throws Exception
		{
		    BufferRecycleCache<byte[]> buf = new ByteBufferRecycleCache(5,10);
		
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
    public void allocateBuffersWithRecycleBuffer(Blackhole bh) throws Exception {
    	
    	BufferRecycleCache<byte[]> buf = new ByteBufferRecycleCache(5,10);
    	
    	for(int i=0;i<100;i++)
    	{
    	byte[] c1=null;
    	byte[] c2=null;
    	byte[] c3=null;
    	byte[] c4=null;
    	byte[] c5=null;
    	byte[] c6=null;
    	byte[] c7=null;
    	byte[] c8=null;
    	byte[] c9=null;
    	
    	bh.consume(c1=buf.getBuffer(1024));
    	bh.consume(c2=buf.getBuffer(1024));
    	bh.consume(c3=buf.getBuffer(1024));
    	bh.consume(c4=buf.getBuffer(1024*8));
    	bh.consume(c5=buf.getBuffer(1024*8));
    	bh.consume(c6=buf.getBuffer(1024*8));
    	bh.consume(c7=buf.getBuffer(1024*32));
    	bh.consume(c8=buf.getBuffer(1024*32));
    	bh.consume(c9=buf.getBuffer(1024*32));
    	
    	buf.releaseBuffer(c1);
    	buf.releaseBuffer(c2);
    	buf.releaseBuffer(c3);
    	buf.releaseBuffer(c4);
    	buf.releaseBuffer(c5);
    	buf.releaseBuffer(c6);
    	buf.releaseBuffer(c7);
    	
    	bh.consume(c1=buf.getBuffer(1024));
    	bh.consume(c2=buf.getBuffer(1024));
    	bh.consume(c3=buf.getBuffer(1024));
    	bh.consume(c4=buf.getBuffer(1024*8));
    	bh.consume(c5=buf.getBuffer(1024*8));
    	bh.consume(c6=buf.getBuffer(1024*8));
    	bh.consume(c7=buf.getBuffer(1024*32));
    	bh.consume(c8=buf.getBuffer(1024*32));
    	bh.consume(c9=buf.getBuffer(1024*32));
    	
    	}
    }

    @Benchmark
    public void allocateBuffersWithoutRecycleBuffer(Blackhole bh) throws Exception {
    	
    	for(int i=0;i<100;i++)
    	{
    	byte[] c1= new byte[1024];
    	bh.consume(c1);
    	
    	c1= new byte[1024];
    	bh.consume(c1);
    	
    	c1= new byte[1024];
    	bh.consume(c1);
    	
    	c1= new byte[1024*8];
    	bh.consume(c1);
    	
    	c1= new byte[1024*8];
    	bh.consume(c1);
    	
    	c1= new byte[1024*8];
    	bh.consume(c1);
    	
    	c1= new byte[1024*32];
    	bh.consume(c1);
    	
    	c1= new byte[1024*32];
    	bh.consume(c1);
    	
    	c1= new byte[1024*32];
    	bh.consume(c1);
    	
    	c1= new byte[1024];
    	bh.consume(c1);
    	
    	c1= new byte[1024];
    	bh.consume(c1);
    	
    	c1= new byte[1024];
    	bh.consume(c1);
    	
    	c1= new byte[1024*8];
    	bh.consume(c1);
    	
    	c1= new byte[1024*8];
    	bh.consume(c1);
    	
    	c1= new byte[1024*8];
    	bh.consume(c1);
    	
    	c1= new byte[1024*32];
    	bh.consume(c1);
    	
    	c1= new byte[1024*32];
    	bh.consume(c1);
    	
    	c1= new byte[1024*32];
    	bh.consume(c1);}
    	
    }

    
}
