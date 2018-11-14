package com.ydl.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 * 基础工具类 Object 转换成各种类型
 *
 * @author Administrator
 */
public class ObjectUtils {

  /**
   * 对象转byte[]
   */
  public static byte[] objectToBytes(Object obj) throws Exception {
    ByteArrayOutputStream bo = new ByteArrayOutputStream();
    ObjectOutputStream oo = new ObjectOutputStream(bo);
    oo.writeObject(obj);
    byte[] bytes = bo.toByteArray();
    bo.close();
    oo.close();
    return bytes;
  }

  /**
   * byte[]转对象
   */
  public static Object bytesToObject(byte[] bytes) throws Exception {
    ByteArrayInputStream in = new ByteArrayInputStream(bytes);
    ObjectInputStream sIn = new ObjectInputStream(in);
    return sIn.readObject();
  }

  /**
   * object转int,非int型返回0
   */
  public static int o2i(Object o) {
    try {
      return Integer.valueOf(Objects.toString(o));
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  /**
   * object转string
   */
  public static String o2s(Object o) {
    return Objects.toString(o);
  }

  /**
   * String转int 非数字类型返回0
   */
  public static int s2i(String str) {
    try {
      return Integer.valueOf(str);
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  /**
   * double转int
   */
  public static int d2i(Double d) {
    return d.intValue();
  }

  /**
   * int转double
   */
  public static double i2d(int i) {
    return Double.valueOf(o2s(i));
  }

  public static String i2s(int i) {
    return Objects.toString(i);
  }

  /**
   * object转double
   */
  public static double o2d(Object o) {
    return Double.valueOf(o2s(o));
  }

  /**
   * object转long
   */
  public static long o2l(Object o) {
    try {
      return Long.valueOf(o2s(o));
    } catch (NumberFormatException e) {
      return 0L;
    }
  }

  /**
   * object转json串，简单结构的可以用这个，但如果是要包到大的json串里的，应该用o2Json，用这个会导致冒号被转义
   */
  public static String o2JsonStr(Object o) {
    return JSONObject.toJSONString(o);
  }

  /**
   * object转json对象
   */
  public static JSONObject o2Json(Object o) {
    return JSON.parseObject(o2JsonStr(o));
  }

  /**
   * string转json对象
   */
  public static JSONObject s2Json(String s) {
    return JSONObject.parseObject(s);
  }

  /**
   * 是否为空
   *
   * @param o 字符串类型对象
   */
  public static boolean isNull(Object o) {
    return o == null || StringUtils.isBlank(Objects.toString(o));
  }

  /**
   * 是否非空
   *
   * @param o 字符串类型对象
   */
  public static boolean isNotNull(Object o) {
    return !isNull(o);
  }

  /**
   * 是否空
   *
   * @param c 集合类型
   */
  public static boolean isNull(Collection<?> c) {
    return c == null || c.size() == 0;
  }

  /**
   * 是否非空
   *
   * @param c 集合类型
   */
  public static boolean isNotNull(Collection<?> c) {
    return !isNull(c);
  }

  /**
   * 数组是否空
   *
   * @param o 数组
   */
  public static boolean isNull(Object[] o) {
    return o == null || o.length == 0;
  }

  /**
   * 数组是否非空
   */
  public static boolean isNotNull(Object[] o) {
    return !isNull(o);
  }

  /**
   * map是否空
   */
  public static boolean isNull(Map<?, ?> map) {
    return map == null || map.isEmpty();
  }

  /**
   * map是否非空
   */
  public static boolean isNotNull(Map<?, ?> map) {
    return !isNull(map);
  }

  /**
   * map转json
   */
  public static JSONObject map2Json(Map<?, ?> map) {
    if (isNull(map)) {
      return new JSONObject();
    } else {
      JSONObject json = new JSONObject();
      map.forEach((key, value) -> json.put(Objects.toString(key), value));
      return json;
    }
  }

  /**
   * util.Date转sql.Date
   */
  public static java.sql.Date date2SqlDate(Date date) {
    if (date == null) {
      return null;
    }
    return new java.sql.Date(date.getTime());
  }

  public static String obj2JsonStr(Object obj) {
    return JSON.toJSONString(obj);
  }

  /**
   * 对象值比较 任意一个为空返回false
   *
   * @param a 对象1
   * @param b 对象2
   */
  public static boolean equals(Object a, Object b) {
    if (a == null || b == null) {
      return false;
    } else {
      return a.equals(b);
    }
  }

  /**
   * 替换Emoji表情
   */
  public static String replaceEmoji(String str) {
    return str == null ? null : str.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "").replaceAll("\n", "");
  }


}
