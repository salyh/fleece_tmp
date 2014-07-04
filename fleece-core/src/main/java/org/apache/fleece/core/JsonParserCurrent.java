package org.apache.fleece.core;

import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

public class JsonParserCurrent extends JsonCharBufferStreamParser {

    public JsonParserCurrent(Reader reader, int maxStringLength) {
        super(reader, maxStringLength);
        // TODO Auto-generated constructor stub
    }

    public JsonParserCurrent(InputStream stream, int maxStringLength) {
        super(stream, maxStringLength);
        // TODO Auto-generated constructor stub
    }

    public JsonParserCurrent(InputStream in, Charset charset, int maxStringLength) {
        super(in, charset, maxStringLength);
        // TODO Auto-generated constructor stub
    }

}
