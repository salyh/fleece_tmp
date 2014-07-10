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

import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;

public class JsonParserFactoryImpl implements JsonParserFactory, Serializable {
    public static final String BUFFER_STRATEGY = "org.apache.fleece.buffer-strategy";
    public static final String MAX_STRING_LENGTH = "org.apache.fleece.max-string-length";
    public static final String BUFFER_LENGTH = "org.apache.fleece.default-char-buffer";
    public static final String DISABLE_UTF8_PARSER = "org.apache.fleece.disable-fast-utf8-parser";
    public static final int DEFAULT_MAX_SIZE = Integer.getInteger(MAX_STRING_LENGTH, 8192);

    private final Map<String, ?> config;
    private final int maxSize;
    private final BufferStrategy.BufferProvider bufferProvider;

    public JsonParserFactoryImpl(final Map<String, ?> config) {
        this.config = config;

        final int bufferSize = getInt(BUFFER_LENGTH);
        if (bufferSize <= 0) {
            throw new IllegalArgumentException("buffer length must be greater than zero");
        }

        this.maxSize = getInt(MAX_STRING_LENGTH);
        this.bufferProvider = getBufferProvider().newProvider(bufferSize);
    }

    private BufferStrategy getBufferProvider() {
        final Object name = config.get(BUFFER_STRATEGY);
        if (name != null) {
            return BufferStrategy.valueOf(name.toString().toUpperCase(Locale.ENGLISH));
        }
        return BufferStrategy.BY_INSTANCE;
    }

    private int getInt(final String key) {
        final Object maxStringSize = config.get(key);
        if (maxStringSize == null) {
            return DEFAULT_MAX_SIZE;
        } else if (Number.class.isInstance(maxStringSize)) {
            return Number.class.cast(maxStringSize).intValue();
        }
        return Integer.parseInt(maxStringSize.toString());
    }

    private boolean getBoolean(final String key) {
        final Object bool = config.get(key);
        if (bool == null) {
            return false;
        } else if (Boolean.class.isInstance(bool)) {
            return Boolean.class.cast(bool);
        }
        return Boolean.parseBoolean(bool.toString());
    }

    private EscapedStringAwareJsonParser getDefaultJsonParserImpl(final InputStream in) {
        if (!getBoolean(DISABLE_UTF8_PARSER) && Charset.defaultCharset().equals(StandardCharsets.UTF_8)) {
            return new JsonFastUTF8ByteBufferStreamParser(in, maxSize, bufferProvider);
        } else {
            return new JsonCharBufferStreamParser(in, Charset.defaultCharset(), maxSize, bufferProvider);
        }
    }

    private EscapedStringAwareJsonParser getDefaultJsonParserImpl(final InputStream in, final Charset charset) {
        if (!getBoolean(DISABLE_UTF8_PARSER) && StandardCharsets.UTF_8.equals(charset)) {
            return new JsonFastUTF8ByteBufferStreamParser(in, maxSize, bufferProvider);
        } else {
            return new JsonCharBufferStreamParser(in, charset, maxSize, bufferProvider);
        }
    }

    private EscapedStringAwareJsonParser getDefaultJsonParserImpl(final Reader in) {
        return new JsonCharBufferStreamParser(in, maxSize, bufferProvider);
    }

    @Override
    public JsonParser createParser(final Reader reader) {
        return getDefaultJsonParserImpl(reader);
    }

    @Override
    public JsonParser createParser(final InputStream in) {
        return getDefaultJsonParserImpl(in);
    }

    @Override
    public JsonParser createParser(final InputStream in, final Charset charset) {
        return getDefaultJsonParserImpl(in, charset);
    }

    @Override
    public JsonParser createParser(final JsonObject obj) {
        return new JsonInMemoryParser(obj);
    }

    @Override
    public JsonParser createParser(final JsonArray array) {
        return new JsonInMemoryParser(array);
    }

    @Override
    public Map<String, ?> getConfigInUse() {
        return Collections.unmodifiableMap(config);
    }

    public EscapedStringAwareJsonParser createInternalParser(final InputStream in) {
        return getDefaultJsonParserImpl(in);
    }

    public EscapedStringAwareJsonParser createInternalParser(final InputStream in, final Charset charset) {
        return getDefaultJsonParserImpl(in, charset);
    }

    public EscapedStringAwareJsonParser createInternalParser(final Reader reader) {
        return getDefaultJsonParserImpl(reader);
    }
}
