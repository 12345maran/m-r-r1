package com.service;

public interface CoilService {
    //读线圈
    public boolean[] readAllCoil(String host);

    //写线圈
    public boolean writeCoil(String host,int coilX,Boolean coilState);
}
