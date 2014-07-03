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
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.VerboseMode;


public class BenchmarkMain {
    
    static
    {
        //initialize Buffers
        Buffers.init();
    }

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
            .include(".*Raw.*")
            .forks(2)
            .warmupIterations(3)
            .measurementIterations(3)
            .threads(16)
            .mode(Mode.Throughput)
            .timeUnit(TimeUnit.SECONDS)
            .verbosity(VerboseMode.EXTRA)
            .build();

        new Runner(opt).run();
    }

    
}
