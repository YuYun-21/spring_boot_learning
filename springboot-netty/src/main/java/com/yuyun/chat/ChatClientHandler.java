package com.yuyun.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hyh
 * @since 2023-09-25
 */
@Slf4j
public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext context, String msg) throws Exception {
        Channel channel = context.channel();
        if (!msg.contains(channel.remoteAddress() + "")) {
            log.info("接收到 {} 消息：{}", channel.remoteAddress(), msg);
        }
    }
}
