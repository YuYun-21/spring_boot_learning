package com.yuyun.im.handler;

import com.yuyun.im.IMServer;
import com.yuyun.im.Result;
import io.netty.channel.ChannelHandlerContext;

/**
 * 加入群聊组信息处理
 *
 * @author hyh
 * @since 2023-09-21
 */
public class JoinGroupHandler {
    public static void execute(ChannelHandlerContext context) {
        // 加入ChannelGroup
        IMServer.GROUP.add(context.channel());
        context.channel().writeAndFlush(Result.success("已加入系统默认群聊"));

    }

}
