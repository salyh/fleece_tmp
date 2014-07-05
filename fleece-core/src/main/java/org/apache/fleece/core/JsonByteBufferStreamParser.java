package org.apache.fleece.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;



public class JsonByteBufferStreamParser extends JsonBaseStreamParser {

/*private static final BufferCache<byte[]> BUFFER_CACHE = new BufferCache<byte[]>(
        Integer.getInteger("org.apache.fleece.default-char-buffer", 8192)) {
    @Override
    protected byte[] newValue(final int defaultSize) {
        return new byte[defaultSize];
    }
};*/


    private final byte[] buffer0 = new byte[Integer.getInteger("org.apache.fleece.default-char-buffer", 8192)];// BUFFER_CACHE.getCache();
    private final Reader inr;
    private final InputStream in;
    private int pointer=-1;
    private int avail;
    private char mark;
    private boolean reset;
    //private int availOnMark;
    
    //Test increment buffer sizes
    
    public JsonByteBufferStreamParser(final Reader reader, final int maxStringLength) {
        super(maxStringLength);
        inr = reader;
        in=null;
    }


    public JsonByteBufferStreamParser(final InputStream stream, final int maxStringLength) {
    	super(maxStringLength);
        in = stream;
        inr=null;
    }

    public JsonByteBufferStreamParser(final InputStream in, final Charset charset, final int maxStringLength) {
    	super(maxStringLength);
    	this.in = in;
    	inr=null;
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
        return (char) (buffer0[pointer] & 0xFF);
        
        
    }
    
    @Override
    protected void mark()
    {
        if(log)System.out.println("    MARK "+buffer0[pointer]+" ("+(int)buffer0[pointer]+")");
        mark = (char) (buffer0[pointer] & 0xFF);
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
    	
    	//BUFFER_CACHE.release(buffer0);
    	
        if(in!=null)in.close();
        if(inr!=null)in.close();
    }

}
