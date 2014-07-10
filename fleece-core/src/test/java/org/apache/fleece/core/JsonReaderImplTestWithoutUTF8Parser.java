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
package org.apache.fleece.core;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.stream.JsonParser;

import org.junit.Test;

public class JsonReaderImplTestWithoutUTF8Parser extends JsonReaderImplTest {

    @Override
    @SuppressWarnings("unchecked")
    protected Map<String, ?> getFactoryConfig() {
        final Map<String, String> config = new HashMap<String, String>();
        config.put(JsonParserFactoryImpl.DISABLE_UTF8_PARSER, "true");
        return config;
    }

    @Override
    @Test
    public void checkParserClass() {
        final JsonParser parser = Json.createParserFactory(getFactoryConfig()).createParser(new ByteArrayInputStream(new byte[] {}),
                StandardCharsets.UTF_8);
        assertTrue(parser instanceof JsonCharBufferStreamParser);
    }

}
