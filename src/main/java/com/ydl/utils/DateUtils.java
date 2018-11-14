package com.ydl.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类
 *
 * @author ydl
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

  /**
   * yyyy-MM-dd HH:mm:ss
   */
  public static final String FORMAT1 = "yyyy-MM-dd HH:mm:ss";
  /**
   * yyyyMMddHHmmss
   */
  public static final String FORMAT2 = "yyyyMMddHHmmss";
  /**
   * yyyy-MM-dd
   */
  public static final String FORMAT3 = "yyyy-MM-dd";
  /**
   * yyyyMMdd
   */
  public static final String FORMAT4 = "yyyyMMdd";
  /**
   * yyyy-MM-dd HH:mm:ss.S
   */
  public static final String FORMAT5 = "yyyy-MM-dd HH:mm:ss.S";
  /**
   * yyyyMMdd
   */
  public static final String FORMAT6 = "yyyyMM";
  /**
   * yyyyMMdd HH:mm:ss
   */
  public static final String FORMAT7 = "yyyyMMdd HH:mm:ss";
  public static final String FORMAT8 = "yyyyMMdd HHmmss";

  /**
   * 得到当前日期字符串 格式（yyyy-MM-dd）
   */
  public static String getDate() {
    return getDate("yyyy-MM-dd");
  }

  /**
   * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
   */
  public static String getDate(String pattern) {
    return DateFormatUtils.format(new Date(), pattern);
  }

  /**
   * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
   */
  public static String formatDate(Date date, Object... pattern) {
    String formatDate;
    if (pattern != null && pattern.length > 0) {
      formatDate = DateFormatUtils.format(date, pattern[0].toString());
    } else {
      formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
    }
    return formatDate;
  }

  /**
   * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
   */
  public static String formatDateTime(Date date) {
    return formatDate(date, "yyyy-MM-dd HH:mm:ss");
  }

  /**
   * 得到当前时间字符串 格式（HH:mm:ss）
   */
  public static String getTime() {
    return formatDate(new Date(), "HH:mm:ss");
  }

  /**
   * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
   */
  public static String getDateTime() {
    return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
  }

  /**
   * 得到当前年份字符串 格式（yyyy）
   */
  public static String getYear() {
    return formatDate(new Date(), "yyyy");
  }

  /**
   * 得到当前月份字符串 格式（MM）
   */
  public static String getMonth() {
    return formatDate(new Date(), "MM");
  }

  /**
   * 得到当天字符串 格式（dd）
   */
  public static String getDay() {
    return formatDate(new Date(), "dd");
  }

  /**
   * 得到当前星期字符串 格式（E）星期几
   */
  public static String getWeek() {
    return formatDate(new Date(), "E");
  }

  /**
   * 得到星期
   *
   * @param date yyyy-MM-d
   */
  public static String getWeek(String date) {
    return formatDate(DateUtils.string2Date(date, FORMAT3), "E");
  }

  /**
   * 获取过去的天数
   */
  public static long pastDays(Date date) {
    long t = System.currentTimeMillis() - date.getTime();
    return t / (24 * 60 * 60 * 1000);
  }

  public static Date getDateStart(Date date) {
    if (date == null) {
      return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 00:00:00");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  public static Date getDateEnd(Date date) {
    if (date == null) {
      return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 23:59:59");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  /**
   * 将date转成string
   *
   * @param date 需转换的日期
   * @param dateFormat 日期格式 如yyyy-MM-dd HH:mm:ss S
   */
  public static String date2String(Date date, String dateFormat) {
    try {
      SimpleDateFormat df = new SimpleDateFormat(dateFormat);
      return df.format(date);
    } catch (NullPointerException e) {
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 将String类型的日期转成date
   */
  public static Date string2Date(String date, String format) {
    SimpleDateFormat df = new SimpleDateFormat(format);
    try {
      return df.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    } catch (NullPointerException e) {
      return null;
    }
  }

  /**
   * 计算指定日期是某年的第几周
   *
   * @return interger
   */
  public static int getWeekNumOfYear(String strDate, String format) throws ParseException {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sformat = new SimpleDateFormat(format);
    Date curDate = sformat.parse(strDate);
    calendar.setTime(curDate);
    int iWeekNum = calendar.get(Calendar.WEEK_OF_YEAR);
    return iWeekNum;
  }

  /**
   * 计算某年某周的开始日期，星期天为每周第一天
   *
   * @return interger
   */
  public static String getFirstDayOfWeek(int yearNum, int weekNum, String format) throws ParseException {

    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, yearNum);
    cal.set(Calendar.WEEK_OF_YEAR, weekNum);
    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    return new SimpleDateFormat(format).format(cal.getTime());
  }

  /**
   * 计算某年某周的结束日期 ，星期六为每周最后一天
   *
   * @return interger
   */
  public static String getLastDayOfWeek(int yearNum, int weekNum, String format) throws ParseException {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, yearNum);
    cal.set(Calendar.WEEK_OF_YEAR, weekNum);
    cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
    return new SimpleDateFormat(format).format(cal.getTime());
  }

  /**
   * 计算指定日期所属周的第一天
   *
   * @param format 接收参数和返回数据日期格式
   */
  public static String getFirstDayOfWeek(String date, String format) throws ParseException {
    return getFirstDayOfWeek(Integer.valueOf(date.substring(0, 4)), getWeekNumOfYear(date, format), format);
  }

  /**
   * 计算指定日期所属周的第一天
   *
   * @param format date日期格式
   * @param format2 返回日期格式
   */
  public static String getFirstDayOfWeek(String date, String format, String format2) throws ParseException {
    return getFirstDayOfWeek(Integer.valueOf(date.substring(0, 4)), getWeekNumOfYear(date, format), format2);
  }

  /**
   * 上一周的第一天
   */
  public static String getFirstDayOfPreWeek(String date, String format) throws ParseException {
    Date d = sumDate(string2Date(date, format), -7);
    return getFirstDayOfWeek(Integer.valueOf(date2String(d, "yyyy")), getWeekNumOfYear(date2String(d, format),
        format), format);
  }

  /**
   * 上一周的最后一天
   */
  public static String getLastDayOfPreWeek(String date, String format) throws Exception {
    Date d = sumDate(string2Date(date, format), -7);
    return getLastDayOfWeek(Integer.valueOf(date2String(d, "yyyy")), getWeekNumOfYear(date2String(d, format),
        format), format);
  }

  /**
   * 计算指定日期所属周的最后一天
   */
  public static String getLastDayOfWeek(String date, String format) throws Exception {
    return getLastDayOfWeek(Integer.valueOf(date.substring(0, 4)), getWeekNumOfYear(date, format), format);
  }

  /**
   * 计算指定日期所属周的最后一天
   *
   * @param format date日期格式
   * @param format2 返回日期格式
   */
  public static String getLastDayOfWeek(String date, String format, String format2) throws Exception {
    return getLastDayOfWeek(Integer.valueOf(date.substring(0, 4)), getWeekNumOfYear(date, format), format2);
  }

  /**
   * 取指定月份最后一天
   */
  public static String getLastDayOfMonth(int year, int month) {
    return getLastDayOfMonth(year, month, "yyyy-MM-dd ");
  }

  /**
   * 取指定月份最后一天
   */
  public static String getLastDayOfMonth(int year, int month, String format) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, year);
    cal.set(Calendar.MONTH, month - 1);
    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
    return new SimpleDateFormat(format).format(cal.getTime());
  }

  /**
   * 取指定日期所属月的最后一天
   */
  public static String getLastDayOfMonth(String date, String format) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(DateUtils.string2Date(date, format));
    return getLastDayOfMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
  }

  /**
   * 取指定日期所属月的最后一天
   */
  public static String getLastDayOfMonth(String date, String format, String format2) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(DateUtils.string2Date(date, format));
    return getLastDayOfMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, format2);
  }

  /**
   * 前一个月的最后一天
   */
  public static String getLastDayOfPreMonth(String date, String format) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(DateUtils.string2Date(date, format));
    return getLastDayOfMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
  }

  /**
   * 取指定月份的第一天
   */
  public static String getFirstDayOfMonth(int year, int month) {
    return getFirstDayOfMonth(year, month, "yyyy-MM-dd ");
  }

  /**
   * 取指定月份的第一天
   */
  public static String getFirstDayOfMonth(int year, int month, String format) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, year);
    cal.set(Calendar.MONTH, month - 1);
    cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
    return new SimpleDateFormat(format).format(cal.getTime());
  }

  /**
   * 取指定日期所属月的第一天
   */
  public static String getFirstDayOfMonth(String date, String format) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(DateUtils.string2Date(date, format));
    return getFirstDayOfMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
  }

  /**
   * 取指定日期所属月的第一天
   *
   * @param format 接收日期格式
   * @param format 返回日期格式
   */
  public static String getFirstDayOfMonth(String date, String format, String format2) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(DateUtils.string2Date(date, format));
    return getFirstDayOfMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, format2);
  }

  /**
   * 前一个月的第一天
   */
  public static String getFirstDayOfPreMonth(String date, String format) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(DateUtils.string2Date(date, format));
    return getFirstDayOfMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
  }

  /**
   * 计算date1和date2相差的秒数（date2-date1）
   *
   * @param date1 较小的日期
   * @param date2 较大的日期
   */
  public static long subDate(Date date1, Date date2) {
    long l = date2.getTime() - date1.getTime();
    return l / 1000;
  }

  /**
   * 计算date1和date2相差的秒数（date2-date1）
   */
  public static long subDate(String date1, String date2, String format) {
    return subDate(string2Date(date1, format), string2Date(date2, format));
  }

  /**
   * 计算两个日期相差天数
   *
   * @param date1 较小的日期
   * @param date2 较大的日期
   */
  public static long daysBetween(Date date1, Date date2) {
    return subDate(date1, date2) / (60 * 60 * 24);
  }

  /**
   * 计算两个日期相差天数
   *
   * @param date1 较小的日期
   * @param date2 较大的日期
   */
  public static long daysBetween(String date1, String date2, String format) {
    return subDate(date1, date2, format) / (60 * 60 * 24);
  }

  /**
   * 计算两个日期相差周数，同一周算0
   */
  public static long weeksBetween(Date date1, Date date2) {
    return daysBetween(date1, date2) / 7;
  }

  /**
   * 计算两个日期之间相差月数，同一个月份算0
   */
  public static long monthsBetween(Date date1, Date date2) {
    int year1 = Integer.valueOf(formatDate(date1, "yyyy"));
    int year2 = Integer.valueOf(formatDate(date2, "yyyy"));
    int month1 = Integer.valueOf(formatDate(date1, "MM"));
    int month2 = Integer.valueOf(formatDate(date2, "MM"));
    return (year2 - year1) * 12 + (month2 - month1);
  }

  /**
   * 计算日期加指定天数后所得日期
   *
   * @param date 原始日期
   * @param day 所加天数（负数时为减）
   */
  public static Date sumDate(Date date, int day) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    // 让日期加1
    calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + day);
    return calendar.getTime();
  }

  /**
   * 计算日期加指定天数后所得日期
   *
   * @param date 原始日期
   * @param day 所加天数（负数时为减）
   */
  public static String sumDate(String date, int day, String format) {
    return date2String(sumDate(string2Date(date, format), day), format);
  }

  /**
   * 日期格式转换
   */
  public static String changeFormat(String date, String format1, String format2) {
    SimpleDateFormat df = new SimpleDateFormat(format1);
    SimpleDateFormat df2 = new SimpleDateFormat(format2);
    try {
      return df2.format(df.parse(date));
    } catch (ParseException e) {
      e.printStackTrace();
      return "";
    }
  }

  /**
   * 参数1日期是否大于参数2
   */
  public static boolean isLager(Date large, Date small) {
    if (large == null) {
      return false;
    } else if (small == null) {
      return true;
    } else {
      return large.getTime() > small.getTime();
    }
  }

  /**
   * 计算当前时间距离本天结束剩余秒数
   */
  public static int getLeftSecond() {
    return Long.valueOf(subDate(new Date(), DateUtils.string2Date(DateUtils.date2String(new Date(), FORMAT4) +
        "235959", FORMAT2))).intValue();
  }

  /**
   * 毫秒数转为日期
   */
  public static String long2Date(Long longDate, String formatString) {
    Date dat = new Date(longDate);
    GregorianCalendar gc = new GregorianCalendar();
    gc.setTime(dat);
    SimpleDateFormat format = new SimpleDateFormat(formatString);
    String sb = format.format(gc.getTime());
    return sb;
  }

  /**
   * 毫秒转日期
   * @param longDate
   * @return
   */
  public static Date long2Date(Long longDate) {
    Date dat = new Date(longDate);
    return dat;
  }


  /**
   * 毫秒字符串转日期
   */
  public static String longStr2Date(String longDate, String formatString) {
    return long2Date(Long.valueOf(longDate.trim()), formatString);
  }

  /**
   * 字符串日期转换成时间戳
   */
  public static long string2long(String datatime, String fm) {
    long time = 0;
    SimpleDateFormat sdf = new SimpleDateFormat(fm);
    try {
      time = sdf.parse(datatime).getTime();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return time;
  }

  /**
   * 根据当前的时间返回当前时间的后半个小时
   */
  public static String getNextHalfHour(String time) {
    if (time.length() == 2) {
      time = time + ":00";
    }
    if (time.equals("23:30")) {
      return "23:30--00:00";
    }
    String[] times = time.split(":");
    if (times[1].equals("30")) {
      int hour = Integer.valueOf(times[0]);
      hour++;
      if (hour < 10) {
        return time + "--0" + hour + ":00";
      } else {
        return time + "--" + hour + ":00";
      }
    } else {
      return time + "--" + times[0] + ":30";
    }
  }

  /**
   * 获取下一个小时
   */
  public static String getNextHour(String time) {
    if (time.length() == 2) {
      time = time + ":00";
    }
    String[] times = time.split(":");
    if (times[0].equals("23")) {
      return time + "--23:" + times[1];
    } else {
      return time + "--" + (Integer.valueOf(times[0]) + 1) + ":" + times[1];
    }
  }

  /**
   * 取下两个小时
   */
  public static String getNextTwoHour(String time) {
    if (time.length() == 2) {
      time = time + ":00";
    }
    String[] times = time.split(":");
    if (times[0].equals("22") || times[0].equals("23")) {
      return time + "--00:" + times[1];
    } else {
      return time + "--" + (Integer.valueOf(times[0]) + 2) + ":" + times[1];
    }
  }

  /**
   * 日期转时间戳
   */
  public static long date2Long(Date date) {
    if (date == null) {
      return 0;
    } else {
      return date.getTime();
    }
  }

  /**
   * 生日计算
   *
   * @param birthday 生日
   * @param relativeDate 相对日期，计算相对这个日期的年龄
   */
  public static int getAgeByBirth(Date birthday, Date relativeDate) {
    int age;
    try {
      Calendar now = Calendar.getInstance();
      // 当前时间
      now.setTime(relativeDate);

      Calendar birth = Calendar.getInstance();
      birth.setTime(birthday);
//如果传入的时间，在当前时间的后面，返回0岁
      if (birth.after(now)) {
        age = 0;
      } else {
        age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
          age += 1;
        }
      }
      return age;
    } catch (Exception e) {

      return 0;
    }
  }

  /**
   * 生日计算
   *
   * @param birthday 生日
   * @param relativeDate 相对日期，计算相对这个日期的年龄
   */
  public static int getAgeByBirth(String birthday, String relativeDate, String format) {
    if (ObjectUtils.isNull(birthday)) {
      return 0;
    } else {
      return getAgeByBirth(string2Date(birthday, format), string2Date(relativeDate, format));
    }
  }

  /**
   * 昨天
   */
  public static String getYesterday(String format) {
    return date2String(sumDate(new Date(), -1), format);
  }

  /**
   * 昨天
   */
  public static Date getYesterday() {
    return sumDate(new Date(), -1);
  }

  public static void main(String[] args) {
    System.out.println(DateUtils.long2Date(1526959313000L, DateUtils.FORMAT1));
  }
}
