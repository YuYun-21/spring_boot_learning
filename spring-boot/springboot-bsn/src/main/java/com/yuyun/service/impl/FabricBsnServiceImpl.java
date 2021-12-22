package com.yuyun.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bsnbase.sdk.client.fabric.FabricClient;
import com.bsnbase.sdk.entity.config.Config;
import com.bsnbase.sdk.entity.req.fabric.ReqKeyEscrow;
import com.bsnbase.sdk.entity.res.fabric.ResKeyEscrow;
import com.yuyun.dto.BsnHistoryDTO;
import com.yuyun.dto.CompanyDTO;
import com.yuyun.service.FabricBsnService;
import com.yuyun.utils.Base64Utils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author hyh
 */
@Log4j2
@Service
public class FabricBsnServiceImpl implements FabricBsnService {

    /**
     * 初始化config
     */
    public void initConfig() {
        String filePath = "config/config.json";
        Config config = Config.buildByConfigJson(filePath);
        config.initConfig(config);
    }

    public String reqChainCode(String key, String funcName, String body) {
        try {
            initConfig();
            ReqKeyEscrow keyEscrow = new ReqKeyEscrow();

            String[] args;
            if (StringUtils.isNotBlank(body)) {
                args = new String[]{key, body};
            } else {
                args = new String[]{key};
            }

            keyEscrow.setArgs(args);
            keyEscrow.setFuncName(funcName);
            //链码部署名称，在我参与的服务信息中可以找到
            keyEscrow.setChainCode("cc_630d65b3664c42e29570c3754889a7c4");

            keyEscrow.setTransientData(new HashMap<>());
            ResKeyEscrow resKeyEscrow = FabricClient.reqChainCode(keyEscrow);
            if (200 == resKeyEscrow.getCcRes().getCcCode()) {
                String str = resKeyEscrow.getCcRes().getCcData();
                if ("null".equals(str)) {
                    return null;
                }
                return str;
            }
        } catch (Exception e) {
            throw new RuntimeException("区块链访问失败：" + e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean save(String key, String body) {
        log.info("key：" + key);
        String s = reqChainCode(key, "Set", body);
        return StringUtils.isNotBlank(s);
    }

    @Override
    public CompanyDTO query(String key) {
        String queryStr = reqChainCode(key, "Query", null);
        if (StringUtils.isBlank(queryStr)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(queryStr);

        return jsonObject.toJavaObject(CompanyDTO.class);
    }

    @Override
    public List<BsnHistoryDTO> getHistory(String key) throws IOException {
        String historyStr = reqChainCode(key, "History", null);
        if (StringUtils.isBlank(historyStr)) {
            return new ArrayList<>();
        }
        JSONArray jsonArray = JSONArray.parseArray(historyStr);

        List<BsnHistoryDTO> bsnHistoryDTOList = jsonArray.toJavaList(BsnHistoryDTO.class);
        for (BsnHistoryDTO b : bsnHistoryDTOList) {
            if (null != b) {
                String value = Base64Utils.Base64Decode(b.getValue().getBytes());
                if (StringUtils.isNotBlank(value)) {
                    JSONObject jsonObject = JSONObject.parseObject(value);
                    CompanyDTO companyDTO = jsonObject.toJavaObject(CompanyDTO.class);
                    b.setCompanyDTO(companyDTO);
                }
                b.setValue("");
            }
        }

        return bsnHistoryDTOList;
    }

    @Override
    public Boolean delete(String key) {
        String s = reqChainCode(key, "Delete", null);
        return "success".equals(s);
    }

}
