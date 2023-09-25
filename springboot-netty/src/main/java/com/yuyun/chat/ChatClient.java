package com.yuyun.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * 客户端
 *
 * @author hyh
 * @since 2023-09-25
 */
@Slf4j
public class ChatClient {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        // 创建客户端启动对象
        // 注意客户端使用的不是 ServerBootstrap 而是 Bootstrap
        Bootstrap bootstrap = new Bootstrap();
        // 设置线程组
        bootstrap.group(group)
                // 使用NioSocketChannel作为客户端的通道实现
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        // 向 pipeline 加入解码器
                        pipeline.addLast("decoder", new StringDecoder());
                        // 向 pipeline 加入编码器
                        pipeline.addLast("encoder", new StringEncoder());
                        // 自定义业务处理handler
                        pipeline.addLast(new ChatClientHandler());
                    }
                });
        try {
            // 启动客户端去连接服务器端
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9000).sync();
            Channel channel = channelFuture.channel();
            log.info("连接：------ {} -------", channel.localAddress());

            // 客户端需要输入一个信息，创建一个扫描器
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                // 通过 channel 发送到服务端
                channel.writeAndFlush(msg);
            }

            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
