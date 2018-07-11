package com.jcute.core.net.toolkit;

import com.jcute.core.net.toolkit.support.ByteProcessorForOnEquals;
import com.jcute.core.net.toolkit.support.ByteProcessorForOnIndexOf;
import com.jcute.core.net.toolkit.support.ByteProcessorForUnEquals;
import com.jcute.core.net.toolkit.support.ByteProcessorForUnIndexOf;

public interface ByteProcessor{
	
	public static final ByteProcessor FIND_ON_NULL = new ByteProcessorForOnIndexOf((byte)0);
	public static final ByteProcessor FIND_UN_NULL = new ByteProcessorForUnIndexOf((byte)0);
	
	public static final ByteProcessor FIND_ON_CR = new ByteProcessorForOnIndexOf((byte)'\r');
	public static final ByteProcessor FIND_UN_CR = new ByteProcessorForUnIndexOf((byte)'\r');
	
	public static final ByteProcessor FIND_ON_CF = new ByteProcessorForOnIndexOf((byte)'\n');
	public static final ByteProcessor FIND_UN_CF = new ByteProcessorForUnIndexOf((byte)'\n');
	
	public static final ByteProcessor FIND_ON_CS = new ByteProcessorForOnIndexOf((byte)';');
	public static final ByteProcessor FIND_UN_CS = new ByteProcessorForOnIndexOf((byte)';');
	
	public static final ByteProcessor FIND_ON_CRF = new ByteProcessorForOnEquals((byte)'\r',(byte)'\n');
	public static final ByteProcessor FIND_UN_CRF = new ByteProcessorForUnEquals((byte)'\r',(byte)'\n');
	
	public static final ByteProcessor FIND_ON_WHITESPACE = new ByteProcessorForOnEquals((byte)' ',(byte)'\t');
	public static final ByteProcessor FIND_UN_WHITESPACE = new ByteProcessorForUnEquals((byte)' ',(byte)'\t');
	
	public boolean process(byte value) throws Exception;
	
}