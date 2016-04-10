package com.chejixing.socket;

import org.apache.mina.core.buffer.IoBuffer;

public class SocketUtils {
	
	public static IoBuffer escape(IoBuffer buffer) {
		IoBuffer escapedBuffer = IoBuffer.allocate(1024);
		escapedBuffer.setAutoExpand(true);
		
		escapedBuffer.put(buffer.get());
		buffer.limit(buffer.limit()-1);
		
		while(buffer.hasRemaining()){
			byte b = buffer.get();
			if(b == (byte)0x7e){
				escapedBuffer.put((byte)0x7d);
				escapedBuffer.put((byte)0x02);
			}else if(b == (byte)0x7d){
				escapedBuffer.put((byte)0x7d);
				escapedBuffer.put((byte)0x01);
			}else{
				escapedBuffer.put(b);
			}
		}
		buffer.limit(buffer.limit() + 1);
		escapedBuffer.put(buffer.get());
		
		escapedBuffer.flip();
		return escapedBuffer;
	}
	
	public static IoBuffer unescape(IoBuffer buffer){
		IoBuffer unescapedBuffer = IoBuffer.allocate(1024);
		unescapedBuffer.setAutoExpand(true);
		
		while(buffer.hasRemaining()){
			byte pre = buffer.get();
			if(pre == (byte)0x7d){
				byte next = buffer.get();
				if(next == (byte)0x01){
					unescapedBuffer.put((byte)0x7d);
				}else if(next == (byte)0x02){
					unescapedBuffer.put((byte)0x7e);
				}else{
					unescapedBuffer.put(pre);
					unescapedBuffer.put(next);
				}
			}else{
				unescapedBuffer.put(pre);
			}
		}
		
		unescapedBuffer.flip();
		return unescapedBuffer;
	}
}
