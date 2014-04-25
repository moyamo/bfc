package com.moyamo.bfc;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class UTF8 {
	private static Charset charset = Charset.availableCharsets().get("UTF-8");
	
	public static ByteBuffer encode(String s) {
		return charset.encode(s);
	}
	
	public static CharBuffer decode(ByteBuffer b) {
		return charset.decode(b);
	}
}
