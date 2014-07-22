package org.apache.fleece.core;

import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class JsonParserCurrent extends JsonFastUTF8ByteBufferStreamParser {

    public JsonParserCurrent(Reader reader, int maxStringLength) {
        super(null,1);
        // TODO Auto-generated constructor stub
    }

    public JsonParserCurrent(InputStream stream, int maxStringLength) {
        super(stream, maxStringLength);
        // TODO Auto-generated constructor stub
    }

    public JsonParserCurrent(InputStream in, Charset charset, int maxStringLength) {
        super(in, maxStringLength);
        // TODO Auto-generated constructor stub
    }

}
