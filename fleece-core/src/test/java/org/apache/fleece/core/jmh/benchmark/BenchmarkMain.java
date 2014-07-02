package org.apache.fleece.core.jmh.benchmark;



import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
//@BenchmarkMode(Mode.AverageTime)
//@OutputTimeUnit(TimeUnit.SECONDS)
public class BenchmarkMain {

   
    
    public static void main(String[] args) throws RunnerException {
        
        Options opt = new OptionsBuilder()
                .include(".*" + BenchmarkMain.class.getSimpleName() + ".*")
                .forks(1)
                .warmupIterations(3)
                .measurementIterations(5)
                .build();

        new Runner(opt).run();
    }

    private final JsonReaderFactory readerFactory = Json.createReaderFactory(null);
    
    private Object parse(InputStream stream) throws Exception {
        JsonReader reader = readerFactory.createReader(stream);
        return reader.readObject();
    }
    
    @Benchmark
    public void actionLabel(Blackhole bh) throws Exception {
        bh.consume(parse(new ByteArrayInputStream(Buffers.ACTION_LABEL_BYTES)));
    }
    
}
