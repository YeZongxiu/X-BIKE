package com.ucmed.common.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ucmed on 2017/3/15.
 */
public class SysUtil {
    private static final Logger LOG = LoggerFactory.getLogger(SysUtil.class);

    private static final Pattern PHONE_PATTERN = Pattern.compile("1(3|4|5|8)\\d{9}");

    /**
     * @param str
     * @return
     */
    public static boolean sqlInject(String str) {
        if(str == null || str.trim().equals("")) {
            return false;
        }

        String injectStr = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";

        String injectStrArray[] = injectStr.split("\\|");
        for(int i = 0; i < injectStrArray.length; i++) {
            if(str.indexOf(injectStrArray[i]) >= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取一个随机字符串。
     * @param length
     *            随机字符串的长度
     * @param model
     *            0 数字 1 字母 2 数字加字母
     * @return 一个随机字符串
     */
    public static String getRandomString(int length, int model) {
        if(length < 1) {
            return "";
        }

        if(model < 0 || model > 2) {
            model = 0;
        }

        StringBuffer sb = new StringBuffer(length);

        for(int i = 0; i < length; i++) {
            if(model == 0) {
                sb.append(genRandomDigit(i != 0));
            } else if(model == 1) {
                sb.append(genRandomLetter());
            } else if(model == 2) {
                sb.append(genRandomChar(i != 0));
            }

        }

        return sb.toString();
    }

    /**
     * 获取一个随机字符（允许数字0-9和小写字母）。
     * @return 一个随机字符
     */
    private static char genRandomChar(boolean allowZero) {
        int randomNumber = 0;
        do {
            Random r = new Random();
            randomNumber = r.nextInt(36);
        } while((randomNumber == 0) && !allowZero);

        if(randomNumber < 10) {
            return (char) (randomNumber + '0');
        } else {
            return (char) (randomNumber - 10 + 'a');
        }
    }

    public static boolean isCheck1(String str) {
        if(str.equals("0")) {
            return true;
        }
        return false;
    }

    public static boolean isCheck2(String str) {
        if(str.equals("3")) {
            return true;
        }
        return false;
    }

    public static boolean isBlank(String s) {
        if(s == null || s.equals(""))
            return true;

        else
            return false;
    }

    private static char genRandomLetter() {
        int randomNumber = 0;
        Random r = new Random();
        randomNumber = r.nextInt(26);
        return (char) (randomNumber + 'a');
    }

    /**
     * 获取一个随机字符（只允许数字0-9）。
     * @return 一个随机字符
     */
    private static char genRandomDigit(boolean allowZero) {
        int randomNumber = 0;

        do {
            Random r = new Random();
            randomNumber = r.nextInt(10);
        } while((randomNumber == 0) && !allowZero);

        return (char) (randomNumber + '0');
    }

    /**
     * 去除html标签
     * @param HTMLStr
     * @return
     */
    public static String removeHtmlTag(String HTMLStr) {
        if(StringUtils.isBlank(HTMLStr)) {
            return "";
        }

        String htmlStr = HTMLStr;
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regEx_html = "<[^>]+>";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll("");
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll("");
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll("");
            textStr = htmlStr.replaceAll(" ", "");
            // textStr = htmlStr.replaceAll("<", "<");
            // textStr = htmlStr.replaceAll(">", ">");
            // textStr = htmlStr.replaceAll("®", "®");
            // textStr = htmlStr.replaceAll("&", "&");
            textStr = StringUtils.replace(textStr, "&nbsp;", "");
            textStr = StringUtils.replace(textStr, "\r", "");
            textStr = StringUtils.replace(textStr, "\n", "");
            textStr = StringUtils.trim(textStr);

        } catch(Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }
        return textStr;
    }

    public static boolean isPhoneNumber(String phoneNumber) {
        Matcher matcher = PHONE_PATTERN.matcher(phoneNumber);
        return matcher.matches();
    }


    /**
     * 根据身份证号获取性别
     * @param idCard
     * @return 1 男 2 女
     */
    public static String getGenderByIdCard(String idCard) {
        if(idCard.length() == CHINA_ID_MIN_LENGTH) {
            idCard = conver15CardTo18(idCard);
        }
        String sCardNum = idCard.substring(16, 17);
        if(Integer.parseInt(sCardNum) % 2 != 0) {
            return "1";
        }
        return "2";
    }

    /**
     * 将15位身份证号码转换为18位
     * @param idCard
     *            15位身份编码
     * @return 18位身份编码
     */
    public static String conver15CardTo18(String idCard) {
        String idCard18 = "";
        if(idCard.length() != CHINA_ID_MIN_LENGTH) {
            return null;
        }
        if(isNum(idCard)) {
            // 获取出生年月日
            String birthday = idCard.substring(6, 12);
            Date birthDate = null;
            try {
                birthDate = new SimpleDateFormat("yyMMdd").parse(birthday);
            } catch(ParseException e) {
                LOG.info("", e);
            }
            Calendar cal = Calendar.getInstance();
            if(birthDate != null)
                cal.setTime(birthDate);
            // 获取出生年(完全表现形式,如：2010)
            String sYear = String.valueOf(cal.get(Calendar.YEAR));
            idCard18 = idCard.substring(0, 6) + sYear + idCard.substring(8);
            // 转换字符数组
            char[] cArr = idCard18.toCharArray();
            if(cArr != null) {
                int[] iCard = converCharToInt(cArr);
                int iSum17 = getPowerSum(iCard);
                // 获取校验位
                String sVal = getCheckCode18(iSum17);
                if(sVal.length() > 0) {
                    idCard18 += sVal;
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
        return idCard18;
    }

    /**
     * 将power和值与11取模获得余数进行校验码判断.
     * @param iSum
     *            .
     * @return 校验位
     */
    public static String getCheckCode18(int iSum) {
        String sCode = "";
        switch(iSum % 11) {
            case 10:
                sCode = "2";
                break;
            case 9:
                sCode = "3";
                break;
            case 8:
                sCode = "4";
                break;
            case 7:
                sCode = "5";
                break;
            case 6:
                sCode = "6";
                break;
            case 5:
                sCode = "7";
                break;
            case 4:
                sCode = "8";
                break;
            case 3:
                sCode = "9";
                break;
            case 2:
                sCode = "x";
                break;
            case 1:
                sCode = "0";
                break;
            case 0:
                sCode = "1";
                break;
            default:
                break;
        }
        return sCode;
    }

    /**
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值.
     * @param iArr
     *            数组
     * @return 身份证编码。
     */
    public static int getPowerSum(int[] iArr) {
        int iSum = 0;
        if(power.length == iArr.length) {
            for(int i = 0; i < iArr.length; i++) {
                for(int j = 0; j < power.length; j++) {
                    if(i == j) {
                        iSum = iSum + iArr[i] * power[j];
                    }
                }
            }
        }
        return iSum;
    }

    /**
     * 将字符数组转换成数字数组.
     * @param ca
     *            字符数组
     * @return 数字数组
     */
    public static int[] converCharToInt(char[] ca) {
        int len = ca.length;
        int[] iArr = new int[len];
        try {
            for(int i = 0; i < len; i++) {
                iArr[i] = Integer.parseInt(String.valueOf(ca[i]));
            }
        } catch(NumberFormatException e) {
            LOG.error("", e);
        }
        return iArr;
    }

    /**
     * 数字验证
     * @param val
     * @return 提取的数字。
     */
    public static boolean isNum(String val) {
        return val == null || "".equals(val) ? false : val.matches("^[0-9]*$");
    }

    /** 中国公民身份证号码最小长度。 */
    public static final int CHINA_ID_MIN_LENGTH = 15;

    /** 每位加权因子 */
    public static final int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

    /**
     * 获取访问者真实ip
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String StringAmPmAtoHz(String amPmFlag) {
        String AmPm = "";
        if("A".equals(amPmFlag)) {
            AmPm = "上午";
            return AmPm;
        } else if("P".equals(amPmFlag)) {
            AmPm = "下午";
            return AmPm;
        } else if("D".equals(amPmFlag)) {
            AmPm = "白天";
            return AmPm;
        } else if("N".equals(amPmFlag)) {
            AmPm = "夜间";
            return AmPm;
        }
        return AmPm;
    }

    public static String StringAmPmAtoNum(String amPmFlag) {
        String AmPm = amPmFlag;
        if("A".equals(amPmFlag)) {
            AmPm = "1";
            return AmPm;
        } else if("P".equals(amPmFlag)) {
            AmPm = "2";
            return AmPm;
        } else if("D".equals(amPmFlag)) {
            AmPm = "3";
            return AmPm;
        } else if("N".equals(amPmFlag)) {
            AmPm = "4";
            return AmPm;
        }
        return AmPm;
    }

    public static String getClientSessionId() {
        String randKey = UUID.randomUUID().toString() + "_"
                + System.currentTimeMillis();
        String s = SHA256Util.hash(randKey);
        return s;
    }
}
