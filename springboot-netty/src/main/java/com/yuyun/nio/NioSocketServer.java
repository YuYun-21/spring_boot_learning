package com.yuyun.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * @author hyh
 * @since 2023-09-22
 */
@Slf4j
public class NioSocketServer {

    static List<SocketChannel> channelList = new ArrayList<>();

    /**
     * 基础的nio
     * 没有数据收发也要遍历处理所有的channel
     *
     * @throws IOException
     */
    public static void test1() throws IOException {
        // 创建NIO ServerSocketChannel，与BIO的ServerSocket类似
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        // 设置 ServerSocketChannel 为非阻塞
        serverSocketChannel.configureBlocking(false);
        log.info("socket服务启动成功");

        while (true) {
            // 非阻塞模式 accept方法不会阻塞
            // NIO的非阻塞是由操作系统内部实现的，底层调用了Linux内核的accept函数
            // 获取客户端连接
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (Objects.nonNull(socketChannel)) {
                // 如果有客户端进行连接
                log.info("连接成功");
                // 设置 socketChannel 为非阻塞
                socketChannel.configureBlocking(false);
                // 保存客户端连接
                channelList.add(socketChannel);
            }
            Iterator<SocketChannel> iterator = channelList.iterator();
            while (iterator.hasNext()) {
                SocketChannel channel = iterator.next();
                ByteBuffer byteBuffer = ByteBuffer.allocate(6);
                // 非阻塞模式 read方法不会阻塞
                int len = channel.read(byteBuffer);
                if (len > 0) {
                    log.info("接收到消息：{}", new String(byteBuffer.array()));
                } else if (Objects.equals(-1, len)) {
                    // 如果客户端断开，把Socket从list中去掉
                    iterator.remove();
                    log.info("客户端断开连接");
                }
            }
        }
    }

    /**
     * 优化test1，引入多路复用器 Selector
     * 只遍历处理有数据收发的channel
     * 连接多的时候，某个连接处理业务数据慢了可能会造成阻塞
     *
     * @throws IOException
     */
    public static void test2() throws IOException {
        // 创建NIO ServerSocketChannel，与BIO的ServerSocket类似
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        // 设置 ServerSocketChannel 为非阻塞
        serverSocketChannel.configureBlocking(false);

        // 打开 Selector 处理 Channel，即创建epoll
        // selector 多路复用器
        Selector selector = Selector.open();
        // 把 serverSocketChannel 注册到 selector 上，并且 selector 对客户端 accept 连接操作感兴趣 OP_ACCEPT 连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        log.info("socket服务启动成功");

        while (true) {
            // 阻塞 等到需要处理的时间发生
            // 监听 serverSocketChannel 上面的 io 连接事件发生
            selector.select();

            // 获取 selector 中注册的全部事件的 selectedKeys 实例
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            // 遍历 selectedKeys 对事件进行处理
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                // 如果是 OP_ACCEPT 事件，则进行连接获取和事件注册
                if (selectionKey.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);

                    // 这里只注册了读事件，如果需要给客户端发送数据，可以注册写事件
                    socketChannel.register(selector,SelectionKey.OP_READ);
                    log.info("客户端连接成功");
                } else if (selectionKey.isReadable()){
                    // 如果是 OP_READ 事件，则进行读取和打印
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(6);
                    int len = socketChannel.read(byteBuffer);
                    if (len > 0) {
                        log.info("接收到消息：{}", new String(byteBuffer.array()));
                    } else if (Objects.equals(-1, len)) {
                        // 如果客户端断开，把Socket
                        socketChannel.close();
                        log.info("客户端断开连接");
                    }

                }
                // 从事件集合里删除本次处理的key，防止下一次 select 重复处理
                iterator.remove();
            }
        }
    }


    public static void main(String[] args) throws IOException {

        test2();

    }
}
