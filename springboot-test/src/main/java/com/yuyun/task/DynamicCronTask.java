package com.yuyun.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * @author hyh
 * @since 2023-08-16
 */
@Component
@Slf4j
public class DynamicCronTask implements InitializingBean {

    @Autowired
    @Qualifier("MyTaskScheduler")
    private ThreadPoolTaskScheduler taskScheduler;

    private Map<String, ScheduledFuture<?>> scheduledFutureMap;

    @Override
    public void afterPropertiesSet() throws Exception {

        scheduledFutureMap = new HashMap<>();

        scheduledFutureMap.put("2", register("1,21,42 * * * * ?", "2"));
        scheduledFutureMap.put("3", registerNew("11,32,53 * * * * ?", "3"));

//        scheduledFutureMap.put("a",register("* * * * * ?","task1"));
//        scheduledFutureMap.put("b",register("*/2 * * * * ?","task2"));
//        scheduledFutureMap.put("c",register("*/5 * * * * ?","task5"));
//        scheduledFutureMap.put("key1", register("*/2 * * * * ?", "key1"));
//        scheduledFutureMap.put("key2", register("*/4 * * * * ?", "key2"));
    }

    public ScheduledFuture<?> register(String cron, String key) {

        // 高版本使用 CronExpression，低版本使用 CronSequenceGenerator
        boolean validExpression = CronSequenceGenerator.isValidExpression(cron);
        log.info("-----key:{}-----", key);
        log.info("cron:[{}]是合法的吗:[{}]", cron, validExpression);

        CronSequenceGenerator expression = new CronSequenceGenerator(cron);
        Date next = expression.next(new Date());
        if (null != next) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            log.info("定时任务下次执行的时间为:[{}]", sdf.format(next));
        }
        log.info("----------------");
        ScheduledFuture<?> schedule = taskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                log.info("我执行了" + key);
            }
        }, new CronTrigger(cron));

        return schedule;
    }

    public ScheduledFuture<?> registerNew(String cron, String key) {

        // CronExpression  spring 5.3 以后提供的工具类
        // 如果是低版本的spring可以使用 CronSequenceGenerator
        boolean validExpression = CronExpression.isValidExpression(cron);
        log.info("-----key:{}-----", key);
        log.info("cron:[{}]是合法的吗:[{}]", cron, validExpression);

        CronExpression expression = CronExpression.parse(cron);
        //下次预计的执行时间
        LocalDateTime next = expression.next(LocalDateTime.now());

        if (null != next) {
            //下下次预计的执行时间
            LocalDateTime next2 = expression.next(next);
            //获取执行间隔
            long interval = ChronoUnit.SECONDS.between(next, next2);

            log.info("定时任务下次执行的时间为:[{}]", next);
            log.info("定时任务下下次执行的时间为:[{}]", next2);
            log.info("定时任务执行的时间间隔:[{}]", interval);
        }
        log.info("----------------");
        ScheduledFuture<?> schedule = taskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                log.info("我执行了" + key);
            }
        }, new CronTrigger(cron));

        return schedule;
    }

    public void remove(String key) {
        ScheduledFuture<?> scheduledFuture = scheduledFutureMap.get(key);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
            scheduledFutureMap.remove(key);
        }

    }

    public void change(String cronNew, String key) {
        ScheduledFuture<?> scheduledFuture = scheduledFutureMap.get(key);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
            scheduledFutureMap.remove(key);
        }
        scheduledFutureMap.put(key, register(cronNew, key));

    }

    public void cancel(String key) {
        ScheduledFuture<?> scheduledFuture = scheduledFutureMap.get(key);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
            scheduledFutureMap.remove(key);
        }
    }

    public void list() {
        scheduledFutureMap.forEach((k, v) -> {
            System.out.println("k = " + k + ",v = " + v.toString());
        });
    }

}

