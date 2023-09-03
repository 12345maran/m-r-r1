package com.service.impl;

import com.entity.Coil;
import com.Modbus4j.Modbus4jUtils;
import com.Modbus4j.Modbus4jWriteUtils;
import com.mapper.CoilMapper;
import com.service.CoilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CoilServiceimpl implements CoilService {
    @Autowired
    private CoilMapper coilMapper;

    @Autowired
    private Modbus4jUtils modbus4jUtils;

    @Autowired
    private Modbus4jWriteUtils modbus4jWriteUtils;

     //@Scheduled(fixedRate = 3000)  //每3秒执行一次该方法
    public boolean[] readAllCoil(String host) {

        boolean[] booleans = new boolean[8];
        try {
            booleans = modbus4jUtils.batchReadCoil(host);

            //将读出的数据存入数据库
            Coil coil = new Coil();
            coil.setX0(booleans[0]);
            coil.setX1(booleans[1]);
            coil.setX2(booleans[2]);
            coil.setX3(booleans[3]);
            coil.setX4(booleans[4]);
            coil.setX5(booleans[5]);
            coil.setX6(booleans[6]);
            coil.setX7(booleans[7]);
            coil.setRecordTime(LocalDateTime.now());
            coilMapper.insert(coil);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return booleans;
    }

    @Override
    public boolean writeCoil(String host, int coilX, Boolean coilState) {
        boolean b = false;
        try {
            b = Modbus4jWriteUtils.writeCoil(host, 1, coilX, coilState);

            //写线圈完成后读所以线圈状态并存入数据库
            boolean[] booleans = new boolean[8];
            booleans = modbus4jUtils.batchReadCoil(host);
            //将读出的数据存入数据库
            Coil coil = new Coil();
            coil.setX0(booleans[0]);
            coil.setX1(booleans[1]);
            coil.setX2(booleans[2]);
            coil.setX3(booleans[3]);
            coil.setX4(booleans[4]);
            coil.setX5(booleans[5]);
            coil.setX6(booleans[6]);
            coil.setX7(booleans[7]);
            coil.setRecordTime(LocalDateTime.now());
            coilMapper.insert(coil);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
}
