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

import javax.json.JsonException;
import javax.json.stream.JsonLocation;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParsingException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.apache.fleece.core.Strings.asEscapedChar;

public class JsonStreamParser implements JsonChars, EscapedStringAwareJsonParser {
    private static final BufferCache<char[]> BUFFER_CACHE = new BufferCache<char[]>(Integer.getInteger("org.apache.fleece.default-char-buffer", 8192) /*BufferedReader.defaultCharBufferSize*/) {
        @Override
        protected char[] newValue(final int defaultSize) {
            return new char[defaultSize];
        }
    };

    private final Reader reader;
    private final int maxStringSize;

    // lexer state
    private final char[] loadedChars;
    private int availableLength = -1; // to trigger loading at first read() call
    private int currentBufferIdx;

    // current state
    private Event event = null;
    private Event lastEvent = null;
    private String currentValue = null;
    private String escapedValue = null;
    // location
    private int line = 1;
    private int column = 1;
    private int offset = 0;

    private final ValueBuilder valueBuilder = new ValueBuilder();

    public JsonStreamParser(final Reader reader, final int maxStringLength) {
        this.reader = reader;
        this.loadedChars = new char[Integer.getInteger("org.apache.fleece.default-char-buffer", 8192)];
        this.maxStringSize = maxStringLength < 0 ? loadedChars.length : maxStringLength;
        System.out.println("maxStringSize: "+maxStringSize);
        System.out.println("loadedChars length: "+loadedChars.length);
    }

    public JsonStreamParser(final InputStream stream, final int maxStringLength) {
        this(new InputStreamReader(stream), maxStringLength);
    }

    public JsonStreamParser(final InputStream in, final Charset charset, final int maxStringLength) {
        this(new InputStreamReader(in, charset), maxStringLength);
    }

    @Override
    public boolean hasNext() {
        System.out.println("hasNext()");
        
        
        if (event != null) {
            return loadedChars[currentBufferIdx] != EOF;
        }

        try {
            do {
                readUntilEvent();
                System.out.println("    event->"+loadedChars[currentBufferIdx]+"("+(int)loadedChars[currentBufferIdx]+") on "+currentBufferIdx+"/"+offset);
                if (loadedChars[currentBufferIdx] == QUOTE) {
                    System.out.println("    quote detected on "+currentBufferIdx+"/"+offset);
                    valueBuilder.reset(0); // actually offset = 1 but reset increments idx
                    boolean escape = false;
                 
                    while (nextChar() != EOF && loadedChars[currentBufferIdx] != QUOTE && currentBufferIdx < valueBuilder.maxEnd) {
                        
                        System.out.println("    "+loadedChars[currentBufferIdx]);
                        
                        if (loadedChars[currentBufferIdx] == ESCAPE_CHAR) {
                            read();
                            escape = true;
                        }
                        valueBuilder.next();
                        
                    }
                    
                    System.out.println("    valueBuilder.maxEnd "+valueBuilder.maxEnd);
                    System.out.println("    going to read value "+currentValue+" on "+currentBufferIdx+"/"+offset);
                    currentValue = valueBuilder.readValue();
                    System.out.println("    -->-->VALUE is::"+currentValue+" on "+currentBufferIdx+"/"+offset);

                    if (escape) { // this induces an overhead but that's not that often normally
                        final StringBuilder builder = new StringBuilder(currentValue.length());
                        boolean escaped = false;
                        for (final char current : currentValue.toCharArray()) {
                            if (current == ESCAPE_CHAR) {
                                escaped = true;
                                continue;
                            }
                            if (!escaped) {
                                builder.append(current);
                            } else {
                                builder.append(asEscapedChar(current));
                                escaped = false;
                            }
                        }
                        escapedValue = currentValue;
                        currentValue = builder.toString();
                    } else {
                        escapedValue = null;
                    }

                    readUntilEvent(); // we need to check if next char is a ':' to know it is a key
                    if (loadedChars[currentBufferIdx] == KEY_SEPARATOR) {
                        System.out.println("    key_separator detected on "+currentBufferIdx+"/"+offset);
                        event = Event.KEY_NAME;
                    } else {
                        if (loadedChars[currentBufferIdx] != COMMA && loadedChars[currentBufferIdx] != END_OBJECT_CHAR && loadedChars[currentBufferIdx] != END_ARRAY_CHAR) {
                            System.out.println(currentBufferIdx+"/"+offset);
                            System.out.println(lastEvent);
                            System.out.println(new String(Arrays.copyOfRange(loadedChars, Math.max(0, currentBufferIdx-30), currentBufferIdx)));
                            System.out.println(createLocation());
                            System.out.println(new String(loadedChars));
                            throw new JsonParsingException("expecting end of structure or comma but got " + loadedChars[currentBufferIdx], createLocation());
                        }
                        currentBufferIdx--; // we are alredy in place so to avoid offset when calling readUntilEvent() going back
                        event = Event.VALUE_STRING;
                    }
                    return true;
                } else if (loadedChars[currentBufferIdx] == START_OBJECT_CHAR) {
                    event = Event.START_OBJECT;
                    return true;
                } else if (loadedChars[currentBufferIdx] == END_OBJECT_CHAR) {
                    event = Event.END_OBJECT;
                    return true;
                } else if (loadedChars[currentBufferIdx] == START_ARRAY_CHAR) {
                    event = Event.START_ARRAY;
                    return true;
                } else if (loadedChars[currentBufferIdx] == END_ARRAY_CHAR) {
                    event = Event.END_ARRAY;
                    return true;
                } else if (isNumber()) {
                    System.out.println("    number detected");
                    valueBuilder.reset(-1); // reset will increment to check overflow
                    while (nextChar() != EOF && isNumber() && currentBufferIdx < valueBuilder.maxEnd) {
                        valueBuilder.next();
                    }
                    currentValue = valueBuilder.readValue();
                    System.out.println("    number::value::"+currentValue);
                    currentBufferIdx--; // we are alredy in place so to avoid offset when calling readUntilEvent() going back
                    event = Event.VALUE_NUMBER;
                    return true;
                } else if (loadedChars[currentBufferIdx] == TRUE_T) {
                    if (read() != TRUE_R || read() != TRUE_U || read() != TRUE_E) {
                        throw new JsonParsingException("true expected", createLocation());
                    }
                    event = Event.VALUE_TRUE;
                    return true;
                } else if (loadedChars[currentBufferIdx] == FALSE_F) {
                    if (read() != FALSE_A || read() != FALSE_L || read() != FALSE_S || read() != FALSE_E) {
                        throw new JsonParsingException("false expected", createLocation());
                    }
                    event = Event.VALUE_FALSE;
                    return true;
                } else if (loadedChars[currentBufferIdx] == NULL_N) {
                    if (read() != NULL_U || read() != NULL_L || read() != NULL_L) {
                        throw new JsonParsingException("null expected", createLocation());
                    }
                    System.out.println("null event on "+offset);
                    event = Event.VALUE_NULL;
                    return true;
                } else if (loadedChars[currentBufferIdx] == EOF) {
                    return false;
                } else if (loadedChars[currentBufferIdx] == COMMA) {
                    System.out.println("    comma parse on "+currentBufferIdx+"/"+offset);
                    if (event != null && event != Event.KEY_NAME && event != Event.VALUE_STRING && event != Event.VALUE_NUMBER && event != Event.VALUE_TRUE && event != Event.VALUE_FALSE && event != Event.VALUE_NULL) {
                        throw new JsonParsingException("unexpected comma", createLocation());
                    }
                
                }else {
                    throw new JsonParsingException("unexpected character: '" + loadedChars[currentBufferIdx] + "'", createLocation());
                }
            } while (true);
        } catch (final IOException e) {
            throw new JsonParsingException("unknown state", createLocation());
        }
    }

    private StringBuilder savePreviousStringBeforeOverflow(int start, StringBuilder previousParts) {
        System.out.println("save state "+start+"/"+(previousParts==null?"null":""+previousParts.length()));
        final int length = currentBufferIdx - start;
        previousParts = (previousParts == null ? new StringBuilder(length * 2) : previousParts).append(loadedChars, start, length);
        System.out.println("previousParts->"+previousParts);
        return previousParts;
    }

    private boolean isNumber() {
        return isAsciiDigit(loadedChars[currentBufferIdx]) || loadedChars[currentBufferIdx] == DOT || loadedChars[currentBufferIdx] == MINUS || loadedChars[currentBufferIdx] == PLUS || loadedChars[currentBufferIdx] == EXP_LOWERCASE || loadedChars[currentBufferIdx] == EXP_UPPERCASE;
    }

    private static boolean isAsciiDigit(final char value) {
        return value >= ZERO && value <= NINE;
    }

    private void readUntilEvent() throws IOException {
        read();
        skipNotEventChars();
    }

    private void skipNotEventChars() throws IOException {
        int read = 0;
        do {
            final int current = currentBufferIdx;
            while (currentBufferIdx < availableLength) {
                if (loadedChars[currentBufferIdx] > SPACE) {
                    final int diff = currentBufferIdx - current;
                    offset += diff;
                    column += diff;
                    return;
                } else if (loadedChars[currentBufferIdx] == EOL) {
                    line++;
                    column = 0;
                }
                currentBufferIdx++;
            }
            read();
            read++;
        }
        while (loadedChars[currentBufferIdx] != EOF && read < loadedChars.length); // don't accept more space than buffer size to avoid DoS
        
        System.out.println("skipped not event chars: "+read);
        
        if (read == loadedChars.length) {
            //throw new JsonParsingException("Too much spaces (>" + loadedChars.length + ")", createLocation());
        }
    }

    public JsonLocationImpl createLocation() {
        return new JsonLocationImpl(line, column, offset);
    }

    private char read() throws IOException {
        System.out.println("read(), incremented counters from "+offset+"/"+currentBufferIdx);
        offset++;
        column++;
        currentBufferIdx++;
        System.out.println("incremented counters to "+offset+"/"+currentBufferIdx);
        
        if (currentBufferIdx >= availableLength) {
            availableLength = reader.read(loadedChars, 0, loadedChars.length);
            
            
            
            currentBufferIdx = 0;
            System.out.println("    !overflowed, rereaded "+currentBufferIdx+"/"+availableLength);
            if (availableLength <= 0) { // 0 or -1 typically
                loadedChars[0] = EOF;
                offset--;
                column--;
                return EOF;
            }
        }
 
        System.out.println("READ: "+loadedChars[currentBufferIdx]+" ("+(int)loadedChars[currentBufferIdx]+")");
        
        return loadedChars[currentBufferIdx];
    }

   

    @Override
    public Event next() {
        System.out.println("next() ");
        if (event == null) {
            hasNext();
        }
        lastEvent = event;
        event = null;
        System.out.println("next() return "+lastEvent);
        return lastEvent;
    }

    @Override
    public String getString() {
        if (lastEvent == Event.KEY_NAME || lastEvent == Event.VALUE_STRING || lastEvent == Event.VALUE_NUMBER) {
            return currentValue;
        }
        throw new IllegalStateException(event + " doesn't support getString()");
    }

    @Override
    public boolean isIntegralNumber() {
        
        if (lastEvent != Event.VALUE_NUMBER) {
            throw new IllegalStateException(event + " doesn't support isIntegralNumber()");
        }
        
        for (int i = 0; i < currentValue.length(); i++) {
            if (!isAsciiDigit(currentValue.charAt(i))) {
                return false;
            }
        }
        
        return true;
    }

    @Override
    public int getInt() {
        if (lastEvent != Event.VALUE_NUMBER) {
            throw new IllegalStateException(event + " doesn't support getInt()");
        }
        return Integer.parseInt(currentValue);
    }

    @Override
    public long getLong() {
        if (lastEvent != Event.VALUE_NUMBER) {
            throw new IllegalStateException(event + " doesn't support getLong()");
        }
        return Long.parseLong(currentValue);
    }

    @Override
    public BigDecimal getBigDecimal() {
        if (lastEvent != Event.VALUE_NUMBER) {
            throw new IllegalStateException(event + " doesn't support getBigDecimal()");
        }
        return new BigDecimal(currentValue);
    }

    @Override
    public JsonLocation getLocation() {
        return createLocation();
    }

    @Override
    public void close() {
        BUFFER_CACHE.release(loadedChars);
        try {
            reader.close();
        } catch (final IOException e) {
            throw new JsonException(e.getMessage(), e);
        }
    }

    public static JsonLocation location(final JsonParser parser) {
        if (JsonStreamParser.class.isInstance(parser)) {
            return JsonStreamParser.class.cast(parser).createLocation();
        }
        return new JsonLocationImpl(-1, -1, -1);
    }

    @Override
    public String getEscapedString() {
        return escapedValue;
    }

    private class ValueBuilder {
        private int start;
        private int maxEnd;
        private StringBuilder previousParts = null;

        public void next() {
            System.out.println("ValueBuilder: next()");
            if (incr() >= availableLength) { // overflow case
                System.out.println("    next overflow case "+start+"/"+maxEnd);
                previousParts = savePreviousStringBeforeOverflow(start, previousParts);
                start = 0;
                maxEnd = maxStringSize;
            }else
            {
                System.out.println("    next no overflow case "+start+"/"+maxEnd);
            }
        }

        public String readValue() {
            System.out.println("ValueBuilder: readValue()");
            if (loadedChars[currentBufferIdx] == EOF) {
                throw new JsonParsingException("Can't read string", createLocation());
            }

            System.out.println("    currentBufferIdx/start "+currentBufferIdx+"/"+start);
            
            final int length = currentBufferIdx - start;
            if (length >= maxStringSize) {
                throw new JsonParsingException("String too long", createLocation());
            }

            System.out.println("    start/length "+start+"/"+length);
            
            final String currentValue = new String(loadedChars, start, length);
            if (previousParts != null && previousParts.length() > 0) {
                
                System.out.println("    found prev parts: "+previousParts);
                
                return previousParts.append(currentValue).toString();
            }else
            {
                System.out.println("    no prev parts");
            }
            return currentValue;
        }

        public void reset(final int offset) {
            
            System.out.println("ValueBuilder: reset("+offset+")");
            System.out.println("    avail length "+availableLength);
            
            int inc = incr();
            
            System.out.println("    inc "+inc);
            System.out.println("    currentBufferIdx "+currentBufferIdx);
            System.out.println("    old values "+start+"/"+maxEnd);
            
            if (inc < availableLength) { // direct overflow case
                start = currentBufferIdx + offset;
                maxEnd = start + maxStringSize;
                System.out.println("    direct overflow case "+start+"/"+maxEnd);
            } else if (inc>availableLength){
                maxEnd = maxStringSize - (maxEnd - start);
                start = 0;
                System.out.println("    no overflow case "+start+"/"+maxEnd);
            } else
            {
                //inc == availablelength
                maxEnd = maxStringSize - (maxEnd - start);
                start = 0;
                System.out.println("    no overflow case/identical "+start+"/"+maxEnd);
            }
            if (previousParts != null) {
                previousParts.setLength(0);
            }
        }
    }
}
