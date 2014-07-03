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

import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class JsonReaderImplTest {
    
    @Before
    public void setup(){
        System.setProperty("org.apache.fleece.default-char-buffer", "8192");
    }
    
    
    @Test
    public void simple() {
        final JsonReader reader = Json.createReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("json/simple.json"));
        assertNotNull(reader);
        final JsonObject object = reader.readObject();
        assertNotNull(object);
        assertEquals(3, object.size());
        assertEquals("b", object.getString("a"));
        assertEquals(4, object.getInt("c"));
        assertThat(object.get("d"), instanceOf(JsonArray.class));
        final JsonArray array = object.getJsonArray("d");
        assertNotNull(array);
        assertEquals(2, array.size());
        assertEquals(1, array.getInt(0));
        assertEquals(-2, array.getInt(1));
        reader.close();
    }
    
    
    @Test
    public void unicode() {
        final JsonReader reader = Json.createReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("json/unicode.json"));
        assertNotNull(reader);
        final JsonObject object = reader.readObject();
        assertNotNull(object);
      
        assertEquals(String.valueOf('\u6565'), object.getString("a"));
        assertEquals("", object.getString("z"));
        assertEquals(String.valueOf('\u0000'), object.getString("c"));
        assertThat(object.get("d"), instanceOf(JsonArray.class));
        final JsonArray array = object.getJsonArray("d");
        assertNotNull(array);
        assertEquals(3, array.size());
        
        assertEquals(-2, array.getInt(0));
        assertEquals(" ", array.getString(1));
        assertEquals("", array.getString(2));
        assertEquals(5, object.size());
        reader.close();
    }
    
    @Test
    public void special() {
        final JsonReader reader = Json.createReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("json/special.json"));
        assertNotNull(reader);
        final JsonObject object = reader.readObject();
        assertNotNull(object);
        /*assertEquals(3, object.size());
        assertEquals("b", object.getString("a"));
        assertEquals(4, object.getInt("c"));
        assertThat(object.get("d"), instanceOf(JsonArray.class));
        final JsonArray array = object.getJsonArray("d");
        assertNotNull(array);
        assertEquals(2, array.size());
        assertEquals(1, array.getInt(0));
        assertEquals(2, array.getInt(1));*/
        reader.close();
    }
    
    
    @Test
    public void citm() {
        final JsonReader reader = Json.createReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("json/citm_catalog.json"));
        assertNotNull(reader);
        final JsonObject object = reader.readObject();
        assertNotNull(object);
        /*assertEquals(3, object.size());
        assertEquals("b", object.getString("a"));
        assertEquals(4, object.getInt("c"));
        assertThat(object.get("d"), instanceOf(JsonArray.class));
        final JsonArray array = object.getJsonArray("d");
        assertNotNull(array);
        assertEquals(2, array.size());
        assertEquals(1, array.getInt(0));
        assertEquals(2, array.getInt(1));*/
        reader.close();
    }
    
    @Test
    public void simplebadbuffersize() {
        System.setProperty("org.apache.fleece.default-char-buffer", "8");
        final JsonReader reader = Json.createReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("json/simple.json"));
        assertNotNull(reader);
        final JsonObject object = reader.readObject();
        assertNotNull(object);
        /*assertEquals(3, object.size());
        assertEquals("b", object.getString("a"));
        assertEquals(4, object.getInt("c"));
        assertThat(object.get("d"), instanceOf(JsonArray.class));
        final JsonArray array = object.getJsonArray("d");
        assertNotNull(array);
        assertEquals(2, array.size());
        assertEquals(1, array.getInt(0));
        assertEquals(2, array.getInt(1));*/
        reader.close();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void emptyzerobuffersize() {
        System.setProperty("org.apache.fleece.default-char-buffer", "0");
        final JsonReader reader = Json.createReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("json/empty.json"));
        assertNotNull(reader);
        final JsonObject object = reader.readObject();
        assertNotNull(object);
        /*assertEquals(3, object.size());
        assertEquals("b", object.getString("a"));
        assertEquals(4, object.getInt("c"));
        assertThat(object.get("d"), instanceOf(JsonArray.class));
        final JsonArray array = object.getJsonArray("d");
        assertNotNull(array);
        assertEquals(2, array.size());
        assertEquals(1, array.getInt(0));
        assertEquals(2, array.getInt(1));*/
        reader.close();
    }
    
    @Test
    public void emptyonebuffersize() {
        System.setProperty("org.apache.fleece.default-char-buffer", "1");
        final JsonReader reader = Json.createReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("json/empty.json"));
        assertNotNull(reader);
        final JsonObject object = reader.readObject();
        assertNotNull(object);
        /*assertEquals(3, object.size());
        assertEquals("b", object.getString("a"));
        assertEquals(4, object.getInt("c"));
        assertThat(object.get("d"), instanceOf(JsonArray.class));
        final JsonArray array = object.getJsonArray("d");
        assertNotNull(array);
        assertEquals(2, array.size());
        assertEquals(1, array.getInt(0));
        assertEquals(2, array.getInt(1));*/
        reader.close();
    }
    
    @Test
    public void verysimpleonebuffersize() {
        
        int[] buffersizes = new int[]{3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,2832,64,128,1024};
        //int[] buffersizes = new int[]{8192};
      
        for(int i=0;i<buffersizes.length;i++)
        {
        System.setProperty("org.apache.fleece.default-char-buffer", String.valueOf(buffersizes[i]));
        final JsonReader reader = Json.createReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("json/verysimple.json"));
        assertNotNull(reader);
        final JsonObject object = reader.readObject();
        assertNotNull(object);
        assertEquals(1, object.size());
        System.out.println(buffersizes[i]+" ---------------- >>>>> "+object.getString("name"));
        assertEquals("s\"mit\"", object.getString("name"));
        reader.close();
        }
    }
}
