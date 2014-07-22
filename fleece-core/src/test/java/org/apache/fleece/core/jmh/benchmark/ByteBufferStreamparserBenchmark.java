package org.apache.fleece.core.jmh.benchmark;

import java.io.InputStream;
import java.io.Reader;

import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.apache.fleece.core.JsonFastUTF8ByteBufferStreamParser;
import org.apache.fleece.core.JsonCharBufferStreamParser;
import org.apache.fleece.core.JsonParserCurrent;

public class ByteBufferStreamparserBenchmark extends BenchmarkRawStreamParser {

@Override
   protected Object parse(InputStream stream) throws Exception {
        JsonParser parser = new JsonFastUTF8ByteBufferStreamParser(stream, 8193);
        
        while(parser.hasNext())
        {
            Event e = parser.next();
            
            //use the value
            if(e==null) throw new NullPointerException();
            
        }
        
        return parser;
    }
    
@Override
   protected Object parse(Reader reader) throws Exception {
        JsonParser parser = new JsonFastUTF8ByteBufferStreamParser(null, 8193);
        
        while(parser.hasNext())
        {
            Event e = parser.next();
            
          //use the value
            if(e==null) throw new NullPointerException();
        }
        
        return parser;
    }
    
    
}
