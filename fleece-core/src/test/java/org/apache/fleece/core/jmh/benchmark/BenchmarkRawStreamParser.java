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
public abstract class BenchmarkRawStreamParser {

    /*
     * Benchmark                                            Mode   Samples        Score  Score error    Units
o.a.f.c.j.b.BenchmarkRawStreamParser.actionLabel    thrpt         5    45209,278     2672,979    ops/s
o.a.f.c.j.b.BenchmarkRawStreamParser.citmCatalog    thrpt         5       30,574        4,616    ops/s
o.a.f.c.j.b.BenchmarkRawStreamParser.medium         thrpt         5    22989,442     2486,086    ops/s
o.a.f.c.j.b.BenchmarkRawStreamParser.menu           thrpt         5   101780,209     5925,566    ops/s
o.a.f.c.j.b.BenchmarkRawStreamParser.sgml           thrpt         5    61762,731     7852,292    ops/s
o.a.f.c.j.b.BenchmarkRawStreamParser.webxml         thrpt         5    12566,670     1322,656    ops/s
o.a.f.c.j.b.BenchmarkRawStreamParser.widget         thrpt         5    47033,145     4527,860    ops/s
     */

 
    //http://java-performance.info/jmh/
 

    protected abstract Object parse(InputStream stream) throws Exception ;
    
    protected abstract Object parse(Reader reader) throws Exception ;
    @Benchmark
    public void actionLabel(Blackhole bh) throws Exception {
        bh.consume(parse(new ByteArrayInputStream(Buffers.ACTION_LABEL_BYTES)));
    }
    
    @Benchmark
    public void actionLabel_Reader(Blackhole bh) throws Exception {
        bh.consume(parse(new CharArrayReader(Buffers.CHR_ACTION_LABEL_BYTES)));
    }

    @Benchmark
    public void citmCatalog(Blackhole bh) throws Exception {

        bh.consume(parse(new ByteArrayInputStream(Buffers.CITM_CATALOG_BYTES)));
    }
    
    @Benchmark
    public void citmCatalog_Reader(Blackhole bh) throws Exception {
        bh.consume(parse(new CharArrayReader(Buffers.CHR_CITM_CATALOG_BYTES)));
    }

    @Benchmark
    public void medium(Blackhole bh) throws Exception {
        bh.consume(parse(new ByteArrayInputStream(Buffers.MEDIUM_BYTES)));
    }
    
    @Benchmark
    public void medium_Reader(Blackhole bh) throws Exception {
        bh.consume(parse(new CharArrayReader(Buffers.CHR_MEDIUM_BYTES)));
    }

    @Benchmark
    public void menu(Blackhole bh) throws Exception {

        bh.consume(parse(new ByteArrayInputStream(Buffers.MENU_BYTES)));
    }
    
    @Benchmark
    public void menu_Reader(Blackhole bh) throws Exception {

        bh.consume(parse(new CharArrayReader(Buffers.CHR_MENU_BYTES)));
    }
    

    @Benchmark
    public void sgml(Blackhole bh) throws Exception {

        bh.consume(parse(new ByteArrayInputStream(Buffers.SGML_BYTES)));
    }
    
    @Benchmark
    public void sgml_Reader(Blackhole bh) throws Exception {

        bh.consume(parse(new CharArrayReader(Buffers.CHR_SGML_BYTES)));
    }

    @Benchmark
    public void webxml(Blackhole bh) throws Exception {

        bh.consume(parse(new ByteArrayInputStream(Buffers.WEBXML_BYTES)));

    }
    
    @Benchmark
    public void webxml_Reader(Blackhole bh) throws Exception {

        bh.consume(parse(new CharArrayReader(Buffers.CHR_WEBXML_BYTES)));

    }

    @Benchmark
    public void widget(Blackhole bh) throws Exception {
        bh.consume(parse(new ByteArrayInputStream(Buffers.WIDGET_BYTES)));

    }
    
    @Benchmark
    public void widget_Reader(Blackhole bh) throws Exception {
        bh.consume(parse(new CharArrayReader(Buffers.CHR_WIDGET_BYTES)));

    }

    
    @Benchmark
    public void small(Blackhole bh) throws Exception {
        bh.consume(parse(new ByteArrayInputStream(Buffers.SMALL_BYTES)));

    }
    
    @Benchmark
    public void small_Reader(Blackhole bh) throws Exception {
        bh.consume(parse(new CharArrayReader(Buffers.CHR_SMALL_BYTES)));

    }
}
