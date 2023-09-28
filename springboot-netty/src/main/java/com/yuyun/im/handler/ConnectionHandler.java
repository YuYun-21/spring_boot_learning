package com.yuyun.im.handler;

import com.alibaba.fastjson2.JSON;
import com.yuyun.im.command.Command;
import com.yuyun.im.IMServer;
import com.yuyun.im.Result;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 连接信息处理
 *
 * @author hyh
 * @since 2023-09-21
 */
@Slf4j
public class ConnectionHandler {
    public static void execute(ChannelHandlerContext context, Command command) {

        if (IMServer.USERS.containsKey(command.getNickname())) {
            context.channel().writeAndFlush(Result.fail("该用户已在线！"));
            context.disconnect();
            return;
        }

        IMServer.USERS.put(command.getNickname(), context.channel());

        context.channel().writeAndFlush(Result.success("与服务端连接建立成功！"));
        context.channel().writeAndFlush(Result.success(JSON.toJSONString(IMServer.USERS.keySet())));
        log.info("建立单聊连接: {} -------------", context.channel().remoteAddress());
    }

}
