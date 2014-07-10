package org.apache.fleece.core.jmh.benchmark;

import org.apache.fleece.core.Strings;
import org.apache.fleece.core.Strings_Optimized;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

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
