package cn.ffast.core.utils;


import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class FStringUtil {

    /**
     * 将字符串source根据separator分割成字符串数组
     *
     * @param source
     * @param separator
     * @return
     */
    public static List<String> listSplit(String source, String separator) {
        if (source == null) {
            return null;
        }
        int i = 0;
        List<String> list = new ArrayList<>(StringUtils.countMatches(source, separator) + 1);
        while (source.length() > 0) {
            String value = StringUtils.substringBefore(source, separator);
            if (!StringUtils.isEmpty(value)) {
                list.add(value);
            }

            source = StringUtils.substringAfter(source, separator);
        }
        return list;
    }

    /**
     * 将字符串source根据separator分割成字符串数组
     *
     * @param source
     * @param separator
     * @return
     */
    public static String[] split(String source, String separator) {
        String[] distArray = {};
        if (source == null) {
            return null;
        }
        int i = 0;
        distArray = new String[StringUtils.countMatches(source, separator) + 1];
        while (source.length() > 0) {
            String value = StringUtils.substringBefore(source, separator);
            distArray[i++] = StringUtils.isEmpty(value) ? null : value;
            source = StringUtils.substringAfter(source, separator);
        }
        if (distArray[distArray.length - 1] == null) {// 排除最后一个分隔符后放空
            distArray[distArray.length - 1] = null;
        }
        return distArray;
    }

    /**
     * 判断str最后一个字符是否包含str2是的话删除
     * @param str
     * @param str2
     * @return
     */
    public static String removeLast(String str, String str2) {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(str2)) {
            return null;
        }
        if (str2.equals(str.substring(str.length() - 1))) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * 将字符串source根据全局变量GobelConstants.SPLIT_SEPARATOR分割成字符串数组
     *
     * @param source
     * @return
     */
    public static String[] split(String source) {
        return split(source, ",");
    }


    public static void main(String[] args) {

    }

}
