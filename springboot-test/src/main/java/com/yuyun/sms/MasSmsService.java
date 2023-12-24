package com.yuyun.sms;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuyun.sms.service.SendSmsInfoService;
import com.yuyun.sms.utils.MasErrorCode;
import com.yuyun.sms.utils.MasResult;
import com.yuyun.sms.utils.MasUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author hyh
 * @since 2023-10-26
 */
@Slf4j
public class MasSmsService extends AbstractSmsService {

    public static String apiAddress = "http://112.35.1.155:1992/sms/norsubmit";

    private static SendSmsInfoService sendSmsInfoService;

    static {
        MasSmsService.sendSmsInfoService = SpringUtil.getBean(SendSmsInfoService.class);
    }

    public MasSmsService(MasMsgConfig config) {
        this.config = config;
    }

    @Override
    public void sendSms(Map<String, String> contentMap) {

        String content = JSON.toJSONString(contentMap);
        String submit = getSubmit(content);
        int status = 4;
        String reportContent = "";
        try {
            reportContent = sendMasSms(submit);
        } catch (Exception e) {
            status = 3;
            String errMsg = StrUtil.join("调用短息平台失败：", e.getMessage());
            log.debug(errMsg);
            throw new RuntimeException("短信发送失败：" + e.getMessage());
        }

        saveLog(content, status, reportContent);
    }

    protected String getSubmit(String content) {
        String mac = MasUtil.MD5(config.getEcName(),
                config.getApId(),
                config.getSecretKey(),
                "",
                content,
                config.getSign(),
                config.getAddSerial()
        );

        JSONObject data = new JSONObject();
        data.put("ecName", config.getEcName());
        data.put("apId", config.getApId());
        data.put("mobiles", "");
        data.put("content", content);
        data.put("sign", config.getSign());
        data.put("addSerial", config.getAddSerial());
        data.put("mac", mac);

        String jsonString = data.toJSONString();
        return getDecodeStr(jsonString);
    }

    protected String getDecodeStr(String jsonString) {
        String decodeStr = "";
        try {
            decodeStr = Base64.encode(jsonString.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("调用短信平台：数据格式转换失败！");
        }

        return decodeStr;
    }

    /**
     * 调用短信平台
     */
    protected String sendMasSms(String decodeStr) {
        String reportContent = "";
        // 调用短信平台
        try {
            String responseStr = HttpUtil.post(apiAddress, decodeStr);

            MasResult masResult = JSON.parseObject(responseStr, MasResult.class);
            reportContent = JSON.toJSONString(masResult);
            if (!masResult.isSuccess() || "".equals(masResult.getMsgGroup()) || !"success".equals(masResult.getRspcod())) {
                String msg = MasErrorCode.getMsg(masResult.getRspcod());
                String errMsg = StrUtil.join("调用短息平台失败，返回结果：", msg);
                reportContent = reportContent + "：" + msg;
                log.debug(errMsg);
                throw new RuntimeException("调用短信平台失败：" + msg);
            }
        } catch (Exception e) {
            String errMsg = StrUtil.join("调用短息平台失败：", e.getMessage());
            log.debug(errMsg);
            throw new RuntimeException("调用短信平台失败" + e.getMessage());
        }

        return reportContent;
    }

    private void saveLog(String content, int status, String reportContent) {

        // 保存日志到数据库
        sendSmsInfoService.save();
    }
}
