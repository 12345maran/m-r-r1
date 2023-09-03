package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.Acu;
import com.mapper.AcuMapper;
import com.service.AcuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.Modbus4j.Modbus4jUtils.batchRead02;
import static com.Modbus4j.Modbus4jUtils.readCoilStatus;

@EnableAsync  // 异步的注解
@Service
public class AcuServiceimpl implements AcuService {

    @Autowired
    private AcuMapper acuMapper;

    //添加Acu数据
   // @Scheduled(fixedRate = 3000)  //每3秒执行一次该方法
    public Acu addAcu() {

        double[] ResultRegiser2 = new double[0];
        Acu acu = null;
        try {

            // 02测试
//            Boolean v021 = readInputStatus(1, 0);
//            Boolean v022 = readInputStatus(1, 1);
//            Boolean v023 = readInputStatus(1, 2);
//            System.out.println("v021:" + v021);
//            System.out.println("v022:" + v022);
//            System.out.println("v023:" + v023);
//
//
            // 03测试  对应4x
            //Number v031028 = readHoldingRegister(1, 1027, DataType.TWO_BYTE_INT_SIGNED);// 读取16进制，并自动转为10进制
            //Number v032 = readHoldingRegister(1, 2, DataType.TWO_BYTE_INT_SIGNED);// 同上
            //System.out.println("v031:" + v031028);
            //System.out.println("v032:" + v032);


//
            // 04测试 对应3x
//            Number v041025 = readInputRegisters(1, 1024, DataType.TWO_BYTE_INT_SIGNED);//读取16进制，并自动转为10进制
//            Number v041026 = readInputRegisters(1, 1025, DataType.TWO_BYTE_INT_SIGNED);//
//            Number v041027 = readInputRegisters(1, 1026, DataType.TWO_BYTE_INT_SIGNED);//读取16进制，并自动转为10进制
//            Number v041029 = readInputRegisters(1, 1028, DataType.TWO_BYTE_INT_SIGNED);//
//            Number v041030 = readInputRegisters(1, 1029, DataType.TWO_BYTE_INT_SIGNED);//读取16进制，并自动转为10进制


//            double d031028;
//            d031028 = (v031028.doubleValue())/100;  //number类值转换为int型

////
//
//            double d041025,d041026,d041027,d041029,d041030;
//            d041025 = (v041025.doubleValue())/100;  //number类值转换为double型
//            d041026 = (v041026.doubleValue())/100;
//            d041027 = (v041027.doubleValue())/100;
//            d041029 = (v041029.doubleValue())/100;
//            d041030 = (v041030.doubleValue())/100;
//
//

            //读取示例

            String host = "192.168.0.11";
            // 01测试
            Boolean v011 = readCoilStatus(host, 1, 0);
            Boolean v012 = readCoilStatus(host, 1, 1);
            Boolean v013 = readCoilStatus(host, 1, 3);

            System.out.println("v011:" + v011);
            System.out.println("v012:" + v012);
            System.out.println("v013:" + v013);
            System.out.println("_________________________________________________________");

            ResultRegiser2 = batchRead02(host);

            acu = new Acu();
            acu.setOxygen(ResultRegiser2[0]);
            acu.setTemperature(ResultRegiser2[1]);
            acu.setHumidity(ResultRegiser2[2]);
            acu.setCarbon(ResultRegiser2[3]);
            acu.setMethane(ResultRegiser2[4]);
            acu.setHydrogen(ResultRegiser2[5]);
            acu.setLevel(ResultRegiser2[6]);
            acu.setRecordTime(LocalDateTime.now());
            acuMapper.insert(acu);

            System.out.println("氧含量:" + ResultRegiser2[0] + "%");
            System.out.println("温度:" + ResultRegiser2[1] + "℃");
            System.out.println("湿度:" + ResultRegiser2[2] + "%RH");
            System.out.println("二氧化碳含量:" + ResultRegiser2[3] + "ppm");
            System.out.println("甲烷:" + ResultRegiser2[4] + "%");
            System.out.println("硫化氢:" + ResultRegiser2[5] + "%P");
            System.out.println("液位计:" + ResultRegiser2[6] + "M");
            System.out.println("批量读取成功");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return acu;
    }


    //测试数据
    @Override
    //@Scheduled(fixedRate = 3000)  //每3秒执行一次该方法
    public Acu addAcu2() {
        double[] test1 = new double[0];
        Acu acu = null;
        try {
            double a = 1.01;
            double b = 1.02;
            double c = 1.03;
            double d = 1.04;
            double e = 1.05;
            double f = 1.06;
            double g = 1.07;

            test1 = new double[7];

            test1[0] = a;
            test1[1] = b;
            test1[2] = c;
            test1[3] = d;
            test1[4] = e;
            test1[5] = f;
            test1[6] = g;

            acu = new Acu();
            acu.setOxygen(a);
            acu.setTemperature(b);
            acu.setHumidity(c);
            acu.setCarbon(d);
            acu.setMethane(e);
            acu.setHydrogen(f);
            acu.setLevel(g);
            acu.setRecordTime(LocalDateTime.now());
            acuMapper.insert(acu);


            System.out.println("氧含量:" + a + "%");
            System.out.println("温度:" + b + "%");
            System.out.println("湿度:" + c + "%");
            System.out.println("二氧化碳含量:" + d + "%");
            System.out.println("甲烷:" + e + "%");
            System.out.println("硫化氢:" + f + "%");
            System.out.println("液位计:" + g + "CM");
            System.out.println("批量读取成功");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return acu;
    }

}
