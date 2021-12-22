package com.yuyun.bsn;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bsnbase.sdk.client.fabric.FabricClient;
import com.bsnbase.sdk.entity.config.Config;
import com.bsnbase.sdk.entity.req.fabric.*;
import com.bsnbase.sdk.entity.resp.fabric.*;
import com.bsnbase.sdk.util.common.Common;
import com.google.protobuf.util.JsonFormat;
import org.junit.Test;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class FabricTest {


    /**
     * 初始化config
     * 应用程序的私钥和公钥填写在 .pem 文件中.
     * com.bsnbase.sdk.util.common.Common提供根据路径获取内容方法
     * Common.readLocalFile参数为pem存储目录的绝对路径
     * 例如: Common.readLocalFile("D:/test/private_key.pem")
     * Common.readFile参数为pem存储目录的相对路径
     * 例如: Common.readFile("cert/private_key.pem")
     * 或者直接填入pem内容
     * puk field 和 prk field 分别是用户的公钥和私钥, 这些都是需要填写的
     * 对于Testnet服务，testServerIdn需要配置为true, 但不适用于其他服务.
     * 可以通过实例化配置对象或读取json文件来初始化配置，当前示例是实例化配置对象
     * ------------------------------------------------------------------------------------------
     * 实例化配置对象:
     * Config config = new Config();
     * config.setAppCode("app0001202010221038364886804");
     * config.setUserCode("USER0001202010201539390086090");
     * config.setApi("http://192.168.1.43:17502");
     * config.setPrk(Common.readFile("cert/private_key.pem"));
     * config.setPuk(Common.readFile("cert/public_key.pem"));
     * config.setMspDir("D:/test");
     * config.setTestServerIdn(true);
     * config.initConfig(config);
     * ------------------------------------------------------------------------------------------
     * 通过读取json文件进行初始化:
     * String filePath="config/config.json";
     * Config config=Config.buildByConfigJson(filePath);
     * config.initConfig(config);
     * ------------------------------------------------------------------------------------------
     */

    //初始化Config
//    public void initConfig() throws IOException {
//        Config config = new Config();
//        config.setAppCode("app008809d6356825a4c249be16572723c6225");
//        config.setUserCode("USER0001202112061433104102226");
//        config.setApi("http://52.83.209.158:17502");
//        config.setPrk(Common.readFile("cert/private_key.pem"));
//        config.setPuk(Common.readFile("cert/public_key.pem"));
//        config.setMspDir("");
//        config.initConfig(config);
//    }

    // 通过读取json文件进行初始化
    public void initConfig() {
        String filePath="config/config.json";
        Config config=Config.buildByConfigJson(filePath);
        config.initConfig(config);
    }

    /**
     * 用户注册
     * 在密钥信任模式和公钥上传模式下，当用户参与Fabric应用程序并且需要为链外系统的子用户创建单独的用户交易证书时，
     * 需要首先调用此接口在该城市节点中注册用户，
     * 并且用户的用户名为name@appCode在调用参数中
     */
    @Test
    public void userRegister() {
        try {
            initConfig(); //这里为示例，实际使用中，值需在程序生命周期内调用一次即可
            ReqUserRegister register = new ReqUserRegister();
            register.setName("test1");
            register.setSecret("123456");
            ResUserRegister resUserRegister = FabricClient.userRegister(register);
            System.out.println("haha");
            System.out.println(JSONObject.toJSONString(resUserRegister, SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在密钥信任模式下调用链码
     * 链外业务系统连接到BSN网关时，需要根据接口描述在请求消息中添加相应的参数。
     * 调用网关后，网关将返回链码的执行结果。
     * 此接口将直接响应结果，而不等待事务阻塞。用户可以调用“获取事务信息API”，根据事务ID查询块。
     链接:https://www.123pan.com/s/aWd9-KV4uh提取码:xPmG
     */
    @Test
    public void reqChainCode() {
        try {
            initConfig();
            ReqKeyEscrow reqkey = new ReqKeyEscrow();
            String[] args = {"{\"basekey\":\"1261\",\"basevalue\":\"12s3\"}"};
            reqkey.setArgs(args);
            reqkey.setFuncName("set");
            reqkey.setChainCode("cc_630d65b3664c42e29570c3754889a7c4");
//            reqkey.setUserName("test");
            ResKeyEscrow resKeyEscrow = FabricClient.reqChainCode(reqkey);
            System.out.println(JSONObject.toJSONString(resKeyEscrow, SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 公钥上载模式下的用户证书注册
     * 当以公钥上传方式参与应用的用户需要注册子用户时，在完成用户注册界面后，可以调用该界面上传公钥证书申请文件，获取城市节点颁发的子用户证书。
     * 在密钥信任模式下调用此接口时，将返回异常。
     */
    @Test
    public void userEnroll() {
        try {
            initConfig();
            ReqKeyEscrowEnroll r = new ReqKeyEscrowEnroll();
            r.setName("test54");
            r.setSecret("123456");
            ResKeyEscrowEnroll resKeyEscrowEnroll = FabricClient.userEnroll(r);
            System.out.println(JSONObject.toJSONString(resKeyEscrowEnroll, SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在公钥上载模式下调用链码
     * 当公钥上传模式应用程序的用户需要启动从链外系统到链上链码的事务时，他需要在本地组装事务消息并调用此接口来启动事务。
     */
    @Test
    public void nodeTrans() {
        try {
            initConfig();
            ReqKeyEscrow reqkey = new ReqKeyEscrow();
            String[] args = {"{\"baseKey\":\"test20200652\",\"baseValue\":\"this is string \"}"};
            reqkey.setArgs(args);
            reqkey.setFuncName("set");
            reqkey.setChainCode("cc_app0001202111121647396153631_01");
            reqkey.setUserName("test54");
            ResKeyEscrowNo resKeyEscrowNo = FabricClient.nodeTrans(reqkey);
            System.out.println(JSONObject.toJSONString(resKeyEscrowNo, SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取交易信息
     * 链外系统可以使用此接口根据交易ID获取交易的详细信息。
     */
    @Test
    public void getTransaction() {
        try {
            initConfig();
            ReqGetTransaction reqData = new ReqGetTransaction();
            reqData.setTxId("aa2b7510d67e5317cb3d290c3c88d1216715d2b0ba9c7d8ce5a65801c50ca967");
            ResGetTransaction transaction = FabricClient.getTransaction(reqData);
            System.out.println(JSONObject.toJSONString(transaction, SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取事务数据
     * 该接口可供链外系统根据交易ID获取交易数据，交易信息以base64字符串形式返回。
     */
    @Test
    public void reqTransData() {
        try {
            initConfig();
            ReqTransData reqTransData = new ReqTransData();
            reqTransData.setTxId("b033975bcf1e3a20190a5d5a7de6eb461aa8ae98cbc7ebfcf56beb2ee9775930");
            ResTransData resChainCodeCancel = FabricClient.getTransData(reqTransData);
            System.out.println(JSONObject.toJSONString(resChainCodeCancel, SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取块信息
     * 数据上传后，链外业务系统会调用节点网关获取当前交易的区块信息（body.blockInfo）、状态值（body.blockInfo.status）和交易ID（body.blockInfo.txId）
     * 如果状态值为0，则表示交易提交成功，生成区块，用户可以根据交易ID查询区块信息。
     */
    @Test
    public void getBlockInfo() {
        try {
            initConfig();
            ReqGetBlockInformation rbf = new ReqGetBlockInformation();
            rbf.setTxId("aa2b7510d67e5317cb3d290c3c88d1216715d2b0ba9c7d8ce5a65801c50ca967");
            ResGetBlockInformation blockInfo = FabricClient.getBlockInfo(rbf);
            System.out.println(JSONObject.toJSONString(blockInfo, SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取块数据
     * 数据上传后，链外业务系统通过节点网关调用此接口，获取当前交易的区块信息
     */
    @Test
    public void getBlockData() {
        try {
            initConfig();
            ReqGetBlockData reqGetBlockData = new ReqGetBlockData();
            reqGetBlockData.setTxId("e0cffa0b857bae8c00bf63e49a72e0f09384983fb97eb2ef19088048630b2c5b");
            ResGetBlockData resGetBlockData = FabricClient.getBlockData(reqGetBlockData);
            System.out.println(JSONObject.toJSONString(resGetBlockData, SerializerFeature.PrettyFormat));

            org.hyperledger.fabric.protos.common.Common.Block block = org.hyperledger.fabric.protos.common.Common.Block.parseFrom(Base64.getDecoder().decode(resGetBlockData.getBlockData()));
            System.out.println("------------------blockData------------------:\n" + JsonFormat.printer().includingDefaultValueFields().print(block));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取最新的分类账信息
     * 获取应用程序的最新分类帐信息，包括块哈希、上一个块哈希、当前块高度和其他信息。
     */
    @Test
    public void getLedgerInfo() {
        try {
            initConfig();
            ResGetLedgerInfo ledgerInfo = FabricClient.getLedgerInfo();
            System.out.println(JSONObject.toJSONString(ledgerInfo, SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 链码事件注册
     * 如果参与应用的用户需要通过链码事件触发链外业务系统进行后续业务处理，可以调用此接口注册要监听的链码事件。
     */
    @Test
    public void eventRegister() {
        try {
            initConfig();
            ReqChainCodeRegister data = new ReqChainCodeRegister();
            data.setAttachArgs("name=张三&age=20");
            data.setChainCode("cc_app0001202008181653099751659_01");
            data.setEventKey("test");
            data.setNotifyUrl("http://192.168.6.128:8080/api/event/notifyUrl");
            ResChainCodeRegister resChainCodeRegister = FabricClient.eventRegister(data);
            System.out.println(JSONObject.toJSONString(resChainCodeRegister, SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 阻止事件注册
     * 如果参与应用的用户需要通过区块事件触发链外业务系统进行后续业务处理，可以调用此接口注册要监听的区块事件。
     */
    @Test
    public void eventBlockRegister() {
        try {
            initConfig();
            ReqBlockRegister data = new ReqBlockRegister();
            data.setAttachArgs("name=张三&age=20");
            data.setNotifyUrl("http://192.168.6.128:8080/api/event/notifyUrl");
            ResBlockRegister resBlockRegister = FabricClient.eventBlockRegister(data);
            System.out.println(JSONObject.toJSONString(resBlockRegister, SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询链码事件
     * 此接口可以查询已注册事件的列表。
     */
    @Test
    public void eventQuery() {
        try {
            initConfig();
            List<ResChainCodeQuery> resChainCodeQueries = FabricClient.eventQuery();
            System.out.println(JSONObject.toJSONString(resChainCodeQueries, SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除链码事件
     * 此接口可以删除已注册的事件。
     */
    @Test
    public void eventRemove() {
        try {
            initConfig();
            ReqChainCodeRemove reqChainCodeRemove = new ReqChainCodeRemove();
            reqChainCodeRemove.setEventId("ad59c71e3dbc418e8aef8b101addd0cb");
            ResChainCodeRemove resChainCodeRemove = FabricClient.eventRemove(reqChainCodeRemove);
            System.out.println(JSONObject.toJSONString(resChainCodeRemove, SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
