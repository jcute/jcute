package com.jcute.network.buffer;

import com.jcute.network.buffer.support.ByteProcessorByIndexOf;
import com.jcute.network.buffer.support.ByteProcessorByNotIndexOf;

public interface ByteProcessor{

	public static final byte SPACE = (byte)' ';
	public static final byte HTAB = (byte)'\t';
	public static final byte CARRIAGE_RETURN = (byte)'\r';
	public static final byte LINE_FEED = (byte)'\n';

	public static final ByteProcessor FIND_NUL = new ByteProcessorByIndexOf((byte)0);
	public static final ByteProcessor FIND_NON_NUL = new ByteProcessorByNotIndexOf((byte)0);
	public static final ByteProcessor FIND_CR = new ByteProcessorByIndexOf(CARRIAGE_RETURN);
	public static final ByteProcessor FIND_NON_CR = new ByteProcessorByNotIndexOf(CARRIAGE_RETURN);
	public static final ByteProcessor FIND_LF = new ByteProcessorByIndexOf(LINE_FEED);
	public static final ByteProcessor FIND_NON_LF = new ByteProcessorByNotIndexOf(LINE_FEED);
	public static final ByteProcessor FIND_SEMI_COLON = new ByteProcessorByIndexOf((byte)';');
	public static final ByteProcessor FIND_COMMA = new ByteProcessorByIndexOf((byte)',');
	public static final ByteProcessor FIND_ASCII_SPACE = new ByteProcessorByIndexOf(SPACE);

	public static final ByteProcessor FIND_CRLF = new ByteProcessor() {
		@Override
		public boolean process(byte value){
			return value != CARRIAGE_RETURN && value != LINE_FEED;
		}
	};

	public static final ByteProcessor FIND_NON_CRLF = new ByteProcessor() {
		@Override
		public boolean process(byte value){
			return value == CARRIAGE_RETURN || value == LINE_FEED;
		}
	};
	
	public static final ByteProcessor FIND_LINEAR_WHITESPACE = new ByteProcessor() {
		@Override
		public boolean process(byte value){
			return value != SPACE && value != HTAB;
		}
	};
	
	public static final ByteProcessor FIND_NON_LINEAR_WHITESPACE = new ByteProcessor() {
		@Override
		public boolean process(byte value){
			return value == SPACE || value == HTAB;
		}
	};

	boolean process(byte value) throws Exception;

}