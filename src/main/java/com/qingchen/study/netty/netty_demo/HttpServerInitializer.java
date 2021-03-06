/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qingchen.study.netty.netty_demo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.util.ArrayList;
import java.util.List;


/**
 * 配置pipeline处理handler相关操作
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final List<SocketChannel> list = new ArrayList<>();

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        list.add(socketChannel);

        ChannelPipeline pipeline = socketChannel.pipeline();

       /* pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
        pipeline.addLast(new HttpResponseEncoder());*/
        pipeline.addLast(new NettyServerHandler());

    }
}
