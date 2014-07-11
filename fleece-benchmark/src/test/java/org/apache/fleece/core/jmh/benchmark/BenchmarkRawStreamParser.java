/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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


 
    //http://java-performance.info/jmh/
 

    protected abstract Object parse(InputStream stream) throws Exception ;
    
    protected abstract Object parse(Reader reader) throws Exception ;
protected abstract Object read(InputStream stream) throws Exception ;
    
    protected abstract Object read(Reader reader) throws Exception ;
    
    
    @Benchmark
    public void parse_only_genhuge(Blackhole bh) throws Exception {
        bh.consume(parse(new ByteArrayInputStream(Buffers.GENHUGE_BYTES)));

    }
    
    @Benchmark
    public void parse_only_genmedium(Blackhole bh) throws Exception {
        bh.consume(parse(new ByteArrayInputStream(Buffers.GENMEDIUM_BYTES)));

    }
    
    @Benchmark
    public void parse_only_tiny(Blackhole bh) throws Exception {
        bh.consume(parse(new ByteArrayInputStream(Buffers.GENMEDIUM_TINY)));

    }
    
   
    @Benchmark
    public void parse_only_genhuge_Reader(Blackhole bh) throws Exception {
        bh.consume(parse(new CharArrayReader(Buffers.CHR_GENHUGE_BYTES)));

    }
    
    @Benchmark
    public void parse_only_genmedium_Reader(Blackhole bh) throws Exception {
        bh.consume(parse(new CharArrayReader(Buffers.CHR_GENMEDIUM_BYTES)));

    }
    
    @Benchmark
    public void parse_only_tiny_Reader(Blackhole bh) throws Exception {
        bh.consume(parse(new CharArrayReader(Buffers.CHR_GENMEDIUM_TINY)));

    }
    
    @Benchmark
    public void parse_only_combined_Reader(Blackhole bh) throws Exception {
        bh.consume(parse(new CharArrayReader(Buffers.CHR_GENMEDIUM_BYTES)));
        bh.consume(parse(new CharArrayReader(Buffers.CHR_GENMEDIUM_TINY)));
        bh.consume(parse(new CharArrayReader(Buffers.CHR_GENMEDIUM_BYTES)));
        bh.consume(parse(new CharArrayReader(Buffers.CHR_GENMEDIUM_TINY)));
        bh.consume(parse(new CharArrayReader(Buffers.CHR_GENMEDIUM_BYTES)));
        
    }
    
    @Benchmark
    public void parse_only_combined(Blackhole bh) throws Exception {
        bh.consume(parse(new ByteArrayInputStream(Buffers.GENMEDIUM_BYTES)));
        bh.consume(parse(new ByteArrayInputStream(Buffers.GENMEDIUM_TINY)));
        bh.consume(parse(new ByteArrayInputStream(Buffers.GENMEDIUM_BYTES)));
        bh.consume(parse(new ByteArrayInputStream(Buffers.GENMEDIUM_TINY)));
        bh.consume(parse(new ByteArrayInputStream(Buffers.GENMEDIUM_BYTES)));
        
    }
    
    @Benchmark
    public void read_combined_Reader(Blackhole bh) throws Exception {
        bh.consume(read(new CharArrayReader(Buffers.CHR_GENMEDIUM_BYTES)));
        bh.consume(read(new CharArrayReader(Buffers.CHR_GENMEDIUM_TINY)));
        bh.consume(read(new CharArrayReader(Buffers.CHR_GENMEDIUM_BYTES)));
        bh.consume(read(new CharArrayReader(Buffers.CHR_GENMEDIUM_TINY)));
        bh.consume(read(new CharArrayReader(Buffers.CHR_GENMEDIUM_BYTES)));
        
    }
    
    @Benchmark
    public void read_combined(Blackhole bh) throws Exception {
        bh.consume(read(new ByteArrayInputStream(Buffers.GENMEDIUM_BYTES)));
        bh.consume(read(new ByteArrayInputStream(Buffers.GENMEDIUM_TINY)));
        bh.consume(read(new ByteArrayInputStream(Buffers.GENMEDIUM_BYTES)));
        bh.consume(read(new ByteArrayInputStream(Buffers.GENMEDIUM_TINY)));
        bh.consume(read(new ByteArrayInputStream(Buffers.GENMEDIUM_BYTES)));
        
    }
    
    
    
    
    @Benchmark
    public void read_genhuge(Blackhole bh) throws Exception {
        bh.consume(read(new ByteArrayInputStream(Buffers.GENHUGE_BYTES)));

    }
    
    @Benchmark
    public void read_genmedium(Blackhole bh) throws Exception {
        bh.consume(read(new ByteArrayInputStream(Buffers.GENMEDIUM_BYTES)));

    }
    
    @Benchmark
    public void read_tiny(Blackhole bh) throws Exception {
        bh.consume(read(new ByteArrayInputStream(Buffers.GENMEDIUM_TINY)));

    }
    
   
    @Benchmark
    public void read_genhuge_Reader(Blackhole bh) throws Exception {
        bh.consume(read(new CharArrayReader(Buffers.CHR_GENHUGE_BYTES)));

    }
    
    @Benchmark
    public void read_genmedium_Reader(Blackhole bh) throws Exception {
        bh.consume(read(new CharArrayReader(Buffers.CHR_GENMEDIUM_BYTES)));

    }
    
    @Benchmark
    public void read_tiny_Reader(Blackhole bh) throws Exception {
        bh.consume(read(new CharArrayReader(Buffers.CHR_GENMEDIUM_TINY)));

    }
}
