package com.controller;

import com.entity.Acu;
import com.service.AcuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin  //解决跨域问题
@RestController
public class AcuController {
    @Autowired
    private AcuService acuService;

    //连接机柜数据
    @GetMapping("/addAcu")
    public Acu addAcu(){
        Acu acu = acuService.addAcu2();
        return acu;
    }

    //测试数据
    @GetMapping("/addAcu2")
    public Acu addAcu2(){
        Acu acu = acuService.addAcu2();
        return acu;
    }

 }


