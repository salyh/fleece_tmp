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
package org.apache.fleece.mapper;

import junit.framework.AssertionFailedError;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MapperTest {
    private static final String BIG_OBJECT_STR = "{" +
            "\"name\":\"the string\"," +
            "\"integer\":56," +
            "\"longnumber\":118," +
            "\"bool\":true," +
            "\"nested\":{" +
            "\"name\":\"another value\"," +
            "\"integer\":97," +
            "\"longnumber\":34" +
            "}," +
            "\"array\":[" +
            "{" +
            "\"name\":\"a1\"," +
            "\"integer\":1," +
            "\"longnumber\":2" +
            "}," +
            "{" +
            "\"name\":\"a2\"," +
            "\"integer\":3," +
            "\"longnumber\":4" +
            "}" +
            "]," +
            "\"list\":[" +
            "{" +
            "\"name\":\"a3\"," +
            "\"integer\":5," +
            "\"longnumber\":6" +
            "}," +
            "{" +
            "\"name\":\"a4\"," +
            "\"integer\":7," +
            "\"longnumber\":8" +
            "}" +
            "]," +
            "\"primitives\":[1,2,3,4,5]," +
            "\"collectionWrapper\":[1,2,3,4,5]," +
            "\"map\":{\"uno\":\"true\",\"duos\":false}" +
            "}";

    @Test
    public void writeEmptyObject() {
        final StringWriter writer = new StringWriter();
        new MapperBuilder().build().writeObject(null, writer);
        assertEquals("{}", writer.toString());
    }

    @Test
    public void readEmptyObject() {
        final TheObject object = new MapperBuilder().build().readObject(new ByteArrayInputStream("{}".getBytes()), TheObject.class);
        assertNotNull(object);
        assertNull(object.name);
    }

    @Test
    public void readEmptyArray() {
        final TheObject[] object = new MapperBuilder().build().readArray(new ByteArrayInputStream("[]".getBytes()), TheObject.class);
        assertNotNull(object);
        assertEquals(0, object.length);
    }

    @Test
    public void writeMap() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new MapperBuilder().build().writeObject(new LinkedHashMap<String, Integer>() {{
            put("a", 1);
            put("b", 2);
        }}, baos);
        assertEquals("{\"a\":1,\"b\":2}", new String(baos.toByteArray()));
    }

    @Test
    public void writeObject() {
        final TheObject instance = new MapperBuilder().build().readObject(new ByteArrayInputStream(BIG_OBJECT_STR.getBytes()), TheObject.class); // suppose reader writes but this is tested
        final StringWriter writer = new StringWriter();
        new MapperBuilder().build().writeObject(instance, writer);
        final String serialized = writer.toString();
        assertTrue(serialized.contains("\"primitives\":[1,2,3,4,5]"));
        assertTrue(serialized.contains("\"collectionWrapper\":[1,2,3,4,5]"));
        assertTrue(serialized.contains("\"bool\":true"));
        assertTrue(serialized.contains("\"map\":{\"duos\":false,\"uno\":true}"));
    }

    @Test
    public void writeArray() {
        { // integer
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            new MapperBuilder().build().writeArray(new Integer[]{1, 2}, baos);
            assertEquals("[1,2]", new String(baos.toByteArray()));
        }
        { // object
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            new MapperBuilder().build().writeArray(new Pair[]{new Pair(1, "a"), new Pair(2, "b")}, baos);
            try {
                assertEquals("[{\"s\":\"a\",\"i\":1},{\"s\":\"b\",\"i\":2}]", new String(baos.toByteArray()));
            } catch (final AssertionFailedError afe) { // a bit lazy but to avoid field ordering on java > 6
                assertEquals("[{\"i\":1,\"s\":\"a\"},{\"i\":2,\"s\":\"b\"}]", new String(baos.toByteArray()));
            }
        }
    }

    @Test
    public void readObject() {
        final TheObject object = new MapperBuilder().build().readObject(new ByteArrayInputStream(BIG_OBJECT_STR.getBytes()), TheObject.class);

        assertNotNull(object);
        assertEquals("the string", object.name);
        assertEquals(56, object.integer);
        assertEquals(118, object.longnumber);
        assertTrue(object.bool);
        assertEquals("another value", object.nested.name);
        assertEquals(97, object.nested.integer);
        assertEquals(34, object.nested.longnumber);
        assertFalse(object.nested.bool);
        assertNotNull(object.array);
        assertEquals(2, object.array.length);
        assertEquals("a1", object.array[0].name);
        assertEquals(1, object.array[0].integer);
        assertEquals(2, object.array[0].longnumber);
        assertEquals("a2", object.array[1].name);
        assertEquals(3, object.array[1].integer);
        assertEquals(4, object.array[1].longnumber);
        assertEquals("a3", object.list.get(0).name);
        assertEquals(5, object.list.get(0).integer);
        assertEquals(6, object.list.get(0).longnumber);
        assertEquals("a4", object.list.get(1).name);
        assertEquals(7, object.list.get(1).integer);
        assertEquals(8, object.list.get(1).longnumber);
        assertEquals(5, object.primitives.length);
        for (int i = 0; i < object.primitives.length; i++) {
            assertEquals(i + 1, object.primitives[i]);
        }
        assertNotNull(object.collectionWrapper);
        assertEquals(5, object.collectionWrapper.size());
        for (int i = 0; i < object.collectionWrapper.size(); i++) {
            assertEquals(i + 1, object.collectionWrapper.get(i).intValue());
        }
        assertNotNull(object.map);
        assertEquals(2, object.map.size());
        assertTrue(object.map.containsKey("uno"));
        assertTrue(object.map.containsKey("duos"));
        assertTrue(object.map.get("uno"));
        assertFalse(object.map.get("duos"));
    }

    @Test
    public void readArray() {
        final TheObject[] object = new MapperBuilder().build().readArray(new ByteArrayInputStream(("[" +
                "{" +
                "\"name\":\"a3\"," +
                "\"integer\":5," +
                "\"longnumber\":6" +
                "}," +
                "{" +
                "\"name\":\"a4\"," +
                "\"integer\":7," +
                "\"longnumber\":8" +
                "}" +
                "]").getBytes()), TheObject.class);
        assertNotNull(object);
        assertEquals(2, object.length);
        assertEquals("a3", object[0].name);
        assertEquals(5, object[0].integer);
        assertEquals(6, object[0].longnumber);
        assertEquals("a4", object[1].name);
        assertEquals(7, object[1].integer);
        assertEquals(8, object[1].longnumber);
    }

    @Test
    public void converters() {
        final String json = "{\"s\":\"eceelf\"}";
        final Converted v = new MapperBuilder().build().readObject(new ByteArrayInputStream(json.getBytes()), Converted.class);
        assertEquals("fleece", v.getS());
        final StringWriter stream = new StringWriter();
        new MapperBuilder().build().writeObject(v, stream);
        assertEquals(json, stream.toString());
    }

    public static class TheObject {
        private String name;
        private int integer;
        private long longnumber;
        private boolean bool;
        private TheObject nested;
        private TheObject[] array;
        private List<TheObject> list;
        private int[] primitives;
        private List<Integer> collectionWrapper;
        private Map<String, Boolean> map;

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public int getInteger() {
            return integer;
        }

        public void setInteger(final int integer) {
            this.integer = integer;
        }

        public long getLongnumber() {
            return longnumber;
        }

        public void setLongnumber(final long longnumber) {
            this.longnumber = longnumber;
        }

        public boolean isBool() {
            return bool;
        }

        public void setBool(final boolean bool) {
            this.bool = bool;
        }

        public TheObject getNested() {
            return nested;
        }

        public void setNested(final TheObject nested) {
            this.nested = nested;
        }

        public TheObject[] getArray() {
            return array;
        }

        public void setArray(final TheObject[] array) {
            this.array = array;
        }

        public List<TheObject> getList() {
            return list;
        }

        public void setList(final List<TheObject> list) {
            this.list = list;
        }

        public int[] getPrimitives() {
            return primitives;
        }

        public void setPrimitives(final int[] primitives) {
            this.primitives = primitives;
        }

        public List<Integer> getCollectionWrapper() {
            return collectionWrapper;
        }

        public void setCollectionWrapper(final List<Integer> collectionWrapper) {
            this.collectionWrapper = collectionWrapper;
        }

        public Map<String, Boolean> getMap() {
            return map;
        }

        public void setMap(final Map<String, Boolean> map) {
            this.map = map;
        }
    }

    public static class Pair {
        private final int i;
        private final String s;

        public Pair(final int i, final String s) {
            this.i = i;
            this.s = s;
        }

        public int getI() {
            return i;
        }

        public String getS() {
            return s;
        }
    }

    public static class Converted {
        private String s;

        @FleeceConverter(ReverseConverter.class)
        public String getS() {
            return s;
        }

        @FleeceConverter(ReverseConverter.class)
        public void setS(String v) {
            s = v;
        }
    }

    public static class ReverseConverter implements Converter<String> {
        @Override
        public String toString(final String instance) {
            return new StringBuilder(instance).reverse().toString();
        }

        @Override
        public String fromString(final String text) {
            return toString(text);
        }
    }
}
