package com.slt.concurrency.myTest.executor;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Classname ListTest
 * @Date 2019/12/28 15:14
 * @Created by Gavin
 * ____           _
 * / ___| __ ___   _(_)_ __
 * | |  _ / _` \ \ / / | '_ \
 * | |_| | (_| |\ V /| | | | |
 * \____|\__,_| \_/ |_|_| |_|
 */
public class ListTest {
    public static void main(String[] args) {
        List list = new ArrayList();

        for(int i =0;i<=100;i++){
            list.add(i);
        }
        list.isEmpty();
        System.out.println(CollectionUtils.isEmpty(list));

    }
}
