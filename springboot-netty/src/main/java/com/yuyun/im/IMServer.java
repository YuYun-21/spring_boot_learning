package com.yuyun.im;

import com.yuyun.im.handler.WebSocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * netty服务启动
 *
 * @author hyh
 * @since 2023-09-21
 */
public class IMServer {

    /**
     * 用户信息
     */
    public static Map<String, Channel> USERS = new ConcurrentHashMap<>(1024);

    public static ChannelGroup GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

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
                        // websocket基于http协议 添加http解码编码器
                        pipeline.addLast(new HttpServerCodec())
                                // 是以块方式写 添加 ChunkedWriteHandler 处理器  支持大数据流
                                .addLast(new ChunkedWriteHandler())
                                // http数据在传输过程中是分段 当浏览器发送大量数据时，就会发出多次http请求
                                // 对http消息做聚合操作 FullHttpRequest FullHttpResponse
                                .addLast(new HttpObjectAggregator(1024 * 64))
                                // webSocket支持 它的数据是以帧（frame）的形式传输
                                // WebSocketFrame 下面有6个之类
                                // 浏览器请求时 ws://localhost:9000/xxx 表示请求的uri，则new WebSocketServerProtocolHandler("/xxx")
                                // WebSocketServerProtocolHandler 核心功能是将http协议升级为ws协议，保持长连接
                                .addLast(new WebSocketServerProtocolHandler("/"))
                                // 自定义接收处理消息
                                .addLast(new WebSocketHandler());
                    }
                });

        bootstrap.bind(8088).sync();
    }
}
