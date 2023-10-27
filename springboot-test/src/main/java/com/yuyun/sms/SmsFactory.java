package com.yuyun.sms;

import cn.hutool.extra.spring.SpringUtil;
import com.wzkj.common.constant.Constant;
import com.wzkj.modules.sys.service.SysParamsService;
import org.springframework.stereotype.Component;

/**
 * 短信factory
 *
 * @author hyh
 * @since 2023-10-26
 */
@Component
public class SmsFactory {

    private static SysParamsService sysParamsService;

    static {
        SmsFactory.sysParamsService = SpringUtil.getBean(SysParamsService.class);
    }

    public static AbstractSmsService build() {
        //获取配置信息
        MasMsgConfig config = sysParamsService.getValueObject(Constant.SMS_MAS_CONFIG_KEY, MasMsgConfig.class);
        return new MasSmsService(config);
    }
}
