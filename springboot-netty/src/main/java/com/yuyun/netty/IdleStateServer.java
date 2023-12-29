package com.yuyun.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author hyh
 * @since 2023-09-25
 */
@Slf4j
public class IdleStateServer {

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
                    // 在 parentGroup 增加一个日志处理器
                    .handler(new LoggingHandler(LogLevel.INFO))
                    // 创建通道初始化对象，设置初始化参数，在 SocketChannel 建立起来之前执行
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            /*
                            1、IdleStateHandler 是netty提供的处理空闲状态的处理器
                            2、long readerIdleTime 表示多长时间没有读，就会发送一个心跳检测包检测是否连接
                            3、long writerIdleTime 表示多长时间没有写，就会发送一个心跳检测包检测是否连接
                            4、long allIdleTime 表示多长时间没有读，就会发送一个心跳检测包检测是否连接
                            5、当 IdleStateHandler 触发后，就会传递给管道的下一个handle去处理
                            通过调用（触发）下一个handle的 userEventTiggered ，在该方法中处理 IdleStateHandler(读空闲，写空闲，读写空闲)
                             */
                            pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
                            // 加入一个对空闲检测进一步处理的handle
                            pipeline.addLast(new UserEventHandler());
                        }
                    });
            log.info("server启动。。。。");
            // 绑定一个端口并生成了一个ChannelFuture异步对象，通过isDone()等方法可以判断异步事件的执行情况
            // 启动服务器(并绑定端口)，bind是异步操作，sync方法是等待异步操作执行完毕
            ChannelFuture channelFuture = bootstrap.bind(9001).sync();
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
