package com.zwj.springboot.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

public class BeanUtil implements ApplicationContextAware {
    private static Logger log = LoggerFactory.getLogger(BeanUtil.class);
    private static ConfigurableApplicationContext applicationContext;

    public static void createBean(Object o) {
        String beanName = o.getClass().getName() ;
        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
        // 注册bean
        defaultListableBeanFactory.registerSingleton(beanName, o);
        log.info("register bean " + applicationContext.getBean(o.getClass()));
    }

    public static <T> T getBean(Class<T> clazz) {
       return applicationContext.getBean(clazz);
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBeanWithEx(Class<T> clazz) {
        T obj =  applicationContext.getBean(clazz);
        if (obj == null) {
            throw new RuntimeException("no " + clazz.getName() + " bean found");
        }
        return obj;
    }

    @Override public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

}
