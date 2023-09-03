package com.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//分页查询配置
@Configuration
public class MpConfig {

    @Bean
    public MybatisPlusInterceptor pageInterceptor(){
        //1.定义拦截器
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        //2.添加具体的拦截器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}
