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

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public final class Strings_Optimized implements JsonChars {
    private static final BufferCache<StringBuilder> BUFFER_CACHE = new BufferCache<StringBuilder>(Integer.getInteger(
            "org.apache.fleece.default-string-builder", 1024)) {
        @Override
        protected StringBuilder newValue(final int defaultSize) {
            return new StringBuilder(defaultSize);
        }
    };

    private static HashMap<Character, String> unicodeCache = new HashMap<Character, String>(Integer.getInteger(
            "org.apache.fleece.default-unicode-cache-size", 1024));

    private static final String UNICODE_PREFIX = "\\u";
    private static final String UNICODE_PREFIX_HELPER = "000";

    public static char asEscapedChar(final char current) {
        switch (current) {
        case 'r':
            return CR;
        case 't':
            return TAB;
        case 'b':
            return BACKSPACE;
        case 'f':
            return FORMFEED;
        case 'n':
            return EOL;

            // '"' and '\"' are identical, both are '\u0022', so this case is
            // not neccessary
            // case '"':
            // return '\"';
        }
        return current;
    }

    private static int cmis = 0;
    private static int chit = 0;

    public static String escape(final String value) {
        final StringBuilder builder = BUFFER_CACHE.getCache();
        try {
            for (int i = 0; i < value.length(); i++) {
                final char c = value.charAt(i);
                switch (c) {
                case QUOTE:
                    builder.append(ESCAPED_QUOTE);
                    break;
                case ESCAPE_CHAR:
                    builder.append(ESCAPE_CHAR).append(c);
                    break;
                default:
                    if (c < SPACE) { // we could do a single switch but actually
                                     // we should rarely enter this if so no
                                     // need to pay it
                        switch (c) {
                        case EOL:
                            builder.append(ESCAPED_EOL);
                            break;
                        case CR:
                            builder.append(ESCAPED_CR);
                            break;
                        case TAB:
                            builder.append(ESCAPED_TAB);
                            break;
                        case BACKSPACE:
                            builder.append(ESCAPED_BACKSPACE);
                            break;
                        case FORMFEED:
                            builder.append(ESCAPED_FORMFEED);
                            break;
                        default:
                            builder.append(toUnicode1(c));
                        }
                    } else if ((c >= '\u0080' && c < '\u00a0') || (c >= '\u2000' && c < '\u2100')) {
                        builder.append(toUnicode1(c));
                    } else {
                        builder.append(c);
                    }
                }
            }
            final String s = builder.toString();
            
            return s;
        } finally {
            builder.setLength(0);
            BUFFER_CACHE.release(builder);
        }

    }

    private static String toUnicode1(final char c) {

        String unicodeRepresentation = unicodeCache.get(c);

        if (unicodeRepresentation == null) {
            cmis++;
            final String hex = UNICODE_PREFIX_HELPER + Integer.toHexString(c);
            unicodeRepresentation = UNICODE_PREFIX + hex.substring(hex.length() - 4);
            // unicodeCache.putIfAbsent(c, unicodeRepresentation);
            unicodeCache.put(c, unicodeRepresentation);
            
            return unicodeRepresentation;
           
        } else {
            chit++;
            
            return unicodeRepresentation;
        }
        
    }

    private Strings_Optimized() {
        // no-op
    }
}