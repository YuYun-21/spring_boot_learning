package com.yuyun.im;

import com.alibaba.fastjson2.JSON;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 响应数据
 *
 * @author hyh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 响应数据
     */
    private String data;

    /**
     * 响应时间
     */
    private LocalDateTime time;

    public static TextWebSocketFrame success(String data) {

        return new TextWebSocketFrame(JSON.toJSONString(new Result(2001, "Success", data, LocalDateTime.now())));
    }

    public static TextWebSocketFrame success(Integer code, String data) {

        return new TextWebSocketFrame(JSON.toJSONString(new Result(code, "Success", data, LocalDateTime.now())));
    }

    public static TextWebSocketFrame success(String msg, String data) {

        return new TextWebSocketFrame(JSON.toJSONString(new Result(2001, msg, data, LocalDateTime.now())));
    }

    public static TextWebSocketFrame fail(String data) {

        return new TextWebSocketFrame(JSON.toJSONString(new Result(5001, "error", data, LocalDateTime.now())));
    }

    public static TextWebSocketFrame fail(Integer code, String data) {

        return new TextWebSocketFrame(JSON.toJSONString(new Result(code, "error", data, LocalDateTime.now())));
    }

    public static TextWebSocketFrame fail(String errorMsg, String data) {

        return new TextWebSocketFrame(JSON.toJSONString(new Result(5001, errorMsg, data, LocalDateTime.now())));
    }
}
