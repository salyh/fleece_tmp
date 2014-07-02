package org.apache.fleece.core.jmh.benchmark;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

public class Buffers {

    public static final byte[] ACTION_LABEL_BYTES = readBytes( "/bench/actionLabel.json" );
    public static final byte[] CITM_CATALOG_BYTES = readBytes( "/bench/citm_catalog.json" );
    public static final byte[] MEDIUM_BYTES = readBytes( "/bench/medium.json" );
    public static final byte[] MENU_BYTES = readBytes( "/bench/menu.json" );
    public static final byte[] SGML_BYTES = readBytes( "/bench/sgml.json" );
    public static final byte[] SMALL_BYTES = readBytes( "/bench/small.json" );
    public static final byte[] WEBXML_BYTES = readBytes( "/bench/webxml.json" );
    public static final byte[] WIDGET_BYTES = readBytes( "/bench/widget.json" );

    public static final String STR_ACTION_LABEL_BYTES = readStr( "/bench/actionLabel.json" );
    public static final String STR_CITM_CATALOG_BYTES = readStr( "/bench/citm_catalog.json" );
    public static final String STR_MEDIUM_BYTES = readStr( "/bench/medium.json" );
    public static final String STR_MENU_BYTES = readStr( "/bench/menu.json" );
    public static final String STR_SGML_BYTES = readStr( "/bench/sgml.json" );
    public static final String  STR_SMALL_BYTES = readStr( "/bench/small.json" );
    public static final String STR_WEBXML_BYTES = readStr( "/bench/webxml.json" );
    public static final String STR_WIDGET_BYTES = readStr( "/bench/widget.json" );


    public static final char[] CHR_ACTION_LABEL_BYTES = readChars ( "/bench/actionLabel.json" );
    public static final char[] CHR_CITM_CATALOG_BYTES = readChars ( "/bench/citm_catalog.json" );
    public static final char[] CHR_MEDIUM_BYTES = readChars ( "/bench/medium.json" );
    public static final char[] CHR_MENU_BYTES = readChars ( "/bench/menu.json" );
    public static final char[] CHR_SGML_BYTES = readChars ( "/bench/sgml.json" );
    public static final char[] CHR_SMALL_BYTES = readChars ( "/bench/small.json" );
    public static final char[] CHR_WEBXML_BYTES = readChars ( "/bench/webxml.json" );
    public static final char[] CHR_WIDGET_BYTES = readChars ( "/bench/widget.json" );

    private static byte[] readBytes(String path) {
            try {
                return IOUtils.toByteArray(Buffers.class.getResourceAsStream(path));
            } catch (IOException e) {
                return null;
            }
            //return IO.read (path ).getBytes ( StandardCharsets.UTF_8 );

    }


    private static String readStr(String path) {
        try {
            return IOUtils.toString(Buffers.class.getResourceAsStream(path));
        } catch (IOException e) {
            return null;
        }
    }



    private static char [] readChars(String path) {
        try {
            return IOUtils.toCharArray(Buffers.class.getResourceAsStream(path), Charset.forName("UTF-8"));
        } catch (IOException e) {
            return null;
        }
    }
}
