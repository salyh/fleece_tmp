package org.apache.fleece.core;

import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ConcurrentMap;

import javax.json.JsonValue;
import javax.json.stream.JsonGenerator;

public class JsonGeneratorFacade<P extends JsonGeneratorImpl<?>> implements
		JsonGenerator {

	private JsonGenerator generatorImpl;

	public JsonGeneratorFacade(final Writer writer,
			final ConcurrentMap<String, String> cache) {
		this(writer, null, false, cache);
	}

	public JsonGeneratorFacade(final Writer writer, final P parent,
			final boolean array, final ConcurrentMap<String, String> cache) {

		generatorImpl = new JsonGeneratorImpl<P>(writer, parent, array, cache);

	}

	
	public JsonGenerator writeStartObject() {
		return generatorImpl=generatorImpl.writeStartObject();
	}

	public JsonGenerator writeStartObject(String name) {
		return generatorImpl=generatorImpl.writeStartObject(name);
	}

	public JsonGenerator writeStartArray() {
		return generatorImpl=generatorImpl.writeStartArray();
	}

	
	public JsonGenerator writeStartArray(String name) {
		return generatorImpl=generatorImpl.writeStartArray(name);
	}

	public JsonGenerator write(String name, JsonValue value) {
		return generatorImpl=generatorImpl.write(name, value);
	}

	public JsonGenerator write(String name, String value) {
		return generatorImpl=generatorImpl.write(name, value);
	}

	public JsonGenerator write(String name, BigInteger value) {
		return generatorImpl=generatorImpl.write(name, value);
	}

	public JsonGenerator write(String name, BigDecimal value) {
		return generatorImpl=generatorImpl.write(name, value);
	}

	public JsonGenerator write(String name, int value) {
		return generatorImpl=generatorImpl.write(name, value);
	}

	public JsonGenerator write(String name, long value) {
		return generatorImpl=generatorImpl.write(name, value);
	}

	public JsonGenerator write(String name, double value) {
		return generatorImpl=generatorImpl.write(name, value);
	}

	public JsonGenerator write(String name, boolean value) {
		return generatorImpl=generatorImpl.write(name, value);
	}

	public JsonGenerator writeNull(String name) {
		return generatorImpl=generatorImpl.writeNull(name);
	}

	public JsonGenerator writeEnd() {
		return generatorImpl=generatorImpl.writeEnd();
	}

	public JsonGenerator write(JsonValue value) {
		return generatorImpl=generatorImpl.write(value);
	}

	public JsonGenerator write(String value) {
		return generatorImpl=generatorImpl.write(value);
	}

	public JsonGenerator write(BigDecimal value) {
		return generatorImpl=generatorImpl.write(value);
	}

	public JsonGenerator write(BigInteger value) {
		return generatorImpl=generatorImpl.write(value);
	}

	public JsonGenerator write(int value) {
		return generatorImpl=generatorImpl.write(value);
	}

	public JsonGenerator write(long value) {
		return generatorImpl=generatorImpl.write(value);
	}

	public JsonGenerator write(double value) {
		return generatorImpl=generatorImpl.write(value);
	}

	public JsonGenerator write(boolean value) {
		return generatorImpl=generatorImpl.write(value);
	}

	public JsonGenerator writeNull() {
		return generatorImpl=generatorImpl.writeNull();
	}

	public void close() {
		generatorImpl.close();
	}

	public void flush() {
		generatorImpl.flush();
	}

	

}
