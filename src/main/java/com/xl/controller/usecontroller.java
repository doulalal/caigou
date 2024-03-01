package com.xl.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xl.mapper.usermapper;
import com.xl.pojo.a;
import com.xl.pojo.user;
import com.xl.service.userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class usecontroller {

    @Autowired
    userservice userservice;

@RequestMapping("/test")
Map<String,Object> chaxun(@RequestParam("page") int current,@RequestParam("size")  int size,@RequestParam("username") String username){


    Page<a> page = new Page<>(current,size);
    QueryWrapper<a> wrapper = new QueryWrapper<>();
    wrapper.like("name",username);
    userservice.page(page,wrapper);
    Map<String,Object> map=new HashMap<>();
    if (page.getRecords().isEmpty())
    {
        Page<a> page1 = new Page<>(current-1,size);
        userservice.page(page1,wrapper);
        map.put("total",page1.getTotal());
        map.put("data",page1.getRecords());
        return  map;
    }


        map.put("total",page.getTotal());
        map.put("data",page.getRecords());
        return map;

    }

@RequestMapping("/zhengjia")
    boolean AddUser(@RequestBody a user)
{
    boolean b = userservice.saveOrUpdate(user);
    return b;
}
@RequestMapping("/shanchu")
boolean shanchur(@RequestParam("id") int id)
{
    boolean b = userservice.removeById(id);
    return b;
}
@RequestMapping("/pshanchu")
    boolean pshanchu(@RequestBody() List<Integer> list) {

    return userservice.removeByIds(list);



}
}
