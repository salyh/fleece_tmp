package org.apache.fleece.core.jmh.benchmark;


import java.io.IOException;
import java.io.InputStream;
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
    public static final byte[] GENHUGE_BYTES = readBytes( "/bench/gen_huge.json" );
    public static final byte[] GENMEDIUM_BYTES = readBytes( "/bench/gen_medium.json" );
    public static final byte[] UNICODESTXT_BYTES = readBytes( "/bench/unicodes.txt" );
    public static final byte[] UNICODESTXT_BIG_BYTES = readBytes( "/bench/unicodes_big.txt" );

    public static final String STR_ACTION_LABEL_BYTES = readStr( "/bench/actionLabel.json" );
    public static final String STR_CITM_CATALOG_BYTES = readStr( "/bench/citm_catalog.json" );
    public static final String STR_MEDIUM_BYTES = readStr( "/bench/medium.json" );
    public static final String STR_MENU_BYTES = readStr( "/bench/menu.json" );
    public static final String STR_SGML_BYTES = readStr( "/bench/sgml.json" );
    public static final String STR_SMALL_BYTES = readStr( "/bench/small.json" );
    public static final String STR_WEBXML_BYTES = readStr( "/bench/webxml.json" );
    public static final String STR_WIDGET_BYTES = readStr( "/bench/widget.json" );
    public static final String STR_GENHUGE_BYTES = readStr( "/bench/gen_huge.json" );
    public static final String STR_GENMEDIUM_BYTES = readStr( "/bench/gen_medium.json" );
    public static final String STR_UNICODESTXT_BYTES = readStr( "/bench/unicodes.txt" );
    public static final String STR_UNICODESTXT_BIG_BYTES = readStr( "/bench/unicodes_big.txt" );

    public static final char[] CHR_ACTION_LABEL_BYTES = readChars ( "/bench/actionLabel.json" );
    public static final char[] CHR_CITM_CATALOG_BYTES = readChars ( "/bench/citm_catalog.json" );
    public static final char[] CHR_MEDIUM_BYTES = readChars ( "/bench/medium.json" );
    public static final char[] CHR_MENU_BYTES = readChars ( "/bench/menu.json" );
    public static final char[] CHR_SGML_BYTES = readChars ( "/bench/sgml.json" );
    public static final char[] CHR_SMALL_BYTES = readChars ( "/bench/small.json" );
    public static final char[] CHR_WEBXML_BYTES = readChars ( "/bench/webxml.json" );
    public static final char[] CHR_WIDGET_BYTES = readChars ( "/bench/widget.json" );
    public static final char[] CHR_GENHUGE_BYTES = readChars( "/bench/gen_huge.json" );
    public static final char[] CHR_GENMEDIUM_BYTES = readChars( "/bench/gen_medium.json" );
    public static final char[] CHR_UNICODESTXT_BYTES = readChars( "/bench/unicodes.txt" );
    public static final char[] CHR_UNICODESTXT_BIG_BYTES = readChars( "/bench/unicodes_big.txt" );

    private static byte[] readBytes(String path) {
            
            InputStream in = null;
            try {
                return IOUtils.toByteArray(in=Buffers.class.getResourceAsStream(path));
            } catch (IOException e) {
                return null;
            }
            finally
            {
                IOUtils.closeQuietly(in);
            }

    }


    private static String readStr(String path) {
        
        InputStream in = null;
        try {
            return IOUtils.toString(in=Buffers.class.getResourceAsStream(path),Charset.forName("UTF-8"));
        } catch (IOException e) {
            return null;
        }finally
        {
            IOUtils.closeQuietly(in);
        }
    }



    private static char [] readChars(String path) {
        
        InputStream in = null;
        try {
            return IOUtils.toCharArray(in=Buffers.class.getResourceAsStream(path), Charset.forName("UTF-8"));
        } catch (IOException e) {
            return null;
        }finally
        {
            IOUtils.closeQuietly(in);
        }
    }
    
    public static void init()
    {
        
    }
}
