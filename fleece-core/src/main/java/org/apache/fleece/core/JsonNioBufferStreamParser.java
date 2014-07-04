package org.apache.fleece.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;



/*public class JsonNioBufferStreamParser extends JsonBaseStreamParser {

    //http://stackoverflow.com/questions/13120585/decoding-characters-in-java-why-is-it-faster-with-a-reader-than-using-buffers
    private final ReadableByteChannel channel;
    private final ByteBuffer bbuf = ByteBuffer.allocate(Integer.getInteger("org.apache.fleece.default-char-buffer", 8192));
    private int pointer=-1;
    private int avail;
    private char mark;
    private boolean reset;
    //private int availOnMark;
    
    //Test increment buffer sizes
    
    public JsonNioBufferStreamParser(final Reader reader, final int maxStringLength) {
       super(maxStringLength);
       throw new NotImplementedException();
    }


    public JsonNioBufferStreamParser(final InputStream stream, final int maxStringLength) {
        this(stream, Charset.defaultCharset(), maxStringLength);
    }

    public JsonNioBufferStreamParser(final InputStream in, final Charset charset, final int maxStringLength) {
        super(maxStringLength);
        channel = Channels.newChannel(in);
    }

    @Override
    protected char readNextChar() throws IOException {
        
        if(bbuf.capacity() <=0) throw new IllegalArgumentException("buffer length must be greater than zero");
        
        if(reset)
        {
            reset=false;
            return mark;
        }
        
        if(avail <= 0)// || avail <= (pointer+1))
        {
            if(log)System.out.println("avail:"+avail+"/pointer:"+pointer);
           
            avail = channel.read(bbuf);
            //decoder.decode(bbuf, cbuf, true);
            //decoder.flush(cbuf);
            
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
        return bbuf.
        
        
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
        if(channel!=null)channel.close();

    }

}*/
