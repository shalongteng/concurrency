package com.slt.concurrency.springbootThreadPool;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author slt
 * @date 2018/10/12
 * @description  直接使用@Async注解，并声明线程池，即可使用多线程；
 */
@RestController
public class ThreadController {

    @Autowired
    private AsyncService asyncService;

    /**
     * executeAsync executeAsync2 执行先后顺序不定
     *
     * @return
     */
    @GetMapping("/async")
    public String async(){

        //调用service层的任务
        asyncService.executeAsync();
//        //从线程池 拿到一个线程 执行executeAsync2
//        asyncService.executeAsync2();
//
//        asyncService.executeAsync3();
        System.out.println("123");
        return "OK";
    }


}
