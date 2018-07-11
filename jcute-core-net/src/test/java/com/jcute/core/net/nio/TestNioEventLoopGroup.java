package com.jcute.core.net.nio;

import com.jcute.core.net.nio.support.NioEventLoopGroup;

public class TestNioEventLoopGroup{

	public static void main(String[] args) throws Exception{

		NioEventLoopGroup group = new NioEventLoopGroup("nio-loop",4);
		group.start();

	}

}