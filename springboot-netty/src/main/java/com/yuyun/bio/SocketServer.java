package com.yuyun.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/**
 * @author hyh
 * @since 2023-09-22
 */
@Slf4j
public class SocketServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);

        while (true) {
            log.info("等待连接。。。");
            // 阻塞方法
            Socket clientSocket = serverSocket.accept();
            log.info("有客户端连接了");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        handler(clientSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    public static void handler(Socket clientSocket) throws IOException {
        byte[] bytes = new byte[1024];
        log.info("准备read。。。");
        // 阻塞方法 接收客户端的数据，没有接收到数据时就阻塞
        InputStream inputStream = clientSocket.getInputStream();
        int read = inputStream.read(bytes);
        log.info("read完毕");
        if (!Objects.equals(-1, read)) {
            log.info("接收到客户端的数据：{}", new String(bytes, 0, read));
        }

        log.info("end");
    }
}
