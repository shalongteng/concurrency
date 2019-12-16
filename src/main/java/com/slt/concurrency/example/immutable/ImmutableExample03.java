package com.slt.concurrency.example.immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.slt.concurrency.annoations.ThreadSafe;

@ThreadSafe
public class ImmutableExample03 {

    private final static ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);

    private final static ImmutableSet set = ImmutableSet.copyOf(list);

    private final static ImmutableMap<Integer, Integer> map = ImmutableMap.of(1, 2, 3, 4);

    private final static ImmutableMap<Integer, Integer> map2 = ImmutableMap.<Integer, Integer>builder()
            .put(1, 2).put(3, 4).put(5, 6).build();

    /**
     * @Description: 如果对象可以变成，不可变对象。那么可以将它变为不可变对象，这样线程肯定安全
     * @return:
     * @Author: shalongteng
     * @Date: 2019-12-16
     */
    public static void main(String[] args) {
        System.out.println(map2.get(3));
    }
}
