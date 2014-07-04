package org.apache.fleece.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;



public class JsonCharBufferStreamParser extends JsonBaseStreamParser {

    private final char[] buffer0 = new char[Integer.getInteger("org.apache.fleece.default-char-buffer", 8192)];
    private final Reader in;
    private int pointer=-1;
    private int avail;
    private char mark;
    private boolean reset;
    //private int availOnMark;
    
    //Test increment buffer sizes
    
    public JsonCharBufferStreamParser(final Reader reader, final int maxStringLength) {
        super(maxStringLength);
        in = reader;
       
    }


    public JsonCharBufferStreamParser(final InputStream stream, final int maxStringLength) {
        this(new InputStreamReader(stream), maxStringLength);
    }

    public JsonCharBufferStreamParser(final InputStream in, final Charset charset, final int maxStringLength) {
        this(new InputStreamReader(in, charset), maxStringLength);
    }

    @Override
    protected char readNextChar() throws IOException {
        
        if(buffer0.length==0) throw new IllegalArgumentException("buffer length must be greater than zero");
        
        if(reset)
        {
            reset=false;
            return mark;
        }
        
        if(avail <= 0)// || avail <= (pointer+1))
        {
            if(log)System.out.println("avail:"+avail+"/pointer:"+pointer);
           
            avail = in.read(buffer0, 0, buffer0.length);
            
            pointer=-1;
            
            if(log)System.out.println("******* Fill buffer with "+avail+" chars");
            
            if(avail <=0)
            {
                throw new IOException("EOF");
            }
            
            //lastOfBuffer = buffer0[avail-1];
        }
        
        pointer++;
        avail--;
        return buffer0[pointer];
        
        
    }
    
    @Override
    protected void mark()
    {
        if(log)System.out.println("    MARK "+buffer0[pointer]+" ("+(int)buffer0[pointer]+")");
        mark = buffer0[pointer];
        //availOnMark = avail;
        
        if(avail ==0) {
            
        }
    }
    
    @Override
    protected void reset()
    {
        if(log)System.out.println("    RESET ");
        //pointer = mark;
        //avail = availOnMark;
        reset=true;
    }

  

    @Override
    protected void closeUnderlyingSource() throws IOException {
        if(in!=null)in.close();

    }

}
