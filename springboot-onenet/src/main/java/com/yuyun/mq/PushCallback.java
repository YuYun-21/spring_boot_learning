package com.yuyun.mq;

import com.yuyun.service.MessageProcessService;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

@Component
public class PushCallback implements MqttCallback {

    @Autowired
    protected MessageProcessService messageProcessService;
    private static PushCallback pushCallback;

    private IMqttAsyncClient Client;
    private static final Logger logger = Logger.getLogger(PushCallback.class.getCanonicalName());
    private MqClient mqClient;
    private int reConnTimes = 0;

    /**
     * 通过PostConstruct实现初始化bean之前进行的操作
     */
    @PostConstruct
    public void init() {
        pushCallback = this;
        pushCallback.messageProcessService = this.messageProcessService;
    }

    public PushCallback(MqClient client) {
        mqClient = client;
    }

    @Override
    public void connectionLost(Throwable cause) {
        logger.info("connect is losted,and try to reconnect,cause = " + cause.getMessage() );
        cause.printStackTrace();
        while(true){
            if(mqClient.reConnect()){
                break;
            }
            try {
                //前20次每秒重连一次
                if(reConnTimes++ > 20){
                    Thread.sleep(1000);
                }else{//超过20次后没10s重连一次
                    Thread.sleep(10000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    /**
     * 说明：paho 本身有个bug，即此函数接口抛出异常都会回调到connectionLost()的接口，故需要在此函数中用try catch包起来处理，
     *      确保无异常抛出。
     * */
    public void messageArrived(String topic, MqttMessage message)  {
       try {
           byte[] payload = message.getPayload();
           OnenetMq.Msg obj = OnenetMq.Msg.parseFrom(payload);
           long at = obj.getTimestamp();
           long msgid = obj.getMsgid();
           String body = new String(obj.getData().toByteArray());

           SimpleDateFormat slf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           String time = slf.format(at);

           logger.info("time = " + time +
                   ",msg id: " + msgid +
                   ", body: " + body);

           // 消息处理，存储到数据库
           pushCallback.messageProcessService.messageProcess(body);

       }catch(Exception e){
           logger.info("messageArrived phrase exception");
       }finally {
           if(mqClient.getManualAcks()){
               mqClient.messageArrivedComplete(message);
           }
       }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        Client = token.getClient();
    }

}
