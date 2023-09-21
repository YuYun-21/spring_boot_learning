package com.yuyun.im;

import com.yuyun.im.handler.WebSocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * netty服务启动
 *
 * @author hyh
 * @since 2023-09-21
 */
public class IMServer {

    public static Map<String, Channel> USERS = new ConcurrentHashMap<>(1024);

    public static void start() throws InterruptedException {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        // 绑定端口
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        // 添加http解码编码器
                        pipeline.addLast(new HttpServerCodec())
                                // 支持大数据流
                                .addLast(new ChunkedWriteHandler())
                                // 对http消息做聚合操作 FullHttpRequest FullHttpResponse
                                .addLast(new HttpObjectAggregator(1024 * 64))
                                // webSocket支持
                                .addLast(new WebSocketServerProtocolHandler("/"))
                                // 自定义接收处理消息
                                .addLast(new WebSocketHandler());
                    }
                });

        bootstrap.bind(8088).sync();
    }
}
