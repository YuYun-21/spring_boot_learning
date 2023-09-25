package com.yuyun.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端
 *
 * @author hyh
 * @since 2023-09-25
 */
@Slf4j
public class ChatServer {

    public static void main(String[] args) throws InterruptedException {
        // bossGroup只是处理连接请求，真正的和客户端业务处理，由workerGroup完成
        // nThreads 含有的子线程NioEventLoop的个数默认为cpu核数的两倍
        EventLoopGroup parentGroup = new NioEventLoopGroup(1);
        EventLoopGroup childGroup = new NioEventLoopGroup(8);

        // 服务器端的启动对象
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            // 设置两个线程组
            bootstrap.group(parentGroup, childGroup)
                    // 使用NioServerSocketChannel作为服务器的通道实现
                    .channel(NioServerSocketChannel.class)
                    // 初始化服务器连接队列大小，服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接。
                    // 多个客户端同时来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 创建通道初始化对象，设置初始化参数，在 SocketChannel 建立起来之前执行
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 向 pipeline 加入解码器  将 byteBuf 转换成 string
                            pipeline.addLast("decoder", new StringDecoder());
                            // 向 pipeline 加入编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            // 向 pipeline 加入自定义业务处理handler
                            // 对workerGroup的SocketChannel设置处理器
                            pipeline.addLast(new ChatServerHandler());
                        }
                    });
            log.info("server启动。。。。");
            // 绑定一个端口并生成了一个ChannelFuture异步对象，通过isDone()等方法可以判断异步事件的执行情况
            // 启动服务器(并绑定端口)，bind是异步操作，sync方法是等待异步操作执行完毕
            ChannelFuture channelFuture = bootstrap.bind(9000).sync();
            log.info("关闭通道");
            // 等待服务端监听端口关闭，closeFuture是异步操作
            // 通过sync方法同步等待通道关闭处理完毕，这里会阻塞等待通道关闭完成，内部调用的是Object的wait()方法
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅关闭EventLoopGroup，释放掉所有资源包括创建的线程
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
