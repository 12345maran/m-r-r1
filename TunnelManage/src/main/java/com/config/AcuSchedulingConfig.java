package com.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.SchedulingConfiguration;


//定时配置方法
@Configuration
@ConditionalOnExpression(value = "${task.scheduling}")
@Import(SchedulingConfiguration.class)
public class AcuSchedulingConfig {

}