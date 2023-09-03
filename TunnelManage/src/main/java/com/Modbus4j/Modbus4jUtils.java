package com.Modbus4j;

import com.serotonin.modbus4j.BatchRead;
import com.serotonin.modbus4j.BatchResults;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.locator.BaseLocator;
import com.mapper.AcuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
//import org.assertj.core.error.ShouldStartWith;


/**
 * modbus通讯工具类,采用modbus4j实现
 *
 * @author lxq
 * @dependencies modbus4j-3.0.3.jar
 * @website https://github.com/infiniteautomation/modbus4j
 */

@Component
public class Modbus4jUtils {

    /**
     * 工厂。
     */
    static ModbusFactory modbusFactory;
    static {
        if (modbusFactory == null) {
            modbusFactory = new ModbusFactory();
        }
    }

    /**
     * 获取master
     *
     * @return
     * @throws ModbusInitException
     */
    public static ModbusMaster getMaster(String host) throws ModbusInitException {
        IpParameters params = new IpParameters();
//        params.setHost("localhost");   //指定IP和PORT
//        params.setPort(502);
//        params.setHost("192.168.0.11");   //指定IP和PORT
//        params.setPort(12315);
        params.setHost(host);   //指定IP和PORT

        //默认PORT:12315
        params.setPort(12315);


        //
        // modbusFactory.createRtuMaster(wapper); //RTU 协议
        // modbusFactory.createUdpMaster(params);//UDP 协议
        // modbusFactory.createAsciiMaster(wrapper);//ASCII 协议
        ModbusMaster master = modbusFactory.createTcpMaster(params, false); // TCP 协议
        master.init();
        System.out.println("创建" +
                "连接成功");
        return master;
    }

    /**
     * 读取[01 Coil Status 0x]类型 开关数据
     *
     * @param slaveId
     *            slaveId
     * @param offset
     *            位置
     * @return 读取值
     * @throws ModbusTransportException
     *             异常
     * @throws ErrorResponseException
     *             异常
     * @throws ModbusInitException
     *             异常
     */
    public static Boolean readCoilStatus(String host,int slaveId, int offset)
            throws ModbusTransportException, ErrorResponseException, ModbusInitException {
        // 01 Coil Status
        BaseLocator<Boolean> loc = BaseLocator.coilStatus(slaveId, offset);
        Boolean value = getMaster(host).getValue(loc);
        //System.out.println("线圈"+offset+"读取成功");
        return value;
    }

    /**
     * 读取[02 Input Status 1x]类型 开关数据
     *
     * @param slaveId
     * @param offset
     * @return
     * @throws ModbusTransportException
     * @throws ErrorResponseException
     * @throws ModbusInitException
     */
    public static Boolean readInputStatus(String host,int slaveId, int offset)
            throws ModbusTransportException, ErrorResponseException, ModbusInitException {
        // 02 Input Status
        BaseLocator<Boolean> loc = BaseLocator.inputStatus(slaveId, offset);
        Boolean value = getMaster(host).getValue(loc);
        System.out.println("输入寄存器"+offset+"读取成功");
        return value;
    }

    /**
     * 读取[03 Holding Register类型 2x]模拟量数据
     *
     * @param slaveId
     *            slave Id
     * @param offset
     *            位置
     * @param dataType
     *            数据类型,来自com.serotonin.modbus4j.code.DataType
     * @return
     * @throws ModbusTransportException
     *             异常
     * @throws ErrorResponseException
     *             异常
     * @throws ModbusInitException
     *             异常
     */
    public static Number readHoldingRegister(String host,int slaveId, int offset, int dataType)
            throws ModbusTransportException, ErrorResponseException, ModbusInitException {
        // 03 Holding Register类型数据读取
        BaseLocator<Number> loc = BaseLocator.holdingRegister(slaveId, offset, dataType);
        Number value = getMaster(host).getValue(loc);
        System.out.println("保持寄存器"+offset+"读取成功");
        return value;
    }

    /**
     * 读取[04 Input Registers 3x]类型 模拟量数据
     *
     * @param slaveId
     *            slaveId
     * @param offset
     *            位置
     * @param dataType
     *            数据类型,来自com.serotonin.modbus4j.code.DataType
     * @return 返回结果
     * @throws ModbusTransportException
     *             异常
     * @throws ErrorResponseException
     *             异常
     * @throws ModbusInitException
     *             异常
     */
    public static Number readInputRegisters(String host,int slaveId, int offset, int dataType)
            throws ModbusTransportException, ErrorResponseException, ModbusInitException {
        // 04 Input Registers类型数据读取
        BaseLocator<Number> loc = BaseLocator.inputRegister(slaveId, offset, dataType);
        Number value = getMaster(host).getValue(loc);
        return value;
    }

    /**
     * 批量读取使用方法
     *
     * @throws ModbusTransportException
     * @throws ErrorResponseException
     * @throws ModbusInitException
     */
    public static void batchRead(String host) throws ModbusTransportException, ErrorResponseException, ModbusInitException {

        BatchRead<Integer> batch = new BatchRead<Integer>();

        batch.addLocator(0, BaseLocator.holdingRegister(1, 0, DataType.FOUR_BYTE_FLOAT));
        batch.addLocator(1, BaseLocator.inputStatus(1, 0));

        ModbusMaster master = getMaster(host);

        batch.setContiguousRequests(false);
        BatchResults<Integer> results = master.send(batch);
        System.out.println(results.getValue(0));
        System.out.println(results.getValue(1));
    }


    /*
    批量读取线圈

    coilNum   线圈总数
    */

    public static boolean[] batchReadCoil(String host) throws ModbusTransportException, ErrorResponseException, ModbusInitException{

        //CoilNum为默认线圈总数8个
        int coilNo = 0,coilNum=8;

        boolean acuCoil[] = new boolean[coilNum];

        for(coilNo=0;coilNo<coilNum;coilNo++) {
            acuCoil[coilNo] = readCoilStatus(host, 1, coilNo);
        }
        return acuCoil;
    }


    public static boolean[] CoilbatchReadpecial(String host,int bp,int ep) throws ModbusTransportException, ErrorResponseException, ModbusInitException{

        int coilNo = 0,coilNum=8;

        boolean acuCoil[] = new boolean[coilNum];

        for(coilNo=bp;coilNo<ep;coilNo++) {
            acuCoil[coilNo] = readCoilStatus(host, 1, coilNo);
        }
        return acuCoil;
    }
    public static boolean CoilbatchReadOne(String host,int p) throws ModbusTransportException, ErrorResponseException, ModbusInitException{

        boolean acuCoil = readCoilStatus(host, 1, p);

        return acuCoil;
    }

    public static double[] batchRead02(String host) throws ModbusTransportException, ErrorResponseException, ModbusInitException {

        // 03测试  对应4x
        Number v031028 = readHoldingRegister(host,1, 1027, DataType.TWO_BYTE_INT_SIGNED);// 读取16进制，并自动转为10进制
        Number v031031 = readHoldingRegister(host,1, 1030, DataType.TWO_BYTE_INT_SIGNED);// 读取16进制，并自动转为10进制
        //Number v032 = readHoldingRegister(1, 2, DataType.TWO_BYTE_INT_SIGNED);// 同上
        //System.out.println("v031:" + v031028);
        //System.out.println("v032:" + v032);


//
        // 04测试 对应3x
        Number v041025 = readInputRegisters(host,1, 1024, DataType.TWO_BYTE_INT_SIGNED);//读取16进制，并自动转为10进制
        Number v041026 = readInputRegisters(host,1, 1025, DataType.TWO_BYTE_INT_SIGNED);
        Number v041027 = readInputRegisters(host,1, 1026, DataType.TWO_BYTE_INT_SIGNED);
        Number v041029 = readInputRegisters(host,1, 1028, DataType.TWO_BYTE_INT_SIGNED);
        Number v041030 = readInputRegisters(host,1, 1029, DataType.TWO_BYTE_INT_SIGNED);



        double d031028,d031031;
        d031028 = v031028.doubleValue();  //number类值转换为int型
        d031031 = (v031031.doubleValue())/100;  //number类值转换为int型

        //System.out.println("二氧化碳含量:"+d031028+"%");
//

        double d041025,d041026,d041027,d041029,d041030;
        d041025 = (v041025.doubleValue())/10;  //number类值转换为double型
        d041026 = (v041026.doubleValue())/100;
        d041027 = (v041027.doubleValue())/100;
        d041029 = (v041029.doubleValue())/100;
        d041030 = (v041030.doubleValue())/100;


        double[] ResultRegiters = {d041025,d041026,d041027,d031028,d041029,d041030,d031031}; //顺序为含氧量，温度，湿度，二氧化碳，甲烷，硫化氢,液位计

        return ResultRegiters;
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
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
            Boolean v011 = readCoilStatus(host,1, 0);
            Boolean v012 = readCoilStatus(host,1, 1);
            Boolean v013 = readCoilStatus(host,1, 3);

            System.out.println("v011:" + v011);
            System.out.println("v012:" + v012);
            System.out.println("v013:" + v013);
            System.out.println("_________________________________________________________");





            double[] ResultRegiser2 = batchRead02(host);

            System.out.println("氧含量:"+ResultRegiser2[0]+"%");
            System.out.println("温度:"+ResultRegiser2[1]+"%");
            System.out.println("湿度:"+ResultRegiser2[2]+"%");

            System.out.println("二氧化碳含量:"+ResultRegiser2[3]+"ppm");

            System.out.println("甲烷:"+ResultRegiser2[4]+"%");
            System.out.println("硫化氢:"+ResultRegiser2[5]+"%");
            System.out.println("液位计:"+ResultRegiser2[6]+"CM");
            System.out.println("批量读取成功");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}