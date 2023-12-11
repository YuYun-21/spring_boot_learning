package com.yuyun.runner;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

/**
 * @author hyh
 * @since 2023-12-11
 */
@Component
public class StartedUpRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(StartedUpRunner.class);
    private final ConfigurableApplicationContext context;
    @Value("${server.port:}")
    private String port;
    @Value("${server.servlet.context-path:}")
    private String contextPath;

    public void run(ApplicationArguments args) throws Exception {
        if (this.context.isActive()) {
            log.info("==> 系统启动完毕");
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            while(true) {
                NetworkInterface networkInterface;
                do {
                    do {
                        if (!networkInterfaces.hasMoreElements()) {
                            return;
                        }

                        networkInterface = (NetworkInterface)networkInterfaces.nextElement();
                    } while(networkInterface.isLoopback());
                } while(networkInterface.isVirtual());

                List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();
                interfaceAddresses.forEach(interfaceAddress->{
                    InetAddress inetAddress = interfaceAddress.getAddress();
                    if (inetAddress instanceof Inet4Address) {
                        String url = String.format("http://%s:%s", inetAddress.getHostAddress(), this.port);
                        if (StrUtil.isNotBlank(this.contextPath)) {
                            url = url + this.contextPath;
                        }

                        log.info("==> 访问路径: {}", url);
                        log.info("==> 文档路径: {}/doc.html", url);
                    }
                });
            }
        }
    }

    public StartedUpRunner(ConfigurableApplicationContext context) {
        this.context = context;
    }

}
