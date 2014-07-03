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

import static org.apache.fleece.core.Strings.asEscapedChar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.NoSuchElementException;

import javax.json.JsonException;
import javax.json.stream.JsonLocation;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParsingException;

public class JsonSimpleStreamParser_Opt1 implements JsonChars, EscapedStringAwareJsonParser {
    
    /**
     * Benchmark                                            Mode   Samples        Score  Score error    Units
o.a.f.c.j.b.BenchmarkRawStreamParser.actionLabel    thrpt         5    75259,919     2189,018    ops/s
o.a.f.c.j.b.BenchmarkRawStreamParser.citmCatalog    thrpt         5       64,529        1,185    ops/s
o.a.f.c.j.b.BenchmarkRawStreamParser.medium         thrpt         5    35598,657     2924,714    ops/s
o.a.f.c.j.b.BenchmarkRawStreamParser.menu           thrpt         5   144594,629     1431,270    ops/s
o.a.f.c.j.b.BenchmarkRawStreamParser.sgml           thrpt         5    88563,935     1980,304    ops/s
o.a.f.c.j.b.BenchmarkRawStreamParser.webxml         thrpt         5    20035,152      734,584    ops/s
o.a.f.c.j.b.BenchmarkRawStreamParser.widget         thrpt         5    75538,671      529,913    ops/s
     */
  
    
    //private boolean log = false;
    //private final BufferedReader reader;
    private boolean reread;
    private byte unread;
    private  InputStream reader1;
    private final byte[] buffer = new byte[8192];
    private int avail=0;
    private int pointer=0;
    
    private  int maxStringSize;
    

    // current state
    private Event event = null;
    private Event lastEvent = null;
    private int lastSignificantChar = -1;
    //private StringBuilder currentValue = new StringBuilder();
    private char[] currentValue ;
    private int valueLength=0;
    //private String escapedValue = null;

    // location
    private int line = 1;
    private int column = 1;
    private int offset = 0;


    private boolean constructingStringValue = false;
    private boolean withinArray = false;
    private boolean stringValueIsKey = false;

    private int openObjects = 0;
    private int openArrays = 0;
    private boolean escaped=false;

    public JsonSimpleStreamParser_Opt1(final Reader reader, final int maxStringLength) {
        //this.reader = new BufferedReader(reader, Integer.getInteger("org.apache.fleece.default-char-buffer", 8192));

        //this.reader1 = reader;
        
        //this.maxStringSize = maxStringLength < 0 ? 8192 : maxStringLength;
        
       //currentValue =  new char[maxStringSize];
        // System.out.println("maxStringSize: " + maxStringSize);
        // System.out.println("loadedChars length: " + loadedChars.length);
    }

    public JsonSimpleStreamParser_Opt1(final InputStream stream, final int maxStringLength) {
        this.reader1 = stream;
        
        this.maxStringSize = maxStringLength < 0 ? 8192 : maxStringLength;
        
       currentValue =  new char[maxStringSize];
    }

    public JsonSimpleStreamParser_Opt1(final InputStream in, final Charset charset, final int maxStringLength) {
        //this(new InputStreamReader(in, charset), maxStringLength);
    }
    
    
    private void appendValue(char c)
    {
        //currentValue.append(c);
        currentValue[valueLength]=c;
        valueLength++;
    }
    
    private void resetValue()
    {
        valueLength=0;
        //currentValue.setLength(0);
    }
    
    private String getValue()
    {
        //return currentValue.toString();
        return new String(currentValue,0,valueLength);
    }

    @Override
    public boolean hasNext() {
        if(event == null) return true;
        
        return !(openArrays==0 && openObjects==0);
        
    }

    

    private static boolean isAsciiDigit(final char value) {
        return value >= ZERO && value <= NINE;
    }

    public JsonLocationImpl createLocation() {
        return new JsonLocationImpl(line, column, offset);
    }
    
    private boolean ifConstructingStringValueAdd(char c) throws IOException
    {
        if(escaped) {
            
            if(c == 'u')
            {
                char[] tmp = read(4);
                
                //if(log)System.out.println((int)tmp[3]+"/"+(int)tmp[2]+"/"+(int)tmp[1]+"/"+(int)tmp[0]);
                
                int decimal = (((int)tmp[3])-48)*1+(((int)tmp[2])-48)*16+(((int)tmp[1])-48)*256+(((int)tmp[0])-48)*4096;
                c = (char) decimal;
                
            }
            else
            {
                c = asEscapedChar(c);
            }
            
            
            escaped=false;
        }
        
        return ifConstructingStringValueAdd(c, false);
    }
    
    private boolean ifConstructingStringValueAdd(char c, boolean escape)
    {
        if(constructingStringValue) 
        {
            if(valueLength >= maxStringSize) throw new JsonParsingException("max string size reached", createLocation());
            
            appendValue(escape?Strings.asEscapedChar(c):c);
            
        }
        return constructingStringValue;
    }
    
   
    private void unreadOne()
    {
        reread=true;
    }
    
    private void mark()
    {
        
        if(pointer >= buffer.length)
        {
            //??
        }
        else
        unread = buffer[pointer];
    }
    
    
    private char read() throws IOException
    {
        if(reread)
        {
            reread=false;
            return  (char) (unread & 0xFF);
        }
        
        
        if(avail<=0)
        {
            pointer = 0;
            //fill buffer
            avail = reader1.read(buffer, 0, buffer.length);
            
            if(avail <=0 ) throw new NoSuchElementException();
            
        }
        
        //char c = (char) reader.read();
        
        //if(log)System.out.println("reading: "+c+" -> "+((int)c ));

        //if (c == -1) {
            //hasNext = false;
          //  throw new NoSuchElementException();
        //}

       
        
        char c =(char) (buffer[pointer]& 0xFF);
       
        
        offset++;
        column++;
        pointer++;
        avail--;
        
        return c;
    }
    
    private char[] read(int count) throws IOException
    {
        char[] tmp = new char[count];
    
        for(int i=0; i<tmp.length;i++)
        {
            tmp[i] = read();
            
        }
        
        return tmp;
    }
    
    
   
    
  //  private boolean isLastEventAValue()
    //{
        
        //Event.START_ARRAY
        //Event.START_OBJECT
        
        //Event.END_ARRAY
        //Event.END_OBJECT
        
        //Event.KEY_NAME
        
        //** 5 Value Event
        //Event.VALUE_FALSE
        //Event.VALUE_NULL 
        //Event.VALUE_NUMBER
        //Event.VALUE_STRING
        //Event.VALUE_TRUE
        
        //***********************
        //***********************
        //Significant chars (8)
        
        //0 - start doc
        //" - quote
        //, - comma
        
        //: - separator
        //{ - start obj
        //} - end obj
        //[ - start arr
        //] - end arr
        
   
        
        /*
        return lastEvent!=null && (lastEvent == Event.VALUE_FALSE 
                                    || lastEvent == Event.VALUE_NULL 
                                    || lastEvent == Event.VALUE_NUMBER
                                    || lastEvent == Event.VALUE_STRING
                                    || lastEvent == Event.VALUE_TRUE);
    }
    
    private boolean isLastEventAValueOrClosing()
    {
        return isLastEventAValue() || lastEvent == Event.END_ARRAY || lastEvent == Event.END_OBJECT;
    }*/
    
    @Override
    public Event next() {
        
        int dosCount =0;
        lastEvent = event;
        event= null;
        
        resetValue();

        try {
            while (true) {
                char c = read();
             
                switch (c) {
                
                case START_OBJECT_CHAR: {
                    
                    if(ifConstructingStringValueAdd(c)) continue;
                    
                    //if(log)System.out.println(" LASIC "+lastSignificantChar);
                    
                    if(lastSignificantChar == -2 || ( lastSignificantChar != -1 && (char)lastSignificantChar != KEY_SEPARATOR && (char)lastSignificantChar != COMMA && (char)lastSignificantChar != START_ARRAY_CHAR))
                    {
                        throw new JsonParsingException("Unexpected character "+c+ " (last significant was "+lastSignificantChar+")", createLocation());
                    }
                    
                    
                    stringValueIsKey = true;
                    withinArray = false;
                    //if(log)System.out.println(" VAL_IS_KEY");
                    
                    lastSignificantChar = c;
                    openObjects++;
                    event = Event.START_OBJECT;
                    break;

                }
                case END_OBJECT_CHAR:
                    
                    if(ifConstructingStringValueAdd(c)) continue;
                    
                    if(lastSignificantChar >= 0 && (char)lastSignificantChar != START_OBJECT_CHAR && (char)lastSignificantChar != END_ARRAY_CHAR && (char)lastSignificantChar != QUOTE && (char)lastSignificantChar != END_OBJECT_CHAR)
                    {
                        throw new JsonParsingException("Unexpected character "+c+ " (last significant was "+lastSignificantChar+")", createLocation());
                    }
                    
                    
                    if(openObjects == 0) throw new JsonParsingException("Unexpected character "+c, createLocation());
                    
                    
                    
                    lastSignificantChar = c;
                    openObjects--;
                    event = Event.END_OBJECT;
                    break;
                case START_ARRAY_CHAR:
                    
                    if(ifConstructingStringValueAdd(c)) continue;
                    
                    withinArray = true;
                    
                    if(lastSignificantChar == -2 || ( lastSignificantChar != -1 && (char)lastSignificantChar != KEY_SEPARATOR && (char)lastSignificantChar != COMMA && (char)lastSignificantChar != START_ARRAY_CHAR))
                    {
                        throw new JsonParsingException("Unexpected character "+c+ " (last significant was "+lastSignificantChar+")", createLocation());
                    }
                    
                   
                    
                    lastSignificantChar = c;
                    openArrays++;
                    event = Event.START_ARRAY;
                    
                    
                    break;
                case END_ARRAY_CHAR:
                    
                    if(ifConstructingStringValueAdd(c)) continue;
                   
                    withinArray = false;
                  
                    if(lastSignificantChar >= 0 &&(char)lastSignificantChar != START_ARRAY_CHAR && (char)lastSignificantChar != END_ARRAY_CHAR && (char)lastSignificantChar != END_OBJECT_CHAR&& (char)lastSignificantChar != QUOTE)
                    {
                        throw new JsonParsingException("Unexpected character "+c+ " (last significant was "+lastSignificantChar+")", createLocation());
                    }
                    
                    if(openArrays == 0) throw new JsonParsingException("Unexpected character "+c, createLocation());
                    
                    lastSignificantChar = c;
                    openArrays--;
                    
                    event = Event.END_ARRAY;
                    break;
                case EOL: 
                    if(ifConstructingStringValueAdd(c)) throw new JsonParsingException("Unexpected character "+c + " ("+(int)c+")", createLocation());
                    line++;
                    continue; //eol no  allowed within a value
                
                case TAB:
                case CR:
                case SPACE:
                   if(ifConstructingStringValueAdd(c)) { //escaping
                       ////if(log)System.out.println("  ESCAPED");
                       //if(escaped) escaped=false;
                       continue;
                       
                   }else
                   {
                       //dos check
                       if(dosCount >= maxStringSize) throw new JsonParsingException("max string size reached", createLocation());
                       dosCount++;
                   }
                  
                    break;
                case COMMA:
                    if(ifConstructingStringValueAdd(c)) continue;
                    
                    if(lastSignificantChar >= 0 &&(char)lastSignificantChar != QUOTE && (char) lastSignificantChar != END_ARRAY_CHAR && (char)lastSignificantChar != END_OBJECT_CHAR)
                    {
                        throw new JsonParsingException("Unexpected character "+c+ " (last significant was "+lastSignificantChar+")", createLocation());
                    }
                    
                    //if(!isLastEventAValueOrClosing()) throw new JsonParsingException("Unexpected character "+c+ " (lastevent "+lastEvent+")", createLocation());
                    
                    lastSignificantChar = c;
                    
                    stringValueIsKey = true;
                    //if(log)System.out.println(" VAL_IS_KEY");
                    
                    break;
                case KEY_SEPARATOR:
                {
                    if(ifConstructingStringValueAdd(c)) continue;
                   
                    
                    if(lastSignificantChar >= 0 && (char)lastSignificantChar != QUOTE)
                    {
                        throw new JsonParsingException("Unexpected character "+c, createLocation());
                    }
                    
                    
                  
                    
                    lastSignificantChar = c;
                    
                    stringValueIsKey = false;
                    //if(log)System.out.println(" VAL_IS_VALUE");
                    
                    break;
                    
                    
                }
                case QUOTE: //must be escaped within a value
                {
                  
                    if(lastSignificantChar >= 0 &&(char)lastSignificantChar != QUOTE && (char)lastSignificantChar != KEY_SEPARATOR && (char)lastSignificantChar != START_OBJECT_CHAR && (char)lastSignificantChar != START_ARRAY_CHAR&& (char)lastSignificantChar != COMMA)
                    {
                        throw new JsonParsingException("Unexpected character "+c+ " (last significant was "+lastSignificantChar+")", createLocation());
                    }
                    
                    
                    lastSignificantChar = c;
                    
                    if(constructingStringValue)
                    {
                        
                        if(escaped)
                        {
                            if(valueLength >= maxStringSize) throw new JsonParsingException("max string size reached", createLocation());
                            appendValue(QUOTE);
                           
                            escaped = false;
                            continue;
                        }
                        else
                        {
                            
                            
                            if(!withinArray && stringValueIsKey)
                            {
                                event = Event.KEY_NAME;
                                stringValueIsKey = false;
                                //if(log)System.out.println(" VAL_IS_VALUE");
                            }else
                            {
                                
                                if(lastEvent != Event.KEY_NAME && !withinArray)
                                {
                                    throw new JsonParsingException("Unexpected character "+c+" (lastevent "+lastEvent+", comma missing)", createLocation());
                                }
                                
                                
                               //string value end
                                event = Event.VALUE_STRING;
                            }
                            
                           
                            constructingStringValue = false;
                            
                            break;
                        }
                    }
                    else
                    {
                        
                        if(escaped)
                        {
                            throw new JsonParsingException("Unexpected character "+c, createLocation());
                        }
                        
                        //string value start
                        resetValue();
                        constructingStringValue = true;
                        break;
                    }
                    
                }

               
                //non string values
                
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                
                case MINUS:
               
                case FALSE_F:       //false
               
                case TRUE_T:        //true
              
                case NULL_N:        //null  
                 
                    if(ifConstructingStringValueAdd(c)) continue;
                    
                    if(lastSignificantChar >= 0 && lastSignificantChar != KEY_SEPARATOR&& lastSignificantChar != COMMA&& lastSignificantChar != START_ARRAY_CHAR)
                        throw new JsonParsingException("unexpected character "+c, createLocation());
                   
                    lastSignificantChar = -2;
                    
                    resetValue();
                    
                    if(lastSignificantChar != QUOTE)
                    {
                        //probe literals
                        switch(c)
                        {
                            case TRUE_T: 
                                char[] tmpt = read(3);
                                if(tmpt[0] != TRUE_R || tmpt[1] != TRUE_U || tmpt[2] != TRUE_E)
                                    throw new JsonParsingException("Unexpected literal "+c+ new String(tmpt), createLocation());
                                event = Event.VALUE_TRUE;
                                break;
                            case FALSE_F:   
                                char[] tmpf = read(4);
                                if(tmpf[0] != FALSE_A || tmpf[1] != FALSE_L || tmpf[2] != FALSE_S|| tmpf[3] != FALSE_E)
                                    throw new JsonParsingException("Unexpected literal "+c+ new String(tmpf), createLocation());
                                  
                                event = Event.VALUE_FALSE;
                                break;
                            case NULL_N:   
                                char[] tmpn = read(3);
                                if(tmpn[0] != NULL_U || tmpn[1] != NULL_L || tmpn[2] != NULL_L)
                                    throw new JsonParsingException("Unexpected literal "+c+ new String(tmpn), createLocation());
                                event = Event.VALUE_NULL;
                                break;
                                
                                
                            default: //number
                                if(valueLength >= maxStringSize) throw new JsonParsingException("max string size reached", createLocation());
                                appendValue(c);
                           
                                
                                boolean endExpected = false;
                                //boolean zeropassed = c=='0';
                                boolean dotpassed = false;
                                boolean epassed = false;
                                char last = c;
                                int i=-1;
                                
                                while(true)
                                {
                                    i++;
                                    
                                    //reader.mark(10);
                                    mark();
                                    //if(log)System.out.println(" >>>MARKED");
                                    char n = read();
                                    
                                    
                                    
                                    if(n==COMMA || n == END_ARRAY_CHAR || n == END_OBJECT_CHAR)
                                    {
                                        unreadOne();
                                        //if(log)System.out.println(" <<<RESET");
                                        
                                        event = Event.VALUE_NUMBER;
                                        break;
                                    }
                                    
                                    if(endExpected && n != SPACE && n != TAB && n != CR) throw new JsonParsingException("unexpected character "+n, createLocation());
                                    
                                    if(n == EOL)
                                    {
                                        last = n;
                                        continue;
                                    }
                                    
                                    if(n == SPACE || n == TAB || n == CR)
                                    {
                                        endExpected = true;
                                        last = n;
                                        continue;
                                    }
                                    
                                    if(!isNumber(n))throw new JsonParsingException("unexpected character "+n, createLocation());
                                    
                                    //minus only allowed as first char or after e/E
                                    if(n==MINUS && i!=0 && last != EXP_LOWERCASE && last != EXP_UPPERCASE)
                                    {
                                        throw new JsonParsingException("unexpected character "+n, createLocation());
                                    }
                                    
                                    //plus only allowed after e/E
                                    if(n==PLUS && last != EXP_LOWERCASE && last != EXP_UPPERCASE)
                                    {
                                        throw new JsonParsingException("unexpected character "+n, createLocation());
                                    }
                                    
                                    //if(last == '0' && n != DOT && !dotpassed) throw new JsonParsingException("unexpected character "+n, createLocation());
                                    
                                   
                                    
                                    if(n==DOT) {
                                        
                                        if(dotpassed) throw new JsonParsingException("more than one dot", createLocation());
                                        
                                        dotpassed=true;
                                       
                                    }
                                    
                                    if(n==EXP_LOWERCASE || n==EXP_UPPERCASE) {
                                        
                                        if(epassed) throw new JsonParsingException("more than one e/E", createLocation());
                                        
                                        epassed=true;
                                    }
                                  
                                         
                                    if(valueLength >= maxStringSize) throw new JsonParsingException("max string size reached", createLocation());    
                                    appendValue(n);
                                    last = n;
                                    
                                }
                                
                                break;
                                
                        }
                        
                        
                    }
                    else
                    {
                        throw new JsonParsingException("Unexpected character "+c, createLocation());
                    }
                    
                    break;
                
                //escape char
                case ESCAPE_CHAR://must be escaped within a value
                    if(!constructingStringValue)throw new JsonParsingException("Unexpected character "+c, createLocation());
                    
                    if(escaped)
                    {   //if(log)System.out.println(" ESCAPEDESCAPED");
                        
                        if(valueLength >= maxStringSize) throw new JsonParsingException("max string size reached", createLocation());
                        appendValue(ESCAPE_CHAR);
                        
                        escaped = false;
                    }
                    else
                    {   //if(log)System.out.println(" ESCAPECHAR");
                        escaped = true;
                    }
                    
                    break;
              
                //eof
                case EOF:
                    
                    throw new NoSuchElementException();

                default:
                    if(ifConstructingStringValueAdd(c)) continue;
                    lastSignificantChar = -2;
                    throw new JsonParsingException("Unexpected character "+c, createLocation());
                    

                }
                
                if(event != null) {
                    
                    //if(log)System.out.println(" +++ +++ +++ +++ +++ +++"+event+"::"+currentValue);
                    
                    return event;
                
                
                }

            }
        } catch (IOException e) {
            new JsonParsingException("Unexpected IO Excpetion", e, createLocation());
        }

        throw new NoSuchElementException("must not happen");
    }

    
    private boolean isNumber(char c) {
        return isAsciiDigit(c) || c == DOT
                || c == MINUS || c == PLUS
                || c == EXP_LOWERCASE || c == EXP_UPPERCASE;
    }
    
    @Override
    public String getString() {
        if (event == Event.KEY_NAME || event == Event.VALUE_STRING || event == Event.VALUE_NUMBER) {
            return getValue();
        }
        throw new IllegalStateException(event + " doesn't support getString()");
    }

    @Override
    public boolean isIntegralNumber() {

        if (event != Event.VALUE_NUMBER) {
            throw new IllegalStateException(event + " doesn't supportisIntegralNumber()");
        }

        for (int i = 0; i < valueLength; i++) {
            if (!isAsciiDigit(currentValue[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int getInt() {
        if (event != Event.VALUE_NUMBER) {
            throw new IllegalStateException(event + " doesn't supportgetInt()");
        }
        return Integer.parseInt(getValue());
    }

    @Override
    public long getLong() {
        if (event != Event.VALUE_NUMBER) {
            throw new IllegalStateException(event + " doesn't supporgetLong()");
        }
        return Long.parseLong(getValue());
    }

    @Override
    public BigDecimal getBigDecimal() {
        if (event != Event.VALUE_NUMBER) {
            throw new IllegalStateException(event + " doesn't support getBigDecimal()");
        }
        return new BigDecimal(getValue());
    }

    @Override
    public JsonLocation getLocation() {
        return createLocation();
    }

    @Override
    public void close() {

        try {
            reader1.close();
        } catch (final IOException e) {
            throw new JsonException(e.getMessage(), e);
        }
    }

    public static JsonLocation location(final JsonParser parser) {
        if (JsonSimpleStreamParser_Opt1.class.isInstance(parser)) {
            return JsonSimpleStreamParser_Opt1.class.cast(parser).createLocation();
        }
        return new JsonLocationImpl(-1, -1, -1);
    }

    @Override
    public String getEscapedString() {
        return Strings.escape(getValue());
    }

}