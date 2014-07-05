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
public class BenchmarkBufferRecycleCacheStateSynced {
 
	
	private final BufferRecycleCache<byte[]> buf = ByteBufferRecycleCache.synchronizedBufferRecycleCache( new ByteBufferRecycleCache(5,10));
	
	
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
