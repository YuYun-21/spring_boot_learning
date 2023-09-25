package com.yuyun.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hyh
 * @since 2023-09-25
 */
@Slf4j
public class UserEventHandler extends ChannelInboundHandlerAdapter {

    /**
     * 用户事件已触发
     *
     * @param ctx 上下文
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent stateEvent = (IdleStateEvent) evt;
            String eventType = "";
            switch (stateEvent.state()) {
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;
            }
            log.info("{} 空闲状态事件：{}", ctx.channel().remoteAddress(), eventType);

            // 如果发生空闲事件，关闭连接
            ctx.close();
        }

    }
}
