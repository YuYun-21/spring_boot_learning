package com.yuyun.ocr;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.IdUtil;
import com.yuyun.task.DynamicCronTask;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import nuonuo.open.sdk.NNOpenSDK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ocr控制器
 *
 * @author hyh@date 2025-05-21
 */
@RestController
@RequestMapping("ocr")
public class OcrController {

    public static void main(String[] args) {

        //String merchantToken = NNOpenSDK.getIntance().getMerchantToken("appKey","appSecret");

        String code = "code";
        String redirectUri = "redirectUri";
        //String iSVToken = NNOpenSDK.getIntance().getISVToken("appKey","appSecret","code","taxnum","redirectUri");


        NNOpenSDK sdk = NNOpenSDK.getIntance();
        // ISV下授权商户税号，自用型应用置""即可
        String taxNum = "";
        String appKey = "79747679";
        String appSecret = "F004E1E6C4E94253";
        // API方法名
        String method = "nuonuo.OpeMplatform.invoiceInspection";
        // 访问令牌
        String token = "2e73f14cc186c113abaee70hn9vxyo6s";
        // API私有请求参数

        // optionField
        // 专用发票/数电纸专/机动车发票/数电机动车发票(纸质)：不含税金额
        // 增值税普通发票/普通发票（电子）/普通发票（卷票）/普通发票（通行费）：校验码（后6位）
        // 二手车发票/数电二手车发票(纸质)：车价合计
        // 数电专票/数电普票/数航空/数铁路：价税合计
        // 数电纸普：数电票号码后六位

        // taxNo 企业税号
        // invoiceNo 发票号码(全电票为20）
        // invoiceCode 发票代码(全电票为空）
        // invoiceDate 开票日期 （格式:yyyy-MM-dd）
        String content = "{\"optionField\": \"102460\",\"taxNo\": \"915301115551499841\",\"invoiceNo\": \"25537000000148553858\",\"invoiceDate\": \"2025-06-15\",\"invoiceCode\": \"\"}";
        //SDK请求地址
        String url = "https://sdk.nuonuo.com/open/v1/services";
        // 唯一标识，由企业自己生成32位随机码
        String senId = UUID.randomUUID().toString().replace("-", "");
        senId = IdUtil.simpleUUID();
        // 请求API
        String json = sdk.sendPostSyncRequest(url, senId, appKey, appSecret, token, taxNum, method, content);

        System.out.println("json = " + json);
    }

    public static void main1(String[] args) {
        Tesseract tesseract = new Tesseract();

        String dataPath = new ClassPathResource("tessdata_best").getAbsolutePath();
        tesseract.setDatapath(dataPath);
        tesseract.setLanguage("chi_sim");
        //tesseract.setLanguage("91530102683678290Y");
        //tesseract.setLanguage("91530102683678290Y");
        //tesseract.setOcrEngineMode(1);
        //tesseract.setPageSegMode(3);
        //tesseract.setVariable("tessedit_char_whitelist", "0123456789");
        try {
            String result = tesseract.doOCR(new File("E:\\project\\Learning-Project\\spring-boot\\springboot-test\\src\\main\\resources\\img\\发票.jpg"));
            System.out.println(result);
            System.out.println(result.trim().replace(" ", ""));

            String invoiceText = result.replace(" ", "");
                    // 提取发票号码
            Pattern invoiceNoPattern = Pattern.compile("发票号码:(\\d+)");
            Matcher invoiceNoMatcher = invoiceNoPattern.matcher(invoiceText);
            String invoiceNo = invoiceNoMatcher.find() ? invoiceNoMatcher.group(1) : "未找到";

            // 提取开票日期
            Pattern datePattern = Pattern.compile("开票日期:(\\d{4}年\\d{2}月\\d{2}日)");
            Matcher dateMatcher = datePattern.matcher(invoiceText);
            String invoiceDate = dateMatcher.find() ? dateMatcher.group(1) : "未找到";

            // 提取第一个纳税人识别号
            Pattern taxIdPattern = Pattern.compile("纳税人识别号:(\\w+)");
            Matcher taxIdMatcher = taxIdPattern.matcher(invoiceText);
            String taxId = taxIdMatcher.find() ? taxIdMatcher.group(1) : "未找到";

            System.out.println("发票号码: " + invoiceNo);
            System.out.println("开票日期: " + invoiceDate);
            System.out.println("纳税人识别号: " + taxId);
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }
    }


}
