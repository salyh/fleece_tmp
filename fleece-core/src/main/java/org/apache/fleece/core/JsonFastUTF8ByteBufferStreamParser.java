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

import java.io.IOException;
import java.io.InputStream;

import javax.json.stream.JsonParsingException;

public class JsonFastUTF8ByteBufferStreamParser extends JsonBaseStreamParser {

    //TODO implement BufferStrategy.BufferProvider for byte[]
    private final byte[] buffer0 = new byte[Integer.getInteger("org.apache.fleece.default-char-buffer", 8192) * 2];
    private final InputStream in;
    //TODO implement BufferStrategy.BufferProvider for byte[]
    private final BufferStrategy.BufferProvider provider;
    private int pointer = -1;
    private int avail;
    private char mark;
    private boolean reset;
    private int lowSurrogate = -1;

    //We don't use CharsetDecoder because it's synchronized and slow
    //This parser will not parse UTF-16 and UTF-32 or other charsets than UTF-8
    public JsonFastUTF8ByteBufferStreamParser(final InputStream stream, final int maxStringLength,
            final BufferStrategy.BufferProvider bufferProvider) {
        super(maxStringLength);
        in = stream;
        this.provider = bufferProvider;

    }

    protected void validateStartByte(final int start) {
        if ((start & 0xff) > 127 && (start & 0xff) < 192) {
            throw new JsonParsingException("UTF-8 start byte " + Integer.toHexString((start & 0xff)) + " not valid", createLocation());
        }
    }

    protected void validateFollowUpByte(final int follow) {
        if ((follow & 0xff) >= 192) {
            throw new JsonParsingException("UTF-8 follow-up byte " + Integer.toHexString(follow & 0xff) + " not valid", createLocation());
        }
    }

    protected char bytesToChar() throws JsonParsingException, IOException {

        if (lowSurrogate > -1) {
            final char savedLowSurrogate = (char) lowSurrogate;
            lowSurrogate = -1;
            return savedLowSurrogate;
        }

        final int b0 = readNextByte();

        validateStartByte(b0);

        final int b0u = b0 & 0XFF; //make unsigned

        if (b0u < 0) {
            throw new JsonParsingException("UTF-8 first byte " + Integer.toHexString(b0u) + " not valid", createLocation());
        }

        if (b0u >= 0 && b0u <= 127) {

            //single byte per char, ASCII, BMP
            return (char) (b0);

        } else if (b0u > 127 && b0u <= 193) {
            throw new JsonParsingException("UTF-8 first byte " + Integer.toHexString(b0u) + " not valid", createLocation());

        } else if (b0u >= 194 && b0u <= 223) {

            //two byte per char (BPM)
            final int b1 = readNextByte();

            //validate second byte (BMP)
            validateFollowUpByte(b1);

            return (char) (((b0 << 6) ^ b1) ^ 0x0f80);

        } else if (b0u >= 224 && b0u <= 239) {

            //three bytes per char (BPM)
            final int b1 = readNextByte();
            final int b2 = readNextByte();

            //validate second and third byte
            validateFollowUpByte(b1);
            validateFollowUpByte(b2);

            return (char) (((b0 << 12) ^ (b1 << 6) ^ b2) ^ 0x1f80);

        } else if (b0u >= 240 && b0u <= 244) {
            //four byte per two chars (beyond BMP)
            final int b1 = readNextByte();
            final int b2 = readNextByte();
            final int b3 = readNextByte();

            //validate second, third and fourth byte
            validateFollowUpByte(b1);
            validateFollowUpByte(b2);
            validateFollowUpByte(b3);

            final int codepoint = ((b0 & 0x07) << 18) | ((b1 & 0x3f) << 12) | ((b2 & 0x3f) << 06) | (b3 & 0x3f);

            final int highSurrogate = ((codepoint >> 0xA) + 0xD7C0);

            if (!Character.isHighSurrogate((char) highSurrogate)) {
                throw new JsonParsingException("UTF-8 codepoint " + Integer.toHexString(codepoint)
                        + " not valid, expected high surrogate but got " + Integer.toHexString(highSurrogate), createLocation());
            }

            lowSurrogate = ((codepoint & 0x3FF) + 0xDC00);

            if (!Character.isLowSurrogate((char) lowSurrogate)) {
                throw new JsonParsingException("UTF-8 codepoint " + Integer.toHexString(codepoint)
                        + " not valid, expected low surrogate but got " + Integer.toHexString(lowSurrogate), createLocation());
            }

            return (char) highSurrogate;

        } else if (b0u <= 255) {
            throw new JsonParsingException("UTF-8 byte " + Integer.toHexString(b0u) + " not valid", createLocation());
        }

        throw new JsonParsingException("UTF-8 byte " + Integer.toHexString(b0u) + " out of range", createLocation());

    }

    @Override
    protected char readNextChar() throws IOException {

        if (reset) {
            reset = false;
            return mark;
        }

        mark = bytesToChar();
        return mark;

    }

    protected byte readNextByte() throws IOException {

        if (buffer0.length == 0) {
            throw new IllegalArgumentException("buffer length must be greater than zero");
        }

        if (avail <= 0) {

            avail = in.read(buffer0, 0, buffer0.length);

            pointer = -1;

            if (avail <= 0) {
                throw new IOException("EOF");
            }

        }

        pointer++;
        avail--;
        return buffer0[pointer];

    }

    @Override
    protected void mark() {
        //no-op

    }

    @Override
    protected void reset() {
        reset = true;
    }

    @Override
    protected void closeUnderlyingSource() throws IOException {

        //TODO release buffer provider
        if (in != null) {
            in.close();
        }

    }
}
