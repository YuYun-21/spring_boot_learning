package com.yuyun.design;

abstract class AbstractHandler {

    /**
     * 下一关用当前抽象类来接收
     */
    protected AbstractHandler next;

    public void setNext(AbstractHandler next) {
        this.next = next;
    }

    /**
     * 处理程序
     *
     * @return int
     */
    public abstract int handler();
}

class FirstPassHandler extends AbstractHandler {

    private int play() {
        return 80;
    }

    @Override
    public int handler() {
        System.out.println("第一关-->FirstPassHandler");
        int score = play();
        if (score >= 80) {
            //分数>=80 并且存在下一关才进入下一关
            if (this.next != null) {
                return this.next.handler();
            }
        }
        return score;
    }
}

class SecondPassHandler extends AbstractHandler {

    private int play() {
        return 90;
    }

    public int handler() {
        System.out.println("第二关-->SecondPassHandler");

        int score = play();
        if (score >= 90) {
            //分数>=90 并且存在下一关才进入下一关
            if (this.next != null) {
                return this.next.handler();
            }
        }

        return score;
    }
}

class ThirdPassHandler extends AbstractHandler {

    private int play() {
        return 95;
    }

    public int handler() {
        System.out.println("第三关-->ThirdPassHandler");
        int score = play();
        if (score >= 95) {
            //分数>=95 并且存在下一关才进入下一关
            if (this.next != null) {
                return this.next.handler();
            }
        }
        return score;
    }
}

/**
 * 责任链模式处理程序客户端
 *
 * @author wizdom-hyh
 * @date 2024/02/23
 */
public class HandlerClient {
    public static void main(String[] args) {
        //第一关
        FirstPassHandler firstPassHandler = new FirstPassHandler();
        //第二关
        SecondPassHandler secondPassHandler = new SecondPassHandler();
        //第三关
        ThirdPassHandler thirdPassHandler = new ThirdPassHandler();

        // 和上面没有更改的客户端代码相比，只有这里的set方法发生变化，其他都是一样的
        //第一关的下一关是第二关
        firstPassHandler.setNext(secondPassHandler);
        //第二关的下一关是第三关
        secondPassHandler.setNext(thirdPassHandler);

        //说明：因为第三关是最后一关，因此没有下一关

        //从第一个关卡开始
        firstPassHandler.handler();
    }
}