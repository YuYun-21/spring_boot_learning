package com.yuyun;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HuTool工具测试
 *
 * @author hyh
 * @since 2023-11-15
 */
public class HuToolTest {

    @Test
    void test2() {
        String sfz = "532225197104120935";

        System.out.println("IdcardUtil.isValidCard(sfz) = " + IdcardUtil.isValidCard(sfz));
    }

    @Test
    void dateTest() {
        String dateStr1 = "2017-04-01 22:33:23";
        Date date1 = DateUtil.parse(dateStr1);

        String dateStr2 = "2017-05-01 23:33:23";
        Date date2 = DateUtil.parse(dateStr2);

        // 相差一个月，31天
        long betweenDay = DateUtil.between(date1, date2, DateUnit.DAY);
        System.out.println("betweenDay = " + betweenDay);

        // 相差一个月，31天
        long betweenDays = DateUtil.between(date1, date2, DateUnit.SECOND);
        System.out.println("betweenDay = " + betweenDays);

        String startTime = "2024-01-31 12:00:05";
        String endTime = "2024-01-31 14:15:23";

        DateTime startDate = DateUtil.parse(startTime);
        DateTime endDate = DateUtil.parse(endTime);

        long betweenSecond = DateUtil.between(startDate, endDate, DateUnit.SECOND);
        BigDecimal decimal = BigDecimal.valueOf(betweenSecond);
        BigDecimal multiply = decimal.divide(new BigDecimal("3600"), 2, RoundingMode.DOWN);
        System.out.println("multiply = " + multiply);

        DateTime yesterday = DateUtil.yesterday();
        String yesterdayFormat = DateUtil.formatDate(yesterday);
        System.out.println("yesterdayFormat = " + yesterdayFormat);

        DateTime dateTime = DateUtil.offsetDay(yesterday, -1);
        String yeFormat = DateUtil.formatDate(dateTime);
        System.out.println("yeFormat = " + yeFormat);

        BigDecimal subtract = BigDecimal.ZERO.subtract(BigDecimal.ONE);
        System.out.println("subtract = " + subtract);
        BigDecimal divide1 = subtract.divide(BigDecimal.ONE, 2, RoundingMode.HALF_UP);
        BigDecimal multiply1 = divide1.multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
        System.out.println("divide = " + multiply1);
        System.out.println("divide = " + multiply1 + "%");

        String decimalFormat = NumberUtil.decimalFormat("#0.00%", divide1);
        System.out.println("decimalFormat = " + decimalFormat);

        Date date = new Date();
        System.out.println("DateUtil.beginOfDay(date) = " + DateUtil.beginOfDay(date));
        System.out.println("DateUtil.beginOfHour(date) = " + DateUtil.beginOfHour(date));
        System.out.println("DateUtil.beginOfMinute(date) = " + DateUtil.beginOfMinute(date));
        System.out.println("DateUtil.beginOfMonth(date) = " + DateUtil.beginOfMonth(date));
        System.out.println("DateUtil.beginOfYear(date) = " + DateUtil.beginOfYear(date));
        System.out.println("DateUtil.beginOfSecond(date) = " + DateUtil.beginOfSecond(date));

        System.out.println("DateUtil.endOfMonth(date) = " + DateUtil.endOfMonth(date));

        System.out.println("DateUtil.date(1708225517410L) = " + DateUtil.date(1708225517410L));
        System.out.println("DateUtil.formatDateTime(new Date(1708225517410L)) = " + DateUtil.formatDateTime(new Date(1708225517410L)));

    }

    @Test
    void test7() {
        // 配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setWeightKey("order");
        treeNodeConfig.setIdKey("id");
        // 最大递归深度
        treeNodeConfig.setDeep(3);

        List<DeptDto> list = new ArrayList<>();
        list.add(DeptDto.builder().id(1L).pid(0L).name("云南省").build());
        list.add(DeptDto.builder().id(101L).pid(1L).name("昆明市").build());
        list.add(DeptDto.builder().id(102L).pid(1L).name("曲靖市").build());
        list.add(DeptDto.builder().id(1011L).pid(101L).name("五华区").build());

        // 转换器
        List<Tree<Long>> treeNodes = TreeUtil.build(list, list.get(0).getId(), treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getPid());
                    tree.setWeight(treeNode.getSort());
                    tree.setName(treeNode.getName());
                    // 扩展属性 ...
                    tree.putExtra("extraField", 666);
                    tree.putExtra("other", new Object());
                });

        treeNodes.forEach(tree -> System.out.println("tree = " + tree));
        System.out.println("treeNodes = " + treeNodes);
    }

    @Test
    void test6() {
        String regex = "^(?=(.*\\d))(?=(.*[a-z]))(?=(.*[A-Z]))(?=.*[@#$%^&*!+=]).*$";
        System.out.println("ReUtil.isMatch(regex,\"\") = " + ReUtil.isMatch(regex, "iocAdmin123!"));

        if (!ReUtil.isMatch(regex, "iocAdmin123!")) {
            System.out.println("\"密码必须包含数字、大小写字母、特殊符号\" = " + "密码必须包含数字、大小写字母、特殊符号");
        }
    }

    @Test
    void test5() {
        String html = "<p style=\"margin-top:5.0000pt\">&nbsp;</p><p style=\"margin-right:0.0000pt;\"align=\"center\"><span style=\"font-family:方正仿宋_GBK;\">&nbsp;&nbsp;&nbsp;云南省人民政府研究室</span></p><p style=\"margin-top:5.0000pt;\"align=\"justify\"><span style=\"font-family:'Times New Roman';\">2023年3月29日</span></p><p style=\"margin: 0pt; \"align=\"justify\"><span style=\"font-size: 21.3333px;\">附件：<img border=\"0\"src=\"/edittest/ewebeditor/sysimage/icon16/xls.gif\"><a href=\"/edittest/ewebeditor/uploadfile/20230330101140745.xls\"target=\"_blank\">人员名单.xls</a></span></p><p style=\"margin:0pt;\">&nbsp;</p>";
        // String regex = "(?<=云南省人民政府研究室).*?(<p.*?></p>){0,1}<p.*?>(.*?\\d{4}年\\d{1,2}月\\d{1,2}日.*?)</p>";
        String regex = ".*云南省人民政府研究室(?:&nbsp;|&ensp;|\\s)*\\d{4}年\\d{1,2}月\\d{1,2}日.*";

        String s = HtmlUtil.cleanHtmlTag(html);
        System.out.println("HtmlUtil.cleanHtmlTag(html) = " + s);
        Pattern pattern = Pattern.compile(regex);
        String input = "&nbsp;&nbsp;云南省人民政府研究室2023年考试录用公务员资格复审公告&nbsp;按照《云南省2023年度考试录用公务员公告》，现将云南省人民政府研究室2023年考试录用公务员资格复审工作公告如下：一、资格复审的对象云岭先锋“考录专题网页”进入省政府研究室招录岗位首轮资格复审的人员。因有进入首轮资格复审环节考生放弃，根据公务员考试录用有关规定，以进入该环节人员须具备的条件，按笔试成绩由高到低顺序，从报考同职位的考生中递补资格复审人员1名。后续若因自愿放弃或资格复审不合格等出现需递补的情况，递补人员名单、复审时间及地点将另行通知。递补后参加首轮资格复审人员名单见附件。二、资格复审时间2023年4月3日（星期一）9：00至12：00。三、资格复审地点云南省昆明市五华区华山南路74号省政府研究室宿舍旁。四、资格复审所需材料（一）笔试准考证（2份）；（二）本人有效期内的身份证原件及复印件（2份）；（三）所在学校或者单位盖章的报名推荐表、报名登记表原件及复印件；（四）学历学位证书原件及复印件，学历学位网上认证报告；（五）两年以上基层工作经历证明；（六）其他相关证明材料。五、资格复审注意事项（一）考生本人在规定的时间内按要求携带相关证件及材料到指定地点进行现场资格审核。经资格审核合格的考生方可参加面试。报名时填写的信息与报考人员所持证明材料不一致，或者提供虚假证明材料，取消面试资格，由此带来的后果由考生本人承担。不能按要求提供证明材料的考生，视为资格复审不合格。未按时参加资格复审的考生，视为自愿放弃。（二）资格复审其他要求参考《云南省2023年度考试录用公务员公告》。（三）资格复审、面试、体检、考察等后续工作期间，将通过考生报名期间预留电话告知考生相关事宜，请考生务必保持通信工具畅通，如因无法联系造成的后果由考生自行承担。&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;云南省人民政府研究室&ensp;&ensp;&ensp;&ensp;&ensp; &ensp;&ensp;&ensp;2023年3月29日附件：进入首轮资格复审人员名单.xls";
        Matcher matcher = pattern.matcher(input);
        System.out.println("ReUtil.isMatch(regex,input) = " + ReUtil.isMatch(regex, input));

        while (matcher.find()) {
            System.out.println("Group 0: " + matcher.group(0)); // 整个匹配
            System.out.println("Group 1: " + matcher.group(1)); // 第一个捕获组
            System.out.println("Group 2: " + matcher.group(2)); // 第一个捕获组
            System.out.println("Group 3: " + matcher.group(3)); // 第一个捕获组
        }


    }

    @Test
    void test31() {
        String input = "机关党委机关党委人事处）";
        String input1 = "机关党委机关党委（人事处）";
        String input2 = "机关党委机关党委（人才办事处）";
        String regex = "(?<!（[^）]{0,4})）(?![^（]*）)";
        String replaceAll = input.replaceAll(regex, "");
        System.out.println("replaceAll = " + replaceAll);
        String replaceAll1 = input1.replaceAll(regex, "");
        System.out.println("replaceAll = " + replaceAll1);
        String replaceAll2 = input2.replaceAll(regex, "");
        System.out.println("replaceAll2 = " + replaceAll2);

        String replace = StrUtil.replace("", regex, matcher -> "");
        System.out.println("replace = " + replace);

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input1);

        while (matcher.find()) {
            System.out.println("Group 0: " + matcher.group(0)); // 整个匹配
            System.out.println("Group 1: " + matcher.group(1)); // 第一个捕获组
            System.out.println("Group 2: " + matcher.group(2)); // 第一个捕获组
            System.out.println("Group 3: " + matcher.group(3)); // 第一个捕获组
        }
    }

    @Test
    void test11() {
        String html = "<h1 style=\"text-align: center; margin: 0pt; text-indent: 44pt; padding: 0pt; line-height: 30pt;\"><span style=\"font-family:方正小标宋_GBK;font-weight:normal;font-size:22.0000pt;\">&nbsp;审议通过《中华人民共和国专利法实施细则（修正草案）》</span></h1><p style=\"margin-top:5.0000pt;margin-right:0.0000pt;margin-bottom:5.0000pt;margin-left:0.0000pt;text-align:left;font-family:Calibri;font-size:12.0000pt;margin-top:0.0000pt;margin-right:0.0000pt;margin-bottom:0.0000pt;margin-left:0.0000pt;text-indent:24.0000pt;padding:0pt 0pt 0pt 0pt ;text-autospace:ideograph-numeric;line-height:30.0000pt;\">&nbsp;</p><p style=\"margin-top:5.0000pt;margin-right:0.0000pt;margin-bottom:5.0000pt;margin-left:0.0000pt;text-align:left;font-family:Calibri;font-size:12.0000pt;margin-top:0.0000pt;margin-right:0.0000pt;margin-bottom:0.0000pt;margin-left:0.0000pt;text-indent:24.0000pt;padding:0pt 0pt 0pt 0pt ;text-autospace:ideograph-numeric;text-align:center;line-height:30.0000pt;\"align=\"center\"><span style=\"font-family:宋体;font-size:12.0000pt;\">来源：新华社</span></p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-indent:21.0000pt;padding:0pt 0pt 0pt 0pt ;text-autospace:ideograph-numeric;text-align:left;line-height:30.0000pt;\">&nbsp;</p><p style=\"margin-top:5.0000pt;margin-right:0.0000pt;margin-bottom:5.0000pt;margin-left:0.0000pt;text-align:left;font-family:Calibri;font-size:12.0000pt;margin-top:0.0000pt;margin-right:0.0000pt;margin-bottom:0.0000pt;margin-left:0.0000pt;text-indent:32.0000pt;padding:0pt 0pt 0pt 0pt ;text-autospace:ideograph-numeric;text-align:justify;text-justify:inter-ideograph;line-height:30.0000pt;\"align=\"justify\"><span style=\"font-family:方正仿宋_GBK;color:rgb(0,0,0);letter-spacing:0.0000pt;text-transform:none;font-style:normal;font-size:16.0000pt;\">新华社北京11月3日电国务院总理李强11月3日主持召开国务院常务会议，研究推动稀土产业高质量发展有关工作，讨论《中华人民共和国国境卫生检疫法（修订草案）》，审议通过《中华人民共和国专利法实施细则（修正草案）》。</span></p>";

        String subBetween = StrUtil.subBetween(html, "来源：", "</p>");
        if (StringUtils.isBlank(subBetween)) {
            subBetween = StrUtil.subBetween(html, "供稿：", "</p>");
            if (StringUtils.isBlank(subBetween)) {
                subBetween = StrUtil.subBetween(html, "出处：", "</p>");
            }

        }

        String contentSource = StringUtils.isBlank(subBetween) ? "" : HtmlUtil.cleanHtmlTag(subBetween);
        String regex = "(?<!（[^）]{0,12})）(?![^（]*）)";
        String replace = StrUtil.replace(contentSource, regex, matcher -> "");
        System.out.println("replace = " + replace);

    }

    @Test
    void test() {
        String s = "0123@4560";
        String substring = s.substring(s.length() - 2);
        System.out.println("substring = " + substring);
        String s1 = StrUtil.subAfter(s, "@", true);
        String s2 = StrUtil.subAfter(s, "@", false);
        System.out.println("s1 = " + s1);
        System.out.println("s2 = " + s2);

        String s3 = StrUtil.removePrefix(s, "0");
        System.out.println("s3 = " + s3);

    }

    @Test
    void HtmlUtilTest() {
        String html = "<p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-autospace:ideograph-numeric;text-align:center;line-height:30.0000pt;\" align=\"center\">&nbsp;</p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-autospace:ideograph-numeric;text-align:center;line-height:30.0000pt;\" align=\"center\">&nbsp;</p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-autospace:ideograph-numeric;text-align:center;line-height:30.0000pt;\" align=\"center\"><span style=\"font-family:宋体;font-size:22.0000pt;\"><font face=\"方正小标宋_GBK\">梁栋</font></span><span style=\"font-family:方正小标宋_GBK;font-size:22.0000pt;\"><font face=\"方正小标宋_GBK\">带队</font></span><span style=\"font-family:宋体;font-size:22.0000pt;\"><font face=\"方正小标宋_GBK\">赴广南县南屏镇</font></span><span style=\"font-family:方正小标宋_GBK;font-size:22.0000pt;\"><font face=\"方正小标宋_GBK\">开展定点帮扶工作</font></span></p><p style=\"margin-left:10.0000pt;text-indent:-10.0000pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-autospace:ideograph-numeric;text-align:center;line-height:30.0000pt;\" align=\"center\">&nbsp;</p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-autospace:ideograph-numeric;text-align:justify;text-justify:inter-ideograph;line-height:30.0000pt;\" align=\"justify\"><span style=\"font-family:宋体;font-size:16.0000pt;\">&ensp;&ensp;2023<font face=\"方正仿宋_GBK\">年</font><font face=\"宋体\">7</font><font face=\"方正仿宋_GBK\">月</font><font face=\"宋体\">27</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">、</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\">28<font face=\"方正仿宋_GBK\">日，省政府研究室党组成员、副主任梁栋率</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">队</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">到广南县南屏镇老街村、小阿幕村</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">，</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">向基层干部群众宣讲党的二十大精神</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">，</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">实地走访农户，了解当地产业建设情况</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">，看望驻村工作队员。</font></span></p><p style=\"margin-left:10.0000pt;text-indent:-10.0000pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt\">&nbsp;</p><p style=\"margin: 0pt 0pt 0.0001pt; text-align: center; font-family: Calibri; font-size: 10.5pt;\"><img width=\"603\" height=\"351\" src=\"/edittest/ewebeditor/uploadfile/20230806234518391001.png\" align=\"center\"></p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-indent:32.0000pt;line-height:30.0000pt;\">&nbsp;</p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-indent:32.0000pt;line-height:30.0000pt;\"><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">调研组一行</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">先后</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">到老街村</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">的</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">小冲</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">和</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">木丁村小组</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">、小阿</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">幕村</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">的</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">小阿幕村小组，走</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">访脱贫</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">农户</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">，</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">向基层干部群众宣讲党的二十大精神</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">，</font></span><span style=\"font-family:'Times New Roman';font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">与镇</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">村干部、</font></span><span style=\"font-family:'Times New Roman';font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">驻村工作队</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">员深入</font></span><span style=\"font-family:'Times New Roman';font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">座谈</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">，</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">了解农民增收</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">、</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">高峰牛养殖</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">专业村和</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">绿美村庄建设</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">进展</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">情况</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">，</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">探讨定点帮扶中出现的新情况新问题。</font></span></p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-indent:32.0000pt;line-height:30.0000pt;\"><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\"><br></font></span></p><p style=\"margin: 0pt 0pt 0.0001pt; text-align: center; font-family: Calibri; font-size: 10.5pt; text-indent: 32.05pt; line-height: 300%;\"><img width=\"604\" height=\"360\" src=\"/edittest/ewebeditor/uploadfile/20230806234519493002.png\" align=\"center\"></p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-indent:32.0000pt;text-autospace:ideograph-numeric;line-height:30.0000pt;\">&nbsp;</p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-indent:0.0000pt;text-autospace:ideograph-numeric;line-height:30.0000pt;\"><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">&ensp;&ensp;&ensp;&ensp;梁栋指出，</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">南屏镇发展高峰牛产业</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">有基础，市场有前景，要系统谋划、稳步推进。</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">一是学习借鉴玉溪市华宁县发展柑橘产业成功经验，探索形成南屏</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">高峰牛产业发展的新模式</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">。二是通过</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">培养</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">致富带头人</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">和</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">引进</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">优质</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">企业</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">，进行</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">规模化</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">、标准化</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">养殖，形成示范带动效应。三是</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">聚力</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">打造在全国叫得响的</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">广南高峰牛</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">品牌</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">，推动高峰牛等农产品走向国内外市场。注重发挥民族特色文化对品牌的支撑作用，创办地域</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">特色</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">鲜明</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">、活动</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">内容</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">丰富的</font>“<font face=\"方正仿宋_GBK\">牛文化</font><font face=\"宋体\">”</font><font face=\"方正仿宋_GBK\">节，运用新媒体进行宣传推广</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">，</font></span><span style=\"font-family:宋体;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">吸引人气和流量</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">。</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">四是抓住全党</font></span><span style=\"font-family:'Times New Roman';font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">开展主题教育</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">的</font></span><span style=\"font-family:'Times New Roman';font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">机会，把习近平新时代中国特色社会主义思想学深学透</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">，用党的创新理论武装头脑、指导实践、推动工作，在巩固脱贫攻坚成果、实施乡村振兴战略中创造佳绩。五是</font></span><span style=\"font-family:'Times New Roman';font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">要</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">充分发挥省政府研究室和驻村工作队员的智力优势，为推动广南县南屏镇乡村振兴工作贡献力量</font></span><span style=\"font-family:'Times New Roman';font-size:16.0000pt;\"><font face=\"方正仿宋_GBK\">。</font></span></p><p style=\"margin-left:10.0000pt;text-indent:-10.0000pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt\">&nbsp;</p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-indent:256.0000pt;text-autospace:ideograph-numeric;line-height:30.0000pt;\"><span style=\"font-family:方正楷体_GBK;font-size:16.0000pt;\"><font face=\"方正楷体_GBK\">&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;（供稿：信息处）</font> &nbsp;</span></p>";
        String content = HtmlUtil.cleanHtmlTag(html);
        System.out.println("content = " + content);
        String removeAny = StrUtil.removeAny(content, "&nbsp;", "&ensp;");
        System.out.println("removeAny = " + removeAny);

        String escape = "&lt;html&gt;&lt;body&gt;&nbsp;123&#039;123&#039;&nbsp;&lt;/body&gt;&ensp;&lt;&ensp;/html&gt;";
        // 结果为：<html><body>123'123'</body></html>
        String escapeHtml = HtmlUtil.unescape(escape);
        System.out.println("escapeHtml = " + escapeHtml);

        String unescapeHtml = HtmlUtil.unescape(html);
        System.out.println("unescapeHtml = " + unescapeHtml);
        String unescape = HtmlUtil.cleanHtmlTag(unescapeHtml);
        System.out.println("unescape = " + unescape);

        Pattern compile = Pattern.compile("(?<=[\\u4e00-\\u9fa5])(?:&nbsp;|&ensp;)+");
        String replace = StrUtil.replace(content, compile, matcher -> "；");
        System.out.println("replace = " + replace);
        String unescapeReplace = HtmlUtil.unescape(replace);
        System.out.println("unescapeReplace = " + unescapeReplace);

        Pattern compile1 = Pattern.compile("</font>.*(?=</font></span>)");
        String replace1 = StrUtil.replace(html, compile1, matcher -> HtmlUtil.unwrapHtmlTag(matcher.group(), "font"));
        System.out.println("replace1 = " + replace1);

        // 包含多个 span 标签的输入字符串
        String input = "<div data-v-4f8cd50c=\"\" class=\"c_left\"><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-autospace:ideograph-numeric;line-height:30.0000pt;\">&nbsp;</p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-autospace:ideograph-numeric;line-height:30.0000pt;\">&nbsp;</p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-autospace:ideograph-numeric;text-align:center;line-height:30.0000pt;\" align=\"center\"><span style=\"font-family:方正小标宋_GBK;font-size:22.0000pt;\">云南省人民政府研究室党组</span><span style=\"font-family:方正小标宋_GBK;font-size:22.0000pt;\">理论学习中心组</span></p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-autospace:ideograph-numeric;text-align:center;line-height:30.0000pt;\" align=\"center\"><span style=\"font-family:方正小标宋_GBK;font-size:22.0000pt;\">组织开展</span><span style=\"font-family:方正小标宋_GBK;font-size:22.0000pt;\">2022年第8次学习</span></p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-autospace:ideograph-numeric;line-height:30.0000pt;\">&nbsp;</p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-indent:32.0000pt;text-autospace:ideograph-numeric;line-height:30.0000pt;\"><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\">8月26日，云南省人民政府研究室党组理论学习中心组组织开展第8次集中学习，</span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\">学习习近平</span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\">生态文明思想和省委、省政府</span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\">绿美云南建设</span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\">决策部署</span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\">，全室党员干部参加集中学习。集中学习研讨会上，收听收看省委理论学习中心组2022年第五次集中学习录音和PPT。</span></p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-indent:32.0000pt;text-autospace:ideograph-numeric;line-height:30.0000pt;\"><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\">会议指出，习近平生态文明思想，深刻回答了为什么建设生态文明、建设什么样的生态文明、怎样建设生态文明等重大理论和实践问题，深刻阐释了人与自然的关系、环境与民生的关系、发展与保护的关系、自然生态各要素之间的关系、国内与国际的关系，</span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\">是习近平新时代中国特色社会主义思想的重要组成部分</span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\">。</span></p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-indent:32.0000pt;text-autospace:ideograph-numeric;line-height:30.0000pt;\"><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\">会议强调，学深悟透习近平生态文明思想是做好以文辅政工作的基础和前提，要牢固树立全局观念、系统思维，在学深悟透习近平生态文明思想核心要义的基础上，深刻认识和理解保护生态环境是关系党的使命宗旨的重大政治问题和关系民生的重大社会问题，深刻认识和理解保护生态环境与经济社会发展之间辩证统一关系，跳出生态文明看生态文明，把推进生态文明建设放在自然规律、人类文明发展和经济社会发展的大背景下理解和把握，</span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\">为云南主动融入和服务国家战略、参与全球生态文明建设多出好点子、金点子</span><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\">。</span></p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-indent:32.0000pt;text-autospace:ideograph-numeric;line-height:30.0000pt;\"><span style=\"font-family:方正仿宋_GBK;font-size:16.0000pt;\">会议强调，开展绿美云南建设，是省委、省政府团结带领全省干部群众，深入学习贯彻落实习近平生态文明思想和习近平总书记考察云南重要讲话精神，紧扣生态文明建设排头兵战略定位，深化创新实践途径的重大举措和重要抓手。省政府研究室要主动担当以文辅政职责使命，聚焦绿美城市建设、以人为核心的新型城镇化等方面加强调查研究，以改革的思维和创新的举措，回答好问题是什么、差距在哪里、目标怎么定、措施有哪些等问题，为推进绿美云南建设作出积极贡献。</span></p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-indent:32.0000pt;text-autospace:ideograph-numeric;line-height:30.0000pt;\"><br></p><p style=\"margin: 0pt 0pt 0.0001pt; text-align: justify; font-size: 10.5pt; text-indent: 32pt; line-height: 30pt;\"><span style=\"font-family: Calibri;\">                                            </span><span style=\"font-family: 仿宋, 仿宋_GB2312;\"><span style=\"font-size: 16pt;\"> </span><span style=\"font-size: 16pt;\">供稿：机关党委（人事处）&nbsp;</span></span></p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-autospace:ideograph-numeric;line-height:30.0000pt;\">&nbsp;</p></div>";
        // 定义匹配所有 span 标签（除第一个）的正则表达式
        Pattern spanPattern = Pattern.compile("</span><span.*?(?=</span>)");

        // 创建匹配器
        Matcher matcher = spanPattern.matcher(input);

        // 移除除第一个 span 标签外的所有其他 span 标签及其内容
        String result = matcher.replaceAll("222");

        // 输出更新后的字符串
        System.out.println("result = " + result);

        String replace2 = StrUtil.replace(input, spanPattern, matcher1 -> HtmlUtil.unwrapHtmlTag(matcher1.group(), "span"));
        System.out.println("replace2 = " + replace2);

        String s = ReUtil.get(spanPattern, input, 0);
        System.out.println("s = " + s);

    }

    @Test
    void test1() {
        String html = "<p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-autospace:ideograph-numeric;text-align:center;line-height:30.0000pt;\" align=\"center\"><span style=\"font-family:方正小标宋_GBK;font-size:22.0000pt;\"><font face=\"方正小标宋_GBK\">云南省人民政府研究室</font></span></p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-autospace:ideograph-numeric;text-align:center;line-height:30.0000pt;\" align=\"center\"><span style=\"font-family:方正小标宋_GBK;font-size:22.0000pt;\">2022年政府信息公开工作年度报告</span></p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-autospace:ideograph-numeric;line-height:30.0000pt;\">&nbsp;</p><p style=\"margin: 0pt 0pt 0.0001pt; text-align: justify; font-size: 10.5pt; text-indent: 32pt; line-height: 30pt;\"><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">根据《中华人民共和国政府信息公开条例》和省政府办公厅《关于</font></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">做好</font></span><span style=\"font-family: &quot;Times New Roman&quot;;\"><span style=\"letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\">202</span><span style=\"letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\">2</span></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">年政府信息公开工作年度报告</font></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">编制发布工作的通知</font></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">》要求，省政府研究室编</font></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">写</font></span><span style=\"letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\" style=\"font-family: 方正仿宋_GBK;\">了</font><span style=\"font-family: &quot;Times New Roman&quot;;\">202</span></span><span style=\"letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255); font-family: &quot;Times New Roman&quot;;\">2</span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">年政府信息公开工作年度报告。本报告所列数据统计</font></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">期</font></span><span style=\"letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\" style=\"font-family: 方正仿宋_GBK;\">限为</font><span style=\"font-family: &quot;Times New Roman&quot;;\">202</span></span><span style=\"letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255); font-family: &quot;Times New Roman&quot;;\">2</span><span style=\"letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\" style=\"font-family: 方正仿宋_GBK;\">年</font><span style=\"font-family: &quot;Times New Roman&quot;;\">1</span><font face=\"方正仿宋_GBK\" style=\"font-family: 方正仿宋_GBK;\">月</font><font style=\"font-family: &quot;Times New Roman&quot;;\">1</font><font face=\"方正仿宋_GBK\" style=\"font-family: 方正仿宋_GBK;\">日至</font><font style=\"font-family: &quot;Times New Roman&quot;;\">202</font></span><span style=\"letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255); font-family: &quot;Times New Roman&quot;;\">2</span><span style=\"letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\" style=\"font-family: 方正仿宋_GBK;\">年</font><span style=\"font-family: &quot;Times New Roman&quot;;\">12</span><font face=\"方正仿宋_GBK\" style=\"font-family: 方正仿宋_GBK;\">月</font><font style=\"font-family: &quot;Times New Roman&quot;;\">31</font><font face=\"方正仿宋_GBK\" style=\"font-family: 方正仿宋_GBK;\">日。</font></span></p><p style=\"margin:0pt;margin-bottom:.0001pt;text-align:justify;text-justify:inter-ideograph;font-family:Calibri;font-size:10.5000pt;text-indent:32.0000pt;text-autospace:ideograph-numeric;line-height:30.0000pt;\"><span style=\"font-family:方正黑体_GBK;letter-spacing:0.0000pt;text-transform:none;font-style:normal;font-size:16.0000pt;background:rgb(255,255,255);\"><font face=\"方正黑体_GBK\">一、总体情况</font></span></p><p style=\"margin: 0pt 0pt 0.0001pt; font-size: 10.5pt; text-indent: 32pt; text-align: justify; line-height: 30pt;\" align=\"justify\"><span style=\"letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><span style=\"font-family: &quot;Times New Roman&quot;;\">2022</span><font face=\"方正仿宋_GBK\" style=\"font-family: 方正仿宋_GBK;\">年，</font></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">省政府</font></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">研究室始终</font></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">坚持以习近平新时代中国特色社会主义思想为指导，把迎接党的二十大召开和学习宣传贯彻党的二十大精神作为全年工作主线，按照党中央、国务院和省委、省政府关于政务公开工作的</font></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">部署要求，</font></span><span style=\"font-family: 方正仿宋_GBK; color: rgb(0, 0, 0); letter-spacing: 0pt; font-weight: normal; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">认真落实</font>2022<font face=\"方正仿宋_GBK\">年我省政务公开工作要点明确的各项任务</font></span><span style=\"font-family: 方正仿宋_GBK; color: rgb(0, 0, 0); letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">，</font></span><span style=\"font-family: 方正仿宋_GBK; color: rgb(0, 0, 0); letter-spacing: 0pt; font-weight: normal; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">不断</font></span><span style=\"font-family: 宋体; color: rgb(0, 0, 0); letter-spacing: 0pt; font-weight: normal; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">提高政务公开标准化规范化水平</font></span><span style=\"font-family: 方正仿宋_GBK; color: rgb(0, 0, 0); letter-spacing: 0pt; font-weight: normal; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">，</font></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">扎实开展政府信息公开工作。</font></span></p><p style=\"margin: 0pt; font-size: 12pt; text-indent: 32pt; text-align: justify; line-height: 29.5pt;\" align=\"justify\"><span style=\"font-family: 方正楷体_GBK; color: rgb(0, 0, 0); font-size: 16pt;\"><font face=\"方正楷体_GBK\">（一）主动公开方面。</font></span><span style=\"font-family: 方正仿宋_GBK; color: rgb(0, 0, 0); letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">省政府研究室</font></span><span style=\"font-family: 方正仿宋_GBK; color: rgb(0, 0, 0); letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">依托</font></span><span style=\"font-family: 方正仿宋_GBK; color: rgb(0, 0, 0); letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">门</font></span><span style=\"font-family: 方正仿宋_GBK; color: rgb(0, 0, 0); letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">户网站发布，聚焦法定公开内容，</font></span><span style=\"font-family: 方正仿宋_GBK; color: rgb(0, 0, 0); letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">持续做好机构信息、</font></span><span style=\"font-family: 方正仿宋_GBK; color: rgb(0, 0, 0); letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">工作动态、</font></span><span style=\"font-family: 方正仿宋_GBK; color: rgb(0, 0, 0); letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">定点帮扶</font></span><span style=\"font-family: 方正仿宋_GBK; color: rgb(0, 0, 0); letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">、机关党建、</font></span><span style=\"font-family: 方正仿宋_GBK; color: rgb(0, 0, 0); letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">公务员</font></span><span style=\"font-family: 方正仿宋_GBK; color: rgb(0, 0, 0); letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">招考</font></span><span style=\"font-family: 方正仿宋_GBK; color: rgb(0, 0, 0); letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">、</font></span><span style=\"font-family: 方正仿宋_GBK; color: rgb(0, 0, 0); letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">调研成果</font></span><span style=\"font-family: 方正仿宋_GBK; color: rgb(0, 0, 0); letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">等</font></span><span style=\"font-family: 方正仿宋_GBK; color: rgb(0, 0, 0); font-size: 16pt;\"><font face=\"方正仿宋_GBK\">政府信息公开工作</font></span><span style=\"font-family: 方正仿宋_GBK; color: rgb(0, 0, 0); letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">。按要求公开</font></span><span style=\"color: rgb(0, 0, 0); letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><span style=\"font-family: &quot;Times New Roman&quot;;\">2022</span><font face=\"方正仿宋_GBK\" style=\"font-family: 方正仿宋_GBK;\">年</font></span><span style=\"font-family: 方正仿宋_GBK; color: rgb(0, 0, 0); letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">省政府研究室</font></span><span style=\"font-family: 方正仿宋_GBK; color: rgb(0, 0, 0); letter-spacing: 0pt; text-transform: none; font-style: normal; font-size: 16pt; background: rgb(255, 255, 255);\"><font face=\"方正仿宋_GBK\">承办的人大建议和政协提案的复文及办理</font></span><span style=\"letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><font face=\"方正仿宋_GBK\" style=\"font-family: 方正仿宋_GBK;\">情况，公开</font><span style=\"font-family: &quot;Times New Roman&quot;;\">2021</span><font face=\"方正仿宋_GBK\" style=\"font-family: 方正仿宋_GBK;\">年</font></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><font face=\"方正仿宋_GBK\">省政府研究室法治政府建设等相关情况。按时发布省政府研究室</font></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><font face=\"方正仿宋_GBK\">和下属</font></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><font face=\"方正仿宋_GBK\">事业</font></span><span style=\"letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><font face=\"方正仿宋_GBK\" style=\"font-family: 方正仿宋_GBK;\">单位</font><span style=\"font-family: &quot;Times New Roman&quot;;\">202</span></span><span style=\"letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: &quot;Times New Roman&quot;;\">1</span><span style=\"letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><font face=\"方正仿宋_GBK\" style=\"font-family: 方正仿宋_GBK;\">年度部门决算、</font><span style=\"font-family: &quot;Times New Roman&quot;;\">202</span></span><span style=\"letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: &quot;Times New Roman&quot;;\">2</span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><font face=\"方正仿宋_GBK\">年度部门预算以及预算执行情况</font></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><font face=\"方正仿宋_GBK\">，</font></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><font face=\"方正仿宋_GBK\">按时发布省政府</font></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><font face=\"方正仿宋_GBK\">研究室</font></span><span style=\"font-family: &quot;Times New Roman&quot;;\"><span style=\"letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\">202</span><span style=\"letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\">1</span></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><font face=\"方正仿宋_GBK\">年政府信息公开工作年报、省政府门户网站工作报</font></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><font face=\"方正仿宋_GBK\">表</font></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><font face=\"方正仿宋_GBK\">。</font></span><span style=\"letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><font face=\"方正仿宋_GBK\" style=\"font-family: 方正仿宋_GBK;\">截止</font><span style=\"font-family: &quot;Times New Roman&quot;;\">202</span></span><span style=\"letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: &quot;Times New Roman&quot;;\">2</span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><font face=\"方正仿宋_GBK\">年</font></span><span style=\"letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: &quot;Times New Roman&quot;;\">12</span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><font face=\"方正仿宋_GBK\">月</font></span><span style=\"letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: &quot;Times New Roman&quot;;\">31</span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><font face=\"方正仿宋_GBK\">日</font></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><font face=\"方正仿宋_GBK\">，</font></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><font face=\"方正仿宋_GBK\">发布信息</font></span><span style=\"letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: &quot;Times New Roman&quot;;\">345</span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><font face=\"方正仿宋_GBK\">条</font></span><span style=\"font-family: 方正仿宋_GBK; letter-spacing: 0pt; font-size: 16pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><font face=\"方正仿宋_GBK\">。</font></span></p>";
        Pattern spanPattern = Pattern.compile("<p[^>]*>(.*?)</p>");
        // 匹配第<span>标签的属性
        Pattern spanStylePattern = Pattern.compile("<span[^>]*\\sstyle=[\"']([^\"']*)[\"'][^>]*>");
        String replace = StrUtil.replace(html, spanPattern, matcher -> HtmlUtil.cleanHtmlTag(matcher.group(0)));
        System.out.println("replace = " + replace);

        String s = ReUtil.get(spanPattern, html, 0);
        System.out.println("s = " + s);

        Matcher matcher = spanPattern.matcher(html);

        while (matcher.find()) {
            System.out.println("Group 0: " + matcher.group(0)); // 整个匹配
            System.out.println("Group 1: " + matcher.group(1)); // 第一个捕获组

        }

        Matcher matcherStyle = spanStylePattern.matcher(html);

        while (matcherStyle.find()) {
            System.out.println("Group 0: " + matcherStyle.group(0)); // 整个匹配
            System.out.println("Group 1: " + matcherStyle.group(1)); // 第一个捕获组
        }

    }

    @Test
    void test3() {
        String html = "<p style=\"font-size:10pt;text-indent:32pt;\"><span style=\"font-family:方正仿宋_GBK;font-size:16pt;\"><font face=\"宋体\">3</font><font face=\"方正仿宋_GBK\">月</font><font face=\"宋体\">17</font><font face=\"方正仿宋_GBK\">日，云南省人民政府研究室党组理论学习中心组举行</font><font face=\"宋体\">2023</font><font face=\"方正仿宋_GBK\">年第一次集中学习，</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16pt;\"><font face=\"方正仿宋_GBK\">学习习近平</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16pt;\"><font face=\"方正仿宋_GBK\">总书记关于中国式现代化的重要论述</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16pt;\"><font face=\"方正仿宋_GBK\">。集中学习邀请省委党校牛建宏教授作专题辅导。</font></span></p><p style=\"font-size:10pt;text-indent:32pt;\"><span style=\"font-family:方正仿宋_GBK;font-size:16pt;\"><font face=\"方正仿宋_GBK\">会议强调，要立足云南实际，担当好以文辅政职责使命，聚焦补短板、强弱项、扬优势、保安全、促团结，锚定</font><font face=\"方正仿宋_GBK\">“</font><font face=\"宋体\">3815</font><font face=\"方正仿宋_GBK\">”战略发展目标加强调查研究，为服务好省委、省政府谱写中国式现代化的云南篇章，贡献云南省人民政府研究室的智慧和力量。</font></span></p>";
        Pattern spanPattern = Pattern.compile("</span><span.*?(?=</span>)");

        Matcher matcher = spanPattern.matcher(html);
        while (matcher.find()) {
            System.out.println("Group 0: " + matcher.group(0)); // 整个匹配
        }

        String unwrapHtmlTagFont = HtmlUtil.unwrapHtmlTag(html, "font");
        String unwrapHtmlTag = StrUtil.replace(unwrapHtmlTagFont, spanPattern, mat -> HtmlUtil.unwrapHtmlTag(mat.group(), "span"));
        System.out.println("unwrapHtmlTag = " + unwrapHtmlTag);

    }

    @Test
    void test4() {
        String html = "<p style=\"font-size:10pt;text-indent:32pt;\"><span style=\"font-family:方正仿宋_GBK;font-size:16pt;\"><font face=\"宋体\">3</font><font face=\"方正仿宋_GBK\">月</font><font face=\"宋体\">17</font><font face=\"方正仿宋_GBK\">日，云南省人民政府研究室党组理论学习中心组举行</font><font face=\"宋体\">2023</font><font face=\"方正仿宋_GBK\">年第一次集中学习，</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16pt;\"><font face=\"方正仿宋_GBK\">学习习近平</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16pt;\"><font face=\"方正仿宋_GBK\">总书记关于中国式现代化的重要论述</font></span><span style=\"font-family:方正仿宋_GBK;font-size:16pt;\"><font face=\"方正仿宋_GBK\">。集中学习邀请省委党校牛建宏教授作专题辅导。</font></span></p><p style=\"font-size:10pt;text-indent:32pt;\"><span style=\"font-family:方正仿宋_GBK;font-size:16pt;\"><font face=\"方正仿宋_GBK\">会议强调，要立足云南实际，担当好以文辅政职责使命，聚焦补短板、强弱项、扬优势、保安全、促团结，锚定</font><font face=\"方正仿宋_GBK\">“</font><font face=\"宋体\">3815</font><font face=\"方正仿宋_GBK\">”战略发展目标加强调查研究，为服务好省委、省政府谱写中国式现代化的云南篇章，贡献云南省人民政府研究室的智慧和力量。</font></span></p>";
        Pattern spanPattern = Pattern.compile("<p[^>]*>(.*?)</p>");
        // 匹配第<span>标签的属性
        Pattern spanStylePattern = Pattern.compile("<span[^>]*\\sstyle=[\"']([^\"']*)[\"'][^>]*>");
        String replace = StrUtil.replace(html, spanPattern, matcher -> HtmlUtil.cleanHtmlTag(matcher.group(0)));
        System.out.println("replace = " + replace);

        Matcher matcher = spanStylePattern.matcher(html);

        while (matcher.find()) {
            System.out.println("Group 0: " + matcher.group(0)); // 整个匹配
            System.out.println("Group 1: " + matcher.group(1)); // 第一个捕获组
        }

        Pattern pattern = Pattern.compile("<p[^>]*\\sstyle=[\"']([^\"']*)[\"'][^>]*>");
        String string = ReUtil.get(pattern, html, 1);
        System.out.println("string = " + string);
    }

}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class DeptDto {
    private Long id;
    private Long pid;
    private String name;
    private Integer sort;
}