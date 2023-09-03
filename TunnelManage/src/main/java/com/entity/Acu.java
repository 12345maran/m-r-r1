package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//@TableName("Rsgiser")  为mybatisplus设置指定数据库表面，一般会自动匹配与实体类命名相同的表名

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Acu {

    @TableId(type = IdType.AUTO)
    public Integer id;

    public Double temperature; //温度

    public Double humidity; //湿度

    public Double oxygen;  //氧气

    public Double methane;  //甲烷

    public Double carbon;   //二氧化碳

    public Double level;    //液位计

    public Double hydrogen; //氢气

    private LocalDateTime recordTime; //数据记录时间

}
