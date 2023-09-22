package com.yuyun.im.handler;

import com.alibaba.fastjson2.JSON;
import com.yuyun.im.IMServer;
import com.yuyun.im.Result;
import com.yuyun.im.command.ChatMessage;
import com.yuyun.im.enums.MessageType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.StringUtil;

import java.util.Objects;

/**
 * 聊天信息处理
 *
 * @author hyh
 * @since 2023-09-21
 */
public class ChatHandler {
    public static void execute(ChannelHandlerContext context, TextWebSocketFrame frame) {

        try {
            ChatMessage chatMessage = JSON.parseObject(frame.text(), ChatMessage.class);
            switch (MessageType.match(chatMessage.getType())) {
                // 私聊
                case PRIVATE:
                    String messageTarget = chatMessage.getTarget();

                    if (StringUtil.isNullOrEmpty(messageTarget)) {
                        context.channel().writeAndFlush(Result.fail("消息发送失败", "发送消息前请指定消息发送对象！"));
                        return;
                    }
                    Channel channel = IMServer.USERS.get(messageTarget);
                    if (Objects.isNull(channel) || !channel.isActive()) {
                        context.channel().writeAndFlush(Result.fail("消息发送失败", messageTarget + "不在线！"));
                        return;
                    }
                    channel.writeAndFlush(Result.success("私聊消息(" + chatMessage.getNickname() + ")", chatMessage.getContent()));
                    break;
                case GROUP:
                    IMServer.GROUP.writeAndFlush(Result.success("群消息，发送者：" + chatMessage.getNickname(), chatMessage.getContent()));
                    break;
                default:
                    context.channel().writeAndFlush(Result.fail("不支持的消息类型！"));
            }
        } catch (Exception e) {
            context.channel().writeAndFlush(Result.fail("消息格式错误，请确认后再发！"));
        }
    }
}
