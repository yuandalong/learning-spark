package com.ydl.utils;


import com.google.common.collect.Maps;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author ydl
 * @since 2019/2/17
 */
public class Test {

  public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
    Map<String, Integer> map = new HashMap<>(2);
    Field threshold = map.getClass().getDeclaredField("threshold");
    threshold.setAccessible(true);

    System.err.println("threshold0:" + threshold.get(map));
    map.put("1", 1);
    System.err.println("threshold1:" + threshold.get(map));
    map.put("2", 1);
    System.err.println("threshold2:" + threshold.get(map));
    map.put("3", 1);
    System.err.println("threshold3:" + threshold.get(map));
    map.put("4", 1);
    System.err.println("threshold4:" + threshold.get(map));
    map.put("5", 1);
    System.err.println("threshold5:" + threshold.get(map));
    map.put("6", 1);
    System.err.println("threshold6:" + threshold.get(map));
    map.put("7", 1);
    System.err.println("threshold7:" + threshold.get(map));
    map.put("8", 1);
    System.err.println("threshold8:" + threshold.get(map));
    IntStream.rangeClosed(9, 33)
        .forEach(e -> {
          map.put(String.valueOf(e), e);
          if (e == 16 || e == 17 || e == 32 || e == 33) {
            try {
              System.err.println("threshold:" + threshold.get(map));
            } catch (IllegalAccessException e1) {
              e1.printStackTrace();
            }
          }
        });

    List<String> l = new ArrayList<>();
    l.add("a");
    String[] a = new String[l.size()];
    String[] strings = l.toArray(a);
    for (String s :strings){
      System.out.println(s);
    }
  }
}
