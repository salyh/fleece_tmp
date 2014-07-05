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
import org.openjdk.jmh.results.format.ResultFormatType;
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
    
    private static final String REGEX = ".*BufferRecycle.*";
    
    public static void main(String[] args) throws Exception {
    	//run(1,1,2,3);
    	run(1,4,3,4);
    	run(2,4,3,4);
    	//run(2,16,3,5);
    }

    public static void run(int forks ,int threads,int warmupit,int measureit ) throws Exception {

        Options opt = new OptionsBuilder()
            .include(REGEX)
            .forks(forks)
            .warmupIterations(warmupit)
            .measurementIterations(measureit)
            .threads(threads)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.MICROSECONDS)
            .verbosity(VerboseMode.EXTRA)
            //.syncIterations(value)
            .resultFormat(ResultFormatType.TEXT)
            .result(String.format("avg_benchmark_jmh_result_f%d_t%d_w%d_i%d.txt",forks,threads,warmupit,measureit))
            .output(String.format("avg_benchmark_jmh_log_f%d_t%d_w%d_i%d.txt",forks,threads,warmupit,measureit))
            
            .build();

        new Runner(opt).run();
        
        
        System.out.println("-second-");
        
        opt = new OptionsBuilder()
        .include(REGEX)
        .forks(forks)
        .warmupIterations(warmupit)
        .measurementIterations(measureit)
        .threads(threads)
        .mode(Mode.Throughput)
        .timeUnit(TimeUnit.SECONDS)
        .verbosity(VerboseMode.EXTRA)
        //.syncIterations(value)
        .resultFormat(ResultFormatType.TEXT)
        .result(String.format("thr_benchmark_jmh_result_f%d_t%d_w%d_i%d.txt",forks,threads,warmupit,measureit))
        .output(String.format("thr_benchmark_jmh_log_f%d_t%d_w%d_i%d.txt",forks,threads,warmupit,measureit))
        
        .build();

    new Runner(opt).run();
    }

    
}
