package com.controller;

import com.service.CoilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin  //解决跨域问题
@RestController
public class CoilController {
    @Autowired
    private CoilService coilService;

    //读线圈
    @GetMapping("/readAllCoil/{ip}")
    public boolean[] readAllCoil(@PathVariable("ip") String host){
        //String host = "192.168.0.11";
       // String host = "127.0.0.1";
        boolean[] booleans = coilService.readAllCoil(host);
        return booleans;
    }

    //写线圈
    @PostMapping("/write/{ip}/{writeOffset}/{writeValue}")
    public boolean write( @PathVariable("ip") String host,
                          @PathVariable("writeOffset") int coilX,
                         @PathVariable("writeValue") boolean coilState){
        //String host = "127.0.0.1";
        //int coilX = 4;
        //Boolean coilState = true;

        boolean b = coilService.writeCoil(host, coilX, coilState);
        return b;
    }
    //@PathVariable String host,
    //                         @PathVariable int coilX,
    //                         @PathVariable Boolean coilState
}
