package org.apache.fleece.core.jmh.benchmark;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.InputStream;
import java.io.Reader;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

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
    public void genhuge(Blackhole bh) throws Exception {
        bh.consume(parse(new ByteArrayInputStream(Buffers.GENHUGE_BYTES)));

    }
    
    @Benchmark
    public void genmedium(Blackhole bh) throws Exception {
        bh.consume(parse(new ByteArrayInputStream(Buffers.GENMEDIUM_BYTES)));

    }
    
    @Benchmark
    public void small_Reader(Blackhole bh) throws Exception {
        bh.consume(parse(new CharArrayReader(Buffers.CHR_SMALL_BYTES)));

    }
    
    @Benchmark
    public void small(Blackhole bh) throws Exception {
        bh.consume(parse(new ByteArrayInputStream(Buffers.SMALL_BYTES)));

    }
    
    @Benchmark
    public void genhuge_Reader(Blackhole bh) throws Exception {
        bh.consume(parse(new CharArrayReader(Buffers.CHR_GENHUGE_BYTES)));

    }
    
    @Benchmark
    public void genmedium_Reader(Blackhole bh) throws Exception {
        bh.consume(parse(new CharArrayReader(Buffers.CHR_GENMEDIUM_BYTES)));

    }
    
    @Benchmark
    public void combined_Reader(Blackhole bh) throws Exception {
        bh.consume(parse(new CharArrayReader(Buffers.CHR_GENMEDIUM_BYTES)));
        bh.consume(parse(new CharArrayReader(Buffers.CHR_WEBXML_BYTES)));
        bh.consume(parse(new CharArrayReader(Buffers.CHR_GENMEDIUM_BYTES)));
        bh.consume(parse(new CharArrayReader(Buffers.CHR_WEBXML_BYTES)));
        bh.consume(parse(new CharArrayReader(Buffers.CHR_GENMEDIUM_BYTES)));
        
    }
    
    @Benchmark
    public void combined(Blackhole bh) throws Exception {
        bh.consume(parse(new ByteArrayInputStream(Buffers.GENMEDIUM_BYTES)));
        bh.consume(parse(new ByteArrayInputStream(Buffers.WEBXML_BYTES)));
        bh.consume(parse(new ByteArrayInputStream(Buffers.GENMEDIUM_BYTES)));
        bh.consume(parse(new ByteArrayInputStream(Buffers.WEBXML_BYTES)));
        bh.consume(parse(new ByteArrayInputStream(Buffers.GENMEDIUM_BYTES)));
        
    }
}
