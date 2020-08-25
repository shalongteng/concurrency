package com.slt.concurrency.mooc.immutable;

import com.google.common.collect.Maps;
import com.slt.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

@Slf4j
@ThreadSafe
public class ImmutableExample02 {

    private static Map<Integer, Integer> map = Maps.newHashMap();

    static {
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
        //map 就不能被修改了
        map = Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) {
        map.put(10, 3);
        log.info("{}", map.get(1));
    }

}
