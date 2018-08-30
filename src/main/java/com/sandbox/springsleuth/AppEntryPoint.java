package com.sandbox.springsleuth;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AppEntryPoint {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AppEntryPoint.class)
                .initializers(
                        (c) -> c.getBeanFactory()
                                .addBeanPostProcessor(
                                        new CustomPostProcessor()))
                //.child()
                .build()
                .run(args);
        //SpringApplication.run(AppEntryPoint.class, args);
    }

    static class CustomPostProcessor implements BeanPostProcessor {

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            //custom logic here
            return bean;
        }
    }

}
