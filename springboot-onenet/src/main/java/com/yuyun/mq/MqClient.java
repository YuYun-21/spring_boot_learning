package com.yuyun.mq;


import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Component
public class MqClient {
    private MqttConnectOptions options  = new MqttConnectOptions();;
    private MqttClient client ;
    /**
     * 是否需要手动回复ack
     * */
    private boolean manualAcks = false;

    private String subTopic;

    public boolean getManualAcks(){
        return manualAcks;
    }

    public void messageArrivedComplete(MqttMessage msg){
        try {
            client.messageArrivedComplete(msg.getId(),msg.getQos());
        } catch (MqttException e) {
            System.out.println("send puback catch exception");
            e.printStackTrace();
        }
    }
    public synchronized boolean connect(){

        //用户自定义合法的UTF-8字符串，可为空
        String clientID = "yuyun-mq";
        String serverURI = "ssl://183.230.40.96:8883";
        //MQ实例名称
        String userName = "yuyun-mq-test";

        //topic名称
        String mqTopic = "yuyun-topic-test";
        // 订阅名称
        String mqSub = "yuyun-topic-data";

        try {
            if(null == client) {
               client =  new MqttClient(serverURI, clientID, new MemoryPersistence());
            }
            //获取连接配置
            client.setManualAcks(manualAcks);
            client.setCallback(new com.yuyun.mq.PushCallback(this));
            resetOptions();
            try {
                client.connect(options);
            } catch (MqttException e) {
                e.printStackTrace();
            }
            subTopic = String.format("$sys/pb/consume/%s/%s/%s", userName, mqTopic, mqSub);

            try {
                //订阅 topic $sys/pb/consume/$MQ_ID/$TOPIC/$SUB ，QoS必须大于0，否则订阅失败
                client.subscribe(subTopic, 1);
                System.out.println("sub success");
                return true;
            } catch (MqttException e) {
                System.out.println("sub failed");
                e.printStackTrace();
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private void resetOptions(){
        // MQ实例名称
        String userName = "yuyun-mq-test";
        // MQ实例的 access_key
        String accessKey = "fFjnCBIQ5dhZg2wtI6306NoxT17oA1DWraGVhrzKf0A=";

        //版本号，无需修改
        String version = "2018-10-31";
        //通过MQ实例名称访问MQ
        String resourceName = "mqs/" + userName;
        String expirationTime = System.currentTimeMillis() / 1000 + 100 * 24 * 60 * 60 + "";
        //签名方法，支持md5、sha1、sha256
        String signatureMethod = "md5";
        String password = null;
        try {
            //生成token
            password = com.yuyun.mq.Token.assembleToken(version, resourceName, expirationTime, signatureMethod, accessKey);
        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //clean session 必须设置 true
        options.setCleanSession(true);
        options.setUserName(userName);
        options.setPassword(password.toCharArray());
        options.setConnectionTimeout(20);
        options.setKeepAliveInterval(30);
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
        InputStream caCrtFile = null;
        try{
            caCrtFile = this.getClass().getResource("/mqca/certificate.pem").openStream();
        } catch (IOException e){
            e.printStackTrace();
            return;
        }

        try {
            options.setSocketFactory(SslUtil.getSocketFactory(caCrtFile));
        } catch (NoSuchAlgorithmException | KeyStoreException | CertificateException
                | IOException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public boolean reConnect() {
        if(null != client) {
            try {
                //订阅失败而导致重连是不需要重新连接
                if(!client.isConnected()){
                    resetOptions();//防止token过期，需重新设置
                    client.connect(options);
                }
                //订阅失败会抛异常
                client.subscribe(subTopic, 1);
                System.out.println("reconncet and sub ok");
                return true;
            } catch (Exception e) {//订阅和连接失败都会进到此异常中
                System.out.println("reconnect failed");
                e.printStackTrace();//由于在循环中调用，建议调试时打印堆栈信息，正式中此打印删除
                return false;
            }
        }else{
            return connect();
        }
    }
    public static void main(String[] args) {
        MqClient mqClient = new MqClient();
        mqClient.connect();
    }
}
