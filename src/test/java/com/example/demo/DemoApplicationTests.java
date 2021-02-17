package com.example.demo;

import com.example.demo.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;


import java.util.Objects;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    @Qualifier("MyredisTemplate")
    private RedisTemplate redisTemplate;


    @Test
    void contextLoads() {
        //redisTemplate
        //opsForValue()   操作字符串  类似String
        //opsForList()    操作List
//        redisTemplate.opsForValue().set("k1","v1");


        //除了基本的操作，我们常用的操作都可以直接通过RedisTemplate进行实现，比如事务和基本的CRUD

        //获取连接对象
        RedisConnection connection = Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection();
//        connection.flushAll();
//        connection.flushDb();
        redisTemplate.opsForValue().set("k1","一二三");
        System.out.println(redisTemplate.opsForValue().get("k1"));
    }


    @Test
    public void TestSeria() throws JsonProcessingException {
        User user = new User("王柯栋", 18);
        //将对象序列化为json
        String userJson = new ObjectMapper().writeValueAsString(user);
        //将对象写入redis
        redisTemplate.opsForValue().set("user",userJson);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }

}
