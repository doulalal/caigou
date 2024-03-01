package com.xl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xl.mapper.usermapper;
import com.xl.pojo.a;
import com.xl.pojo.user;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PurhoutaiApplicationTests {
@Autowired
usermapper usermapper;
    @Test
    void contextLoads() {
        Page<a> page = new Page<>(1,4);
        usermapper.selectPage(page, null);
        System.out.println(page.getTotal());
        System.out.println(page.getCurrent());
        System.out.println(page.getPages());
        System.out.println(page.getRecords());
        System.out.println(page.getSize());


    }

}
