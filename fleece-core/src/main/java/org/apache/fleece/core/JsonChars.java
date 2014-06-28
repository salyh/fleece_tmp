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

public interface JsonChars {
    char EOF = Character.MIN_VALUE;

    char START_OBJECT_CHAR = '{';
    char END_OBJECT_CHAR = '}';
    char START_ARRAY_CHAR = '[';
    char END_ARRAY_CHAR = ']';
    char EOL = '\n';
    char COMMA = ',';
    char SPACE = ' ';
    char KEY_SEPARATOR = ':';
    char TRUE_T = 't';
    char TRUE_R = 'r';
    char TRUE_U = 'u';
    char TRUE_E = 'e';
    char FALSE_F = 'f';
    char FALSE_A = 'a';
    char FALSE_L = 'l';
    char FALSE_S = 's';
    char FALSE_E = 'e';
    char NULL_N = 'n';
    char NULL_U = 'u';
    char NULL_L = 'l';
    char QUOTE = '"';
    char ZERO = '0';
    char NINE = '9';
    char DOT = '.';
    char MINUS = '-';
    char PLUS = '+';
    char EXP_LOWERCASE = 'e';
    char EXP_UPPERCASE = 'E';
    char ESCAPE_CHAR = '\\';
    
    char TAB = '\t';
    char BACKSPACE = '\b';
    char FORMFEED = '\f';
    char CR = '\r';
    
    char U_007f = '\u007f';
    char U_0080 = '\u0080';
    char U_00a0 = '\u00a0';
    char U_2000 = '\u2000';
    char U_2100 = '\u2100';

    String NULL = "null";
    String ESCAPED_EOL = String.valueOf(ESCAPE_CHAR+EOL);
    String ESCAPED_TAB  = String.valueOf(ESCAPE_CHAR+TAB);
    String ESCAPED_BACKSPACE = String.valueOf(ESCAPE_CHAR+BACKSPACE);
    String ESCAPED_FORMFEED = String.valueOf(ESCAPE_CHAR+FORMFEED);
    String ESCAPED_CR = String.valueOf(ESCAPE_CHAR+CR);
    String ESCAPED_QUOTE = String.valueOf(ESCAPE_CHAR+QUOTE);
}
