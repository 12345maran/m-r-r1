package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class Coil {

    @TableId(type = IdType.AUTO)
    public Integer id;

    public Boolean X0;

    public Boolean X1;

    public Boolean X2;

    public Boolean X3;

    public Boolean X4;

    public Boolean X5;

    public Boolean X6;

    public Boolean X7;

    private LocalDateTime recordTime; //数据记录时间

}
