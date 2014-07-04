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
public class BenchmarkStringsCompact {
 

    @Benchmark
    public void normalWithUnicode(Blackhole bh) throws Exception {
    	bh.consume(Strings.escape(Buffers.STR_UNICODESTXT_BYTES));
    	bh.consume(Strings.escape(Buffers.STR_ACTION_LABEL_BYTES));
    	bh.consume(Strings.escape(Buffers.STR_UNICODESTXT_BYTES));
    	bh.consume(Strings.escape(Buffers.STR_UNICODESTXT_BYTES));        
        bh.consume(Strings.escape(Buffers.STR_MEDIUM_BYTES));
        bh.consume(Strings.escape(Buffers.STR_MENU_BYTES));
        bh.consume(Strings.escape(Buffers.STR_SMALL_BYTES));
        bh.consume(Strings.escape(Buffers.STR_SGML_BYTES));
        bh.consume(Strings.escape(Buffers.STR_WIDGET_BYTES));
        bh.consume(Strings.escape(Buffers.STR_WEBXML_BYTES));
        bh.consume(Strings.escape(Buffers.STR_UNICODESTXT_BYTES));
        bh.consume(Strings.escape(Buffers.STR_UNICODESTXT_BYTES));
        bh.consume(Strings.escape(Buffers.STR_UNICODESTXT_BIG_BYTES));
    }
    
    @Benchmark
    public void optimizedWithUnicode(Blackhole bh) throws Exception {
    	bh.consume(Strings_Optimized.escape(Buffers.STR_UNICODESTXT_BYTES));
    	bh.consume(Strings_Optimized.escape(Buffers.STR_ACTION_LABEL_BYTES));
    	bh.consume(Strings_Optimized.escape(Buffers.STR_UNICODESTXT_BYTES));
    	bh.consume(Strings_Optimized.escape(Buffers.STR_UNICODESTXT_BYTES));        
        bh.consume(Strings_Optimized.escape(Buffers.STR_MEDIUM_BYTES));
        bh.consume(Strings_Optimized.escape(Buffers.STR_MENU_BYTES));
        bh.consume(Strings_Optimized.escape(Buffers.STR_SMALL_BYTES));
        bh.consume(Strings_Optimized.escape(Buffers.STR_SGML_BYTES));
        bh.consume(Strings_Optimized.escape(Buffers.STR_WIDGET_BYTES));
        bh.consume(Strings_Optimized.escape(Buffers.STR_WEBXML_BYTES));
        bh.consume(Strings_Optimized.escape(Buffers.STR_UNICODESTXT_BYTES));
        bh.consume(Strings_Optimized.escape(Buffers.STR_UNICODESTXT_BYTES));
        bh.consume(Strings_Optimized.escape(Buffers.STR_UNICODESTXT_BIG_BYTES));
        
    }

    @Benchmark
    public void normalWithoutUnicode(Blackhole bh) throws Exception {
    	
    	bh.consume(Strings.escape(Buffers.STR_ACTION_LABEL_BYTES));
        bh.consume(Strings.escape(Buffers.STR_CITM_CATALOG_BYTES));
        bh.consume(Strings.escape(Buffers.STR_MEDIUM_BYTES));
        bh.consume(Strings.escape(Buffers.STR_MENU_BYTES));
        bh.consume(Strings.escape(Buffers.STR_SMALL_BYTES));
        bh.consume(Strings.escape(Buffers.STR_SGML_BYTES));
        bh.consume(Strings.escape(Buffers.STR_WIDGET_BYTES));
        bh.consume(Strings.escape(Buffers.STR_WEBXML_BYTES));
        
    }
    
    @Benchmark
    public void optimizedWithoutUnicode(Blackhole bh) throws Exception {
    
    	bh.consume(Strings.escape(Buffers.STR_ACTION_LABEL_BYTES));
        bh.consume(Strings.escape(Buffers.STR_CITM_CATALOG_BYTES));
        bh.consume(Strings.escape(Buffers.STR_MEDIUM_BYTES));
        bh.consume(Strings.escape(Buffers.STR_MENU_BYTES));
        bh.consume(Strings.escape(Buffers.STR_SMALL_BYTES));
        bh.consume(Strings.escape(Buffers.STR_SGML_BYTES));
        bh.consume(Strings.escape(Buffers.STR_WIDGET_BYTES));
        bh.consume(Strings.escape(Buffers.STR_WEBXML_BYTES));
       
    }

    
}
