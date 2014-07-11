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
import java.nio.charset.StandardCharsets;

import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.apache.fleece.core.BufferStrategy;
import org.apache.fleece.core.EscapedStringAwareJsonParser;
import org.apache.fleece.core.JsonCharBufferStreamParser;
import org.apache.fleece.core.JsonFastUTF8ByteBufferStreamParser;
import org.apache.fleece.core.JsonReaderImpl;

public class ManualBenchmark {

    
    static
    {
        //initialize Buffers
        Buffers.init();
    }
    
    public static void main(String[] args) throws Exception{
       
        /*parsec(new ByteArrayInputStream(Buffers.GENHUGE_BYTES));
        parse(new ByteArrayInputStream(Buffers.GENHUGE_BYTES));
      
        
        parsec(new ByteArrayInputStream(Buffers.GENMEDIUM_GIANT));
        parse(new ByteArrayInputStream(Buffers.GENMEDIUM_GIANT));*/

        parsec((ManualBenchmark.class.getResourceAsStream("/bench/small_3kb.json")));
       // parse(ManualBenchmark.class.getResourceAsStream("/bench/gen_monster.json"));
        
       /* long start = System.currentTimeMillis();
        parsec((ManualBenchmark.class.getResourceAsStream("/bench/gen_monster.json")));
        long end = System.currentTimeMillis();
        
        System.out.println("CharBuffer "+(end-start)+" ms.");
        
        start = System.currentTimeMillis();
        JsonParser p =(JsonParser) parse(ManualBenchmark.class.getResourceAsStream("/bench/gen_monster.json"));
        end = System.currentTimeMillis();
        
        System.out.println("FastUtf "+(end-start)+" ms.");*/
  
        
        
    }
    
    
    protected static Object parsec(final InputStream reader) throws Exception {
        final EscapedStringAwareJsonParser parser = new JsonCharBufferStreamParser(reader, StandardCharsets.UTF_8, 8192, BufferStrategy.BY_INSTANCE.newProvider(8192));
        
        while (parser.hasNext()) {
            final Event e = parser.next();

            // use the value
            if (e == null) {
                throw new NullPointerException();
            }
        }
        parser.close();
        return parser;
    }
    
    
    protected static Object parse(final InputStream stream) throws Exception {
        final EscapedStringAwareJsonParser parser = new JsonFastUTF8ByteBufferStreamParser(stream, 8192, BufferStrategy.BY_INSTANCE.newProvider(8192));
        
        while (parser.hasNext()) {
            final Event e = parser.next();

            // use the value
            if (e == null) {
                throw new NullPointerException();
            }

        }

        parser.close();
        return parser;
    }
    
    

}
