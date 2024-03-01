package com.xl.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class user {

    int id;
    String name;
    String principal;
    String number;
    String address;

    String data;

}
