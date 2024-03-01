package com.xl.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_user")
public class a implements Serializable {


    @TableId
    int id;

    String name;
    String address;
    String data;

}
