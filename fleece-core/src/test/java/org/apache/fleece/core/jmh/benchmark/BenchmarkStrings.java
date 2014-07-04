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
public class BenchmarkStrings {
 

    @Benchmark
    public void actionLabel(Blackhole bh) throws Exception {
        bh.consume(Strings.escape(Buffers.STR_ACTION_LABEL_BYTES));
    }
    
    

    @Benchmark
    public void citmCatalog(Blackhole bh) throws Exception {

        bh.consume(Strings.escape(Buffers.STR_CITM_CATALOG_BYTES));
    }
    
    

    @Benchmark
    public void medium(Blackhole bh) throws Exception {
        bh.consume(Strings.escape(Buffers.STR_MEDIUM_BYTES));
    }
    
    

    @Benchmark
    public void menu(Blackhole bh) throws Exception {

        bh.consume(Strings.escape(Buffers.STR_MENU_BYTES));
    }
    
    @Benchmark
    public void small(Blackhole bh) throws Exception {

        bh.consume(Strings.escape(Buffers.STR_SMALL_BYTES));
    }
    

    @Benchmark
    public void sgml(Blackhole bh) throws Exception {

        bh.consume(Strings.escape(Buffers.STR_SGML_BYTES));
    }
    
    
    
    
    

    @Benchmark
    public void webxml(Blackhole bh) throws Exception {

        bh.consume(Strings.escape(Buffers.STR_WEBXML_BYTES));

    }
    
   

    @Benchmark
    public void widget(Blackhole bh) throws Exception {
        bh.consume(Strings.escape(Buffers.STR_WIDGET_BYTES));

    }
    
   
    
    
    
    
    @Benchmark
    public void actionLabel_Optimized(Blackhole bh) throws Exception {
        bh.consume(Strings_Optimized.escape(Buffers.STR_ACTION_LABEL_BYTES));
    }
    
    

    @Benchmark
    public void citmCatalog_Optimized(Blackhole bh) throws Exception {

        bh.consume(Strings_Optimized.escape(Buffers.STR_CITM_CATALOG_BYTES));
    }
    
    

    @Benchmark
    public void medium_Optimized(Blackhole bh) throws Exception {
        bh.consume(Strings_Optimized.escape(Buffers.STR_MEDIUM_BYTES));
    }
    
    

    @Benchmark
    public void menu_Optimized(Blackhole bh) throws Exception {

        bh.consume(Strings_Optimized.escape(Buffers.STR_MENU_BYTES));
    }
    
   
    

    @Benchmark
    public void sgml_Optimized(Blackhole bh) throws Exception {

        bh.consume(Strings_Optimized.escape(Buffers.STR_SGML_BYTES));
    }
    
    

    @Benchmark
    public void webxml_Optimized(Blackhole bh) throws Exception {

        bh.consume(Strings_Optimized.escape(Buffers.STR_WEBXML_BYTES));

    }
    
   

    @Benchmark
    public void widget_Optimized(Blackhole bh) throws Exception {
        bh.consume(Strings_Optimized.escape(Buffers.STR_WIDGET_BYTES));

    }
    
    @Benchmark
    public void small_Optimized(Blackhole bh) throws Exception {

        bh.consume(Strings_Optimized.escape(Buffers.STR_SMALL_BYTES));
    }

}
