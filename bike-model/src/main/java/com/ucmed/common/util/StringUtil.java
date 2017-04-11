package com.ucmed.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;

public class StringUtil {

	/**
	 * 是否是手机号码
	 * @param phoneNo
	 * @return
	 */
	public static boolean isMobileNO(String phoneNo) {
		Pattern pattern = Pattern.compile("((^1)\\d{10}$)");
		Matcher matcher = pattern.matcher(phoneNo);
		return matcher.matches();
	}

	public static boolean isNotBlank(Object o) {
		if(o != null && !("").equals(o)) {
			return true;
		} else
			return false;
	}

	public static String StringToLetter(String args) {
		String convert = "";
		for(int j = 0; j < args.length(); j++) {
			char word = args.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if(pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert.toUpperCase();
	}

	/**
	 * 截取指定字节长度的字符串，不能返回半个汉字 例如： 如果网页最多能显示17个汉字，那么 length 则为 34
	 * StringTool.getSubString(str, 34);
	 * @param str
	 * @param length
	 * @return
	 */
	public static String getSubString(String str, int length) {
		int count = 0;
		int offset = 0;
		char[] c = str.toCharArray();
		for(int i = 0; i < c.length; i++) {
			if(c[i] > 256) {
				offset = 2;
				count += 2;
			} else {
				offset = 1;
				count++;
			}
			if(count == length) {
				return str.substring(0, i + 1);
			}
			if((count == length + 1 && offset == 2)) {
				return str.substring(0, i);
			}
		}
		return "";
	}

	/**
	 * 为了解决日期可能带时间格式问题
	 * @param date
	 * @return
	 */
	public static String foramtDate(String date) {

		if(date.indexOf(" ") != -1) {
			return date.split(" ")[0];
		}
		return date;
	}
}
