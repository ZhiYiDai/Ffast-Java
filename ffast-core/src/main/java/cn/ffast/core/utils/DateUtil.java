package cn.ffast.core.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理函数
 */
public class DateUtil {

    /**
     * 计算两日期相差的天数
     * @param fDate
     * @param oDate
     * @return
     */
    public static int daysOfTwo(Date fDate, Date oDate) {
        long intervalMilli = oDate.getTime() - fDate.getTime();
        int distDay = (int) (intervalMilli / (24 * 60 * 60 * 1000));
        return distDay;
    }

    /**
     * 计算两日期相差的分钟数
     * @param oDate
     * @param fDate
     * @return
     */
    public static long minutesOfTwo(Date oDate, Timestamp fDate) {
        long intervalMilli = oDate.getTime() - fDate.getTime();
        long minutes = intervalMilli / (60 * 1000);
        return minutes;
    }

    /**
     * 按pattern格式化输出Date
     * @param date
     * @param pattern
     * @return
     */
    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 按pattern格式化输出Date
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        return dateToString(date, "yyyy-MM-dd");
    }

    /**
     * 将类型是pattern的日期字符串转化成Date型数据
     * @param pattern
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date toDate(String pattern, String dateString)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(dateString);
    }

    /**
     * 将类型是yyyy-MM-dd的日期字符串转化成Date型数据
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date toDate(String dateString) throws ParseException {
        return toDate("yyyy-MM-dd", dateString);
    }

    /**
     * 将类型是yyyy-MM-dd的日期字符串转化成java.sql.Date型数据
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static java.sql.Date toSqlDate(String dateString)
            throws ParseException {
        return toSqlDate("yyyy-MM-dd", dateString);
    }

    /**
     * 将类型是pattern的日期字符串转化成java.sql.Date型数据
     * @param pattern
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static java.sql.Date toSqlDate(String pattern, String dateString)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = sdf.parse(dateString);
        return new java.sql.Date(date.getTime());
    }

    /**
     * 取得当前时间的Timestamp
     * @return
     */
    public static Timestamp getNowTimestamp() {
        return new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    /**
     * 取得当前时间的Timestamp, 由于SqlServer 时间的精度为1/300秒 将 datetime 值舍入到
     * .000、.003、或 .007 秒的增量
     * @return
     */
    public static Timestamp getNowTimestampSqlServer() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = Calendar.getInstance().getTime();
        try {
            return new Timestamp(df.parse(df.format(now)).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取得当前时间的Timestamp, 由于SqlServer 时间的精度为1/300秒 将 datetime 值舍入到
     * .000、.003、或 .007 秒的增量
     * @return
     */
    public static String getNowTimestampSqlServerStr() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = Calendar.getInstance().getTime();
        return df.format(now);
    }

    /**
     * 取得当前时间的yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getNowTimestampStr() {
        return getNowTimestampStr("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 取得当前时间的yyyy-MM-dd HH:mm:ss
     * @param pattern
     * @return
     */
    public static String getNowTimestampStr(String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        Date now = Calendar.getInstance().getTime();
        return df.format(now);
    }

    /**
     * 将Date转化字符串
     * @param date
     * @param pattern
     * @return
     */
    public static String toDateStr(Date date, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * 计算n小时后的时间
     * @param n
     * @return
     */
    public static Timestamp getNTimestampSqlServer(int n) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + n);
        Date now = calendar.getTime();
        try {
            return new Timestamp(df.parse(df.format(now)).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取得当前时间的Date
     * @return
     */
    public static Date getNowDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 取得当前时间的SqlDate
     * @return
     */
    public static java.sql.Date getNowSqlDate() {
        return new java.sql.Date(Calendar.getInstance().getTimeInMillis());
    }

    /**
     * 日期加一天
     * @param d
     * @param n
     * @return
     */
    public static Date addDay(Date d, int n) {
        try {
            Calendar cd = Calendar.getInstance();
            cd.setTime(d);
            cd.add(Calendar.DATE, n);// 增加一天
            // cd.add(Calendar.MONTH, n);//增加一个月
            return cd.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 日期n秒
     * @param d
     * @param n
     * @return
     */
    public static Date addSecond(Date d, int n) {
        try {
            Calendar cd = Calendar.getInstance();
            cd.setTime(d);
            cd.add(Calendar.SECOND, n);
            return cd.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 比较两个字符串型的日期
     * @param date1
     * @param date2
     * @param pattern
     * @return
     */
    public static int compareDate(String date1, String date2, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            new RuntimeException(e.getMessage());
        }
        return 0;
    }

    /**
     * 日期date1与date2比较
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDate(Date date1, Date date2) {
        try {
            if (date1.getTime() > date2.getTime()) {
                return 1;
            } else if (date1.getTime() < date2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            new RuntimeException(e.getMessage());
        }
        return 0;
    }

    /**
     * 日期与当前日期相比
     * @param date
     * @param pattern
     * @return
     */
    public static int compareNow(String date, String pattern) {
        return compareDate(date, toDateStr(Calendar.getInstance().getTime(), pattern), pattern);
    }

    /**
     * 与现在时间比较（学校时间（yyyy-MM-dd HH:mm））
     * @param date
     * @return
     */
    public static int compareNowInSchool(String date) {
        return compareNow(date, "yyyy-MM-dd HH:mm");
    }

    /**
     * 比较当前日期
     * @param date
     * @return
     */
    public static int compareNow(Date date) {
        return compareDate(date, new Date());
    }

    /**
     *
     * @param dt
     * @return
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }

        return weekDays[w];
    }

    public static void main(String[] args) {
        System.out.println(getWeekOfDate(new Date()));
    }
}
