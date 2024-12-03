package com.yuyun.design.factory;

/**
 * 日志记录器接口：抽象产品
 */
interface Logger {
    /**
     * 写入日志
     */
    public void writeLog();
}

/**
 * 日志记录器工厂接口：抽象工厂
 */
interface LoggerFactory {
    /**
     * 创建记录器
     *
     * @return {@link Logger}
     */
    public Logger createLogger();
}

/**
 * 工厂方法模式
 *
 * @author hyh
 * @since 2024-02-26
 */
public class MethodClient {
    public static void main(String[] args) {
        LoggerFactory factory;
        Logger logger;
        // 可引入配置文件实现
        factory = new FileLoggerFactory();
        logger = factory.createLogger();
        logger.writeLog();
    }
}

/**
 * 数据库日志记录器：具体产品
 */
class DatabaseLogger implements Logger {
    public void writeLog() {
        System.out.println("数据库日志记录。");
    }
}

/**
 * 文件日志记录器：具体产品
 */
class FileLogger implements Logger {
    public void writeLog() {
        System.out.println("文件日志记录。");
    }
}

/**
 * 数据库日志记录器工厂类：具体工厂
 */
class DatabaseLoggerFactory implements LoggerFactory {
    public Logger createLogger() {
        // 连接数据库，代码省略
        // 创建数据库日志记录器对象
        Logger logger = new DatabaseLogger();
        // 初始化数据库日志记录器，代码省略
        return logger;
    }
}

/**
 * 文件日志记录器工厂类：具体工厂
 */
class FileLoggerFactory implements LoggerFactory {
    public Logger createLogger() {
        // 创建文件日志记录器对象
        Logger logger = new FileLogger();
        // 创建文件，代码省略
        return logger;
    }
}
