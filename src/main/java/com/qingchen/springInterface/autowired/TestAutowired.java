package com.qingchen.springInterface.autowired;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName TestAutowired
 * @description:
 * @author: WangChen
 * @create: 2020-07-23 16:50
 **/
@Component
public class TestAutowired implements ApplicationRunner, BeanNameAware {

    @Autowired
    private List<IEmpty> list;

    private String name;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        list.forEach(IEmpty::exec);
        System.out.println("beanName = " + name);
    }

    @Override
    public void setBeanName(String s) {
        name = s;
    }
}
