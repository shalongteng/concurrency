package com.slt.concurrency.mooc.immutable;

import com.google.common.collect.Maps;
import com.slt.concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @ProjectName: concurrency
 * @Package: com.slt.concurrency.example.immutable
 * @ClassName: Immutable01
 * @Author: shalongteng
 * @Description: ${description}
 * @Date: 2019-12-16 21:22
 * @Version: 1.0
 */
@Slf4j
@NotThreadSafe
public class Immutable01 {
    private final static Integer a = 1;
    private final static String b = "2";
    //final 修饰对象，怎该引用不可以指向其他对象
    private final static Map<Integer, Integer> map = Maps.newHashMap();

    static {
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
    }

    public static void main(String[] args) {
//        a = 2;
//        b = "3";
//        map = Maps.newHashMap();
        map.put(1, 3);
        log.info("{}", map.get(1));
    }

    private void test(final int a) {
//        a = 1;
    }
}
