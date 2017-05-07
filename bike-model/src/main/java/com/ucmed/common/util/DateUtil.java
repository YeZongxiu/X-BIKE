package com.ucmed.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {

	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	private final static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	private final static SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat MM_dd = new SimpleDateFormat("MM-dd");
	private final static SimpleDateFormat yyyyMMddHHmm = new SimpleDateFormat("yyyyMMddHHmm");

	private final static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private final static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private final static SimpleDateFormat sdf3 = new SimpleDateFormat("HHmm");

	private final static SimpleDateFormat sdf5 = new SimpleDateFormat("HH:mm");

	private final static SimpleDateFormat sdf4 = new SimpleDateFormat("yy-MM-dd HH:mm");

	private final static SimpleDateFormat yyyyMMddhhmmssSSS = new SimpleDateFormat(
			"yyyyMMddhhmmssSSS");

	private final static SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");

	public static String simpleDate3(Date date) {
		if(date == null)
			return "";
		return yyyyMMddHHmmss.format(date);
	}

	public String simpleDate(Date date) {
		if(date == null)
			return "";
		return sdf.format(date);
	}

	public static String simpleDate1(String date1) {
		if(date1 == null)
			return "";
		return sdf.format(date1);
	}

	public static String simpleDate2(Date date) {
		if(yyyyMMddhhmmssSSS == null)
			return null;
		return yyyyMMddhhmmssSSS.format(date);

	}

	/**
	 * 取当前时间
	 * @return
	 */
	public static String getCurrentTime() {

		Calendar calendar = Calendar.getInstance();

		int i = calendar.get(1);
		int j = calendar.get(2) + 1;
		int k = calendar.get(5);
		return "" + i + (j >= 10 ? "" + j : "0" + j) + (k >= 10 ? "" + k : "0" + k);
	}

	public static Date calculateDate(Date startDay, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDay);
		cal.add(Calendar.DAY_OF_YEAR, days);
		return cal.getTime();
	}

	public static Date calculateMonth(Date startDay, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDay);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	/**
	 * String 获取当前时间yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurrentDateTime() {
		return dateToString4(getCurrentDate());
	}

	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * String 获取今天日期yyyyMMdd
	 * @return
	 */
	public static String getToday() {
		Calendar calendar = Calendar.getInstance();
		return DateUtil.getyyyyMMdd(calendar.getTime());
	}

	/**
	 * String 获取明天日期yyyyMMdd
	 * @return
	 */
	public static String getTomorrow() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return DateUtil.getyyyyMMdd(calendar.getTime());
	}

	/**
	 * String 获取几天后日期yyyyMMdd
	 * @return
	 */
	public static String getDateByDays(Integer days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return DateUtil.getyyyyMMdd(calendar.getTime());
	}

	/**
	 * String 获取几天后日期yyyy-MM-dd
	 * @return
	 */
	public static String getDateByDays2(Integer days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return DateUtil.getyyyy_MM_dd(calendar.getTime());
	}

	/**
	 * Date 获取几天后日期yyyyMMdd
	 * @return
	 */
	public static Date getDateDateByDays(Integer days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return calendar.getTime();
	}

	/**
	 * 将日期格式化为字符串
	 * @param date
	 * @return
	 */
	@Deprecated
	public static String dateToString(Date date, String pattern) {
		if(date == null) {
			return "";
		}
		return new SimpleDateFormat(pattern).format(date);
	}

	public static String getyyyyMMdd(Date date) {
		if(date == null)
			return "";
		return yyyyMMdd.format(date);
	}

	public static String getyyyy_MM_dd(Date date) {
		if(date == null)
			return "";
		return yyyy_MM_dd.format(date);
	}

	public static String getMM_dd(Date date) {
		if(date == null)
			return "";
		return MM_dd.format(date);
	}

	public static Date StringToDate(String date) {
		try {
			return yyyyMMdd.parse(date);
		} catch(ParseException e) {
		}
		return new Date();
	}

	/**
	 * String 获取时间 yyyy-MM-dd HH:mm
	 * @param date
	 * @return
	 */
	public static String format1(Date date) {
		if(date == null)
			date = new Date();
		return sdf1.format(date);
	}

	public static String dateToString1(Date date) {
		try {
			return sdf1.format(date);
		} catch(Exception e) {
		}
		return null;
	}

	/**
	 * Date 获取时间 yyyy-MM-dd HH:mm
	 * @param String
	 * @return Date
	 */
	public static Date StringToDate1(String date) {
		try {
			return sdf1.parse(date);
		} catch(ParseException e) {
		}
		return new Date();
	}

	/**
	 * Date 获取时间 yyyy/MM/dd
	 * @param String
	 * @return Date
	 */
	public static Date StringToDate5(String date) {
		try {
			return sdf.parse(date);
		} catch(ParseException e) {
		}
		return new Date();
	}

	public static Date StringToDate6(String date) {
		try {
			return yyyy_MM_dd.parse(date);
		} catch(Exception e) {
		}
		return new Date();
	}

	/**
	 * Date 获取时间yyyyMMddHHmm
	 * @param String
	 *            yyyyMMddHHmm
	 * @return Date
	 */
	public static Date StringToDate2(String date) {
		try {
			return yyyyMMddHHmm.parse(date);
		} catch(ParseException e) {
		}
		return new Date();
	}

	/**
	 * Date 获取时间yyyyMMdd
	 * @param String
	 *            yyyyMMdd
	 * @return Date
	 */
	public static Date StringToDate3(String date) {
		try {
			return yyyyMMdd.parse(date);
		} catch(ParseException e) {
		}
		return new Date();
	}

	public static Date StringToDate4(String date) {
		try {
			return sdf2.parse(date);
		} catch(ParseException e) {
		}
		return new Date();
	}

	/**
	 * Date 获取时间yyyyMMddHHmmss
	 * @param String
	 *            yyyyMMddHHmmss
	 * @return Date
	 */
	public static Date StringToDate7(String date) {
		try {
			return yyyyMMddHHmmss.parse(date);
		} catch(ParseException e) {
		}
		return new Date();
	}

	public static String dateToString4(Date date) {
		try {
			return sdf2.format(date);
		} catch(Exception e) {
		}
		return null;
	}

	public static String getHHmm(Date date) {
		try {
			return sdf3.format(date);
		} catch(Exception e) {
		}
		return null;
	}

	public static String getHHmm1(Date date) {
		try {
			return sdf5.format(date);
		} catch(Exception e) {
		}
		return null;
	}

	public static Date getHHmm1Date(String date) {
		try {
			return sdf5.parse(date);
		} catch(Exception e) {
		}
		return null;
	}

	public static String dateToString2(Date date) {
		try {
			return sdf4.format(date);
		} catch(Exception e) {
		}
		return null;
	}

	public static Integer getWeekId(String date) {
		Date d = new Date();
		try {
			d = yyyyMMdd.parse(date);
		} catch(ParseException e) {
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		Integer i = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return i;
	}

	/**
	 * 获取周几
	 * @param date
	 *            yyyyMMdd
	 * @return
	 */
	public static String getWeek(String date) {
		String week = "";
		switch(getWeekId(date)) {
		case 1:
			week = "星期一";
			break;
		case 2:
			week = "星期二";
			break;
		case 3:
			week = "星期三";
			break;
		case 4:
			week = "星期四";
			break;
		case 5:
			week = "星期五";
			break;
		case 6:
			week = "星期六";
			break;
		case 0:
			week = "星期天";
			break;

		default:
			break;
		}
		return week;
	}

	/**
	 * 时间计算（日）
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date getDate(Date date, int n) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.DAY_OF_MONTH, n);
		return gc.getTime();
	}

	public static Date getDate(Date date, int field, int n) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(field, n);
		return gc.getTime();
	}

	public static void main(String args[]) {
		System.out.print(DateUtil.dateToString1(DateUtil.StringToDate2("20130314" + "2359")));

	}

	public static String getDate(Date date, Date updateTime) {
		String str = "";
		Calendar calendar1 = new GregorianCalendar();
		calendar1.setTime(date);
		Calendar calendar2 = new GregorianCalendar();
		calendar2.setTime(updateTime);
		Date date1 = DateUtil.minuteAdd(date, -1);
		Date date2 = DateUtil.hourAdd(date, -1);
		Date date3 = DateUtil.dayAdd(date, -1);
		Date date4 = DateUtil.dayAdd(date, -2);
		if(updateTime.after(date4)) {
			str = "昨天" + DateUtil.getHHmm1(updateTime);
			if(updateTime.after(date3)) {
				int hour = (24 + calendar1.get(Calendar.HOUR_OF_DAY) - calendar2
						.get(Calendar.HOUR_OF_DAY));
				if(hour > 24) {
					hour = hour - 24;
				}
				str = hour + "小时前";
				if(updateTime.after(date2)) {
					int minute = 60 + calendar1.get(Calendar.MINUTE)
							- calendar2.get(Calendar.MINUTE);
					if(minute > 60) {
						minute = minute - 60;
					}
					str = minute + "分钟前";
					if(updateTime.after(date1)) {
						int second = 60 + calendar1.get(Calendar.SECOND)
								- calendar2.get(Calendar.SECOND);
						if(second > 60) {
							second = second - 60;
						}
						str = second + "秒前";
					}
				}
			}
		} else {
			str = DateUtil.format1(updateTime);
		}
		return str;
	}

	/**
	 * 年的加减
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date yearAdd(Date date, int n) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		cl.add(Calendar.YEAR, n);
		return cl.getTime();
	}

	/**
	 * 月的加减
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date monthAdd(Date date, int n) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		cl.add(Calendar.MONTH, n);
		return cl.getTime();
	}

	/**
	 * 日的加减
	 */
	public static Date dayAdd(Date date, int n) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		cl.add(Calendar.DAY_OF_MONTH, n);
		return cl.getTime();
	}

	/**
	 * 小时的加减
	 */
	public static Date hourAdd(Date date, int n) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		cl.add(Calendar.HOUR_OF_DAY, n);
		return cl.getTime();
	}

	/**
	 * 分钟的加减
	 */
	public static Date minuteAdd(Date date, long n) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		cl.add(Calendar.MINUTE, (int) n);
		return cl.getTime();
	}

	/**
	 * 两个时间相差的分钟
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static long getMinuteBetweenTime(Date time1, Date time2) {
		Calendar cl1 = Calendar.getInstance();
		cl1.setTime(time1);
		Calendar cl2 = Calendar.getInstance();
		cl2.setTime(time2);
		long timeOne = cl1.getTimeInMillis();
		long timeTwo = cl2.getTimeInMillis();
		return (timeTwo - timeOne) / (1000 * 60);
	}

	/**
	 * 判断医患问答的问题是否已过工作时间的6个小时 0:未超过; 1:已超过
	 * @param createTime
	 * @return
	 */
	public static int judgeQuestionTime(Date createTime) {
		Date nowTime = new Date();
		Date yesTime = DateUtil.dayAdd(nowTime, -1);
		Date yes14 = DateUtil.StringToDate4((new SimpleDateFormat("yyyy-MM-dd 14:00:00")
				.format(yesTime)));
		Date yes20 = DateUtil.StringToDate4((new SimpleDateFormat("yyyy-MM-dd 20:00:00")
				.format(yesTime)));
		Date now06 = DateUtil.StringToDate4((new SimpleDateFormat("yyyy-MM-dd 06:00:00")
				.format(nowTime)));
		Date now12 = DateUtil.StringToDate4((new SimpleDateFormat("yyyy-MM-dd 12:00:00")
				.format(nowTime)));
		Date now20 = DateUtil.StringToDate4((new SimpleDateFormat("yyyy-MM-dd 20:00:00")
				.format(nowTime)));
		if(createTime.before(yes14)) {
			return 1;
		} else if(createTime.after(yes14) && createTime.before(yes20)) {
			Long t1 = yes20.getTime() - createTime.getTime();
			if(nowTime.before(now06)) {
				return 0;
			} else if(nowTime.after(now06) && nowTime.before(now12)) {
				Long t = t1 + nowTime.getTime() - now06.getTime();
				if(t > 6 * 60 * 60 * 1000) {
					return 1;
				} else {
					return 0;
				}
			} else if(nowTime.after(now12)) {
				return 1;
			}
		} else if(createTime.after(yes20) && createTime.before(now06)) {
			if(nowTime.before(now06)) {
				return 0;
			} else if(nowTime.after(now06) && nowTime.before(now12)) {
				Long t = nowTime.getTime() - now06.getTime();
				if(t > 6 * 60 * 60 * 1000) {
					return 1;
				} else {
					return 0;
				}
			} else if(nowTime.after(now12)) {
				return 1;
			}
		} else if(createTime.after(now06) && createTime.before(now20)) {
			Long t = nowTime.getTime() - createTime.getTime();
			if(t > 6 * 60 * 60 * 1000) {
				return 1;
			} else {
				return 0;
			}
		} else if(createTime.after(now20)) {
			return 0;
		}
		return 1;
	}

	/**
	 * 判断日期格式:yyyy-MM-dd
	 * @param sDate
	 * @return
	 */
	public static boolean isValidDate(String sDate) {
		String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
		String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
				+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
				+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
				+ "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		if((sDate != null)) {
			Pattern pattern = Pattern.compile(datePattern1);
			Matcher match = pattern.matcher(sDate);
			if(match.matches()) {
				pattern = Pattern.compile(datePattern2);
				match = pattern.matcher(sDate);
				return match.matches();
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 根据星期几获取weekId
	 * @param week
	 *            yyyyMMdd
	 * @return
	 */
	public static Integer getWeekIdByWeek(String week) {
		Integer weekId = 100;
		if("星期一".equals(week)) {
			weekId = 1;
		}
		if("星期二".equals(week)) {
			weekId = 2;
		}
		if("星期三".equals(week)) {
			weekId = 3;
		}
		if("星期四".equals(week)) {
			weekId = 4;
		}
		if("星期五".equals(week)) {
			weekId = 5;
		}
		if("星期六".equals(week)) {
			weekId = 6;
		}
		if("星期日".equals(week)) {
			weekId = 0;
		}
		return weekId;
	}

	public static Date parseDate2(String date) {
		try {
			return sdf4.parse(date);
		} catch(ParseException e) {
		}
		return new Date();
	}

}
