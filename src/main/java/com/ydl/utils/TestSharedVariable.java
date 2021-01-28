package com.ydl.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.spark.Accumulator;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.broadcast.Broadcast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ydl
 * @since 2020/10/9
 */
public class TestSharedVariable implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(TestSharedVariable.class);
    private static String staticVariable = "apple";
//    private static Broadcast<String> staticBroadcast;  //java.lang.NullPointerException
//    private static Accumulator<Integer> staticAccumulator;

    private String objectVariable = "apple";
    private Broadcast<String> objectBroadcast;
    private Accumulator<Integer> objectAccumulator;


    public void testVariables(JavaSparkContext sc) throws Exception {
        staticVariable = "banana";
//        staticBroadcast = sc.broadcast("banana");
//        staticAccumulator = sc.intAccumulator(0);

        objectVariable = "banana";
        objectBroadcast = sc.broadcast("banana");
        objectAccumulator = sc.intAccumulator(0);

        String localVariable = "banana";
        accessVariables(sc, localVariable);

        staticVariable = "cat";
//        staticBroadcast = sc.broadcast("cat");
        objectVariable = "cat";
        objectBroadcast = sc.broadcast("cat");
        localVariable = "cat";
        accessVariables(sc, localVariable);
    }

    public void accessVariables(JavaSparkContext sc, final String localVariable) throws Exception {
        final Broadcast<String> localBroadcast = sc.broadcast(localVariable);
        final Accumulator<Integer> localAccumulator = sc.intAccumulator(0);

        List<String> list = Arrays.asList("machine learning", "deep learning", "graphic model");
        JavaRDD<String> rddx = sc.parallelize(list).flatMap(new FlatMapFunction<String, String>() {

            @Override
            public Iterator<String> call(String s) throws Exception {
                List<String> list = new ArrayList<String>();

                if (s.equalsIgnoreCase("machine learning")) {
                    list.add("staticVariable:" + staticVariable);
                    list.add("objectVariable:" + objectVariable);
                    list.add("objectBroadcast:" + objectBroadcast.getValue());
                    list.add("localVariable:" + localVariable);
                    list.add("localBroadcast:" + localBroadcast.getValue());
                }

//                staticAccumulator.add(1);
                objectAccumulator.add(1);
                localAccumulator.add(1);

                return list.iterator();
            }
        });


        String desPath = "learn" + localVariable;
        //HdfsOperate.deleteIfExist(desPath);
        //HdfsOperate.openHdfsFile(desPath);
        //List<String> resultList = rddx.collect();
        //for (String str : resultList) {
        //    HdfsOperate.writeString(str);
        //}
        //HdfsOperate.writeString("objectAccumulator:" + objectAccumulator.value());
        //HdfsOperate.writeString("localAccumulator:" + localAccumulator.value());
        //HdfsOperate.closeHdfsFile();
    }

}
