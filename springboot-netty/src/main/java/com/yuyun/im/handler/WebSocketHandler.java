package com.yuyun.im.handler;

import com.alibaba.fastjson2.JSON;
import com.yuyun.im.command.Command;
import com.yuyun.im.enums.CommandType;
import com.yuyun.im.Result;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * WebSocket处理
 *
 * @author hyh
 * @since 2023-09-21
 */
@Slf4j
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext context, TextWebSocketFrame frame) throws Exception {
        log.info("textWebSocketFrame：" + frame.text());
        try {
            Command command = JSON.parseObject(frame.text(), Command.class);

            switch (CommandType.match(command.getCode())) {
                case CONNECTION:
                    ConnectionHandler.execute(context, command);
                    break;
                case CHAT:
                    ChatHandler.execute(context, frame);
                    break;
                case JOIN_GROUP:
                    JoinGroupHandler.execute(context);
                    break;
                default:
                    context.channel().writeAndFlush(Result.fail("不支持的CODE"));
            }
        } catch (Exception e) {
            context.channel().writeAndFlush(Result.fail("失败：" + e.getMessage()));
        }
    }
}
