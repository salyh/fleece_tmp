package org.apache.fleece.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.BitSet;

import javax.json.stream.JsonParsingException;

import sun.text.CodePointIterator;



public class JsonFastUTF8ByteBufferStreamParser extends JsonBaseStreamParser {

/*private static final BufferCache<byte[]> BUFFER_CACHE = new BufferCache<byte[]>(
        Integer.getInteger("org.apache.fleece.default-char-buffer", 8192)) {
    @Override
    protected byte[] newValue(final int defaultSize) {
        return new byte[defaultSize];
    }
};*/


    private final byte[] buffer0 = new byte[Integer.getInteger("org.apache.fleece.default-char-buffer", 8192)*2];// BUFFER_CACHE.getCache();
    private InputStream in;
    private int pointer=-1;
    private int avail;
    private char mark;
    private boolean reset;
    private int lowSurrogate = -1;
    

    public JsonFastUTF8ByteBufferStreamParser(final InputStream stream, final int maxStringLength) {
    	super(maxStringLength);
        in = stream;
       
    }
    
    protected void validateStartByte(int start)
    {
    	if((start&0xff) > 127 && (start&0xff) < 192) throw new JsonParsingException("UTF-8 startbyte "+(start&0xff)+" not valid", null);
    }
    
    protected void validateFollowUpByte(int follow)
    {
    	if((follow & 0xff) >= 192) throw new JsonParsingException("UTF-8 followupbyte "+(follow&0xff)+" not valid", null);
    }
    
    protected char bytesToChar() throws JsonParsingException, IOException
    {
    	
    	if(lowSurrogate > -1)
    	{
    		lowSurrogate = -1;
    		return (char) lowSurrogate;
    	}
    	
    	int b0 = readNextByte();
    	
    	validateStartByte(b0);
    	
    	int b0u = b0 & 0XFF; //make unsigned
    		
    	if(b0u < 0) throw new JsonParsingException("", null);
    	
    	if(b0u >= 0 && b0u <= 127)
    	{
    		System.out.println("  -- 1 bytes");
    		//single byte
    		return (char) (b0);
    		
    	}else if (b0u > 127 && b0u <= 193)
    	{
    		throw new JsonParsingException("byte 1 invalid", null);
    		
    	} else if (b0u >= 194 && b0u <= 223)
    	{
    		
    		System.out.println("  -- 2 bytes");
    		int b1 = readNextByte();
    	
    		validateFollowUpByte(b1);
    		
    		
    		
    		//two byte
    		//validate second byte
    		return (char)(((b0 <<6 ) ^ b1) ^ 0x0f80);
    		
    		
    	}  else if (b0u >= 224 && b0u <= 239)
    	{
    		
    		System.out.println("  -- 3 bytes");
    		int b1 = readNextByte();
    		int b2 = readNextByte();
    		
    		validateFollowUpByte(b1);
    		validateFollowUpByte(b2);
    		
    		
    		
    		//three byte
    		//validate second and third byte
    		return (char) (((b0<<12)^(b1<<6) ^ b2) ^ 0x1f80);
    	} 
    	 else if (b0u >= 240 && b0u <= 244)
     	{
    		 System.out.println("  -- 4 bytes");
    		 
    		 int b1 = readNextByte();
    		 int b2 = readNextByte();
    		 int b3= readNextByte();
    
    		 validateFollowUpByte(b1);
        		validateFollowUpByte(b2);
        		validateFollowUpByte(b3);
        
        	 int tmp = ((b0 & 0x1f) << 18)
        				               | ((b1 & 0x3f) << 12)
        				               | ((b2 & 0x1f) << 6)
        				              | (b3 & 0x1f);
        				          
    		 
    		
    		 
    		 char highSurrogate = (char) ((tmp - 0x10000) / 0x400 + 0xd800);
    		 lowSurrogate =  ((tmp - 0x10000) % 0x400 + 0xdc00); //next one
    		 
    		 return highSurrogate;
    		 
     		//four byte
    		 //validate second, third and fourth byte
     	} else if(b0u <= 255)
     		throw new JsonParsingException("byte "+b0u+" within range but utf8 invalid", null);
     	
    	
     		throw new JsonParsingException("byte "+b0u+" out of range (must not happen)", null);
    	
    }


    @Override
    protected char readNextChar() throws IOException {
        
        if(reset)
        {
            reset=false;
            return mark;
        }
        
           
        mark = bytesToChar();
        return mark;
        
    }
    
    
    
    protected byte readNextByte() throws IOException {
        
        if(buffer0.length==0) throw new IllegalArgumentException("buffer length must be greater than zero");
        
        if(avail <= 0)
        {
            if(log)System.out.println("avail:"+avail+"/pointer:"+pointer);
           
            avail = in.read(buffer0, 0, buffer0.length);
            
            pointer=-1;
            
            if(log)System.out.println("******* Fill buffer with "+avail+" chars");
            
            if(avail <=0)
            {
                throw new IOException("EOF");
            }
            
        }
        
        pointer++;
        avail--;
       
        /*System.out.println("-----");
        System.out.println(buffer0[pointer]);
        System.out.println(buffer0[pointer] & 0xff);
        System.out.println(Integer.toHexString(buffer0[pointer] & 0xff));
        System.out.println("-----");
*/
        return buffer0[pointer];
        
        
    }
    
    @Override
    protected void mark()
    {
        if(log)System.out.println("    MARK "+buffer0[pointer]+" ("+(int)buffer0[pointer]+")");
     
    }
    
    @Override
    protected void reset()
    {
        if(log)System.out.println("    RESET ");
        reset=true;
    }

  

    @Override
    protected void closeUnderlyingSource() throws IOException {
    	
    	//BUFFER_CACHE.release(buffer0);
    	
        if(in!=null)in.close();
       
    }

    
    public void recycle(final Reader reader)
    {
    	
    	in=null;
    	recycle();
    }
    
    public void recycle(final InputStream in)
    {
    	
    	this.in=in;
    	recycle();
    }
}
