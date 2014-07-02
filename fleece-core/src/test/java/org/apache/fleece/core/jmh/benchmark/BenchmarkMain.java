package org.apache.fleece.core.jmh.benchmark;

import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonReader;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

//@State(Scope.Thread)
//@BenchmarkMode(Mode.AverageTime)
//@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class BenchmarkMain {

    private static InputStream in;
    
    public static void main(String[] args) throws RunnerException {
        
       in = Thread.currentThread().getContextClassLoader().getResourceAsStream("json/citm_catalog.json");
        
        Options opt = new OptionsBuilder()
                //.include(".*" + BenchmarkMain.class.getSimpleName() + ".*")
                .include(".*")
                .forks(1)
                .warmupIterations(3)
                .measurementIterations(5)
                .build();

        new Runner(opt).run();
    }

    
    @Benchmark
    public void citmParse() {
        final JsonReader reader = Json.createReader(in);
        reader.readObject();
        
    }
}
