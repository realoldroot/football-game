package com.artemis.football.common;

import com.artemis.football.annotation.ActionMap;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author zhengenshen
 * @date 2018-05-22 11:42
 */

@Component
public class ActionBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private ActionMapUtil actionMapUtil;

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            ActionMap actionMap = method.getAnnotation(ActionMap.class);
            if (actionMap != null) {
                Action action = new Action();
                action.setMethod(method);
                action.setObject(bean);
                actionMapUtil.put(actionMap.value(), action);
            }
        }
        return bean;
    }
}
