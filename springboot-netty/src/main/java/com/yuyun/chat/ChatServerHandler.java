package com.yuyun.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @author hyh
 * @since 2023-09-25
 */
@Slf4j
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * 建立连接，第一个被执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        // 将该客户加入聊天组的信息发送给其他在线的客户端
        channelGroup.writeAndFlush(String.format("【客户端】%s 上线了 %s", channel.remoteAddress(), LocalDateTime.now().format(formatter)));

        // 将当前的 channel 加入 channelGroup
        channelGroup.add(channel);
    }

    /**
     * 断开连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        log.info("【客户端】{} 断开连接", channel.remoteAddress());
        channelGroup.writeAndFlush(String.format("【客户端】%s 断开连接", channel.remoteAddress()));
    }

    /**
     * channel 处于活动状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端 {} 上线了", ctx.channel().remoteAddress());
    }

    /**
     * channel 处于不活动状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        log.info("【客户端】{} 下线", channel.remoteAddress());
        channelGroup.writeAndFlush(String.format("【客户端】%s 下线", channel.remoteAddress()));
    }

    /**
     * 处理异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 关闭通道
        ctx.close();
        log.info("有异常，通道关闭");
    }

    /**
     * 读取数据
     *
     * @param channelHandlerContext
     * @param s
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        // msg 由 decoder 转换来
        Channel channel = channelHandlerContext.channel();
        // 遍历，根据不同的情况发送不同的信息
        channelGroup.forEach(ch -> {
            String user = "";
            if (!Objects.equals(ch, channel)) {
                user = "客户端";
            } else {
                user = "自己";
            }
            ch.writeAndFlush(String.format("【%s】%s 发送消息：%s", user, channel.remoteAddress(), s));
        });

        log.info("{} 发送消息：{}", channel.remoteAddress(), s);
    }
}
