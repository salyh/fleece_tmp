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
import org.apache.fleece.core.JsonReaderImpl;

public class CharBufferStreamparserBenchmark extends BenchmarkRawStreamParser {

    @Override
    protected Object parse(final InputStream stream) throws Exception {
        final JsonParser parser = new JsonCharBufferStreamParser(stream, StandardCharsets.UTF_8, 8192,
                BufferStrategy.BY_INSTANCE.newProvider(8192));

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

    @Override
    protected Object parse(final Reader reader) throws Exception {
        final JsonParser parser = new JsonCharBufferStreamParser(reader, 8192, BufferStrategy.BY_INSTANCE.newProvider(8192));

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
    
    @Override
    protected Object read(final InputStream stream) throws Exception {
        final EscapedStringAwareJsonParser parser = new JsonCharBufferStreamParser(stream, StandardCharsets.UTF_8, 8192, BufferStrategy.BY_INSTANCE.newProvider(8192));
        JsonReader jreader = new JsonReaderImpl(parser);
        JsonStructure js = jreader.read();
        jreader.close();
        return js;
    }

    @Override
    protected Object read(final Reader reader) throws Exception {
        final EscapedStringAwareJsonParser parser = new JsonCharBufferStreamParser(reader,8192, BufferStrategy.BY_INSTANCE.newProvider(8192));
        JsonReader jreader = new JsonReaderImpl(parser);
        JsonStructure js = jreader.read();
        jreader.close();
        return js;
    }

}
