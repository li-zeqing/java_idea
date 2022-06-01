package com.mylove.mpdemo1010;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mylove.mpdemo1010.entity.User;
import com.mylove.mpdemo1010.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Arrays;
import java.util.List;


@SpringBootTest
public class MpdemoApplicationTests {

    @Autowired
    private UserMapper userMapper;


    //查询user表所有数据
    @Test
    public void findAll() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    //添加操作
    @Test
    public void addUser(){
        User user = new User();
        user.setName("lisi");
        user.setAge(18);
        user.setEmail("lisi@qq.com");

        int insert = userMapper.insert(user);
        System.out.println("insert:" + insert);
    }

    //修改操作
    @Test
    public void updateUser(){
        User user = new User();
        user.setId(2L);
        user.setAge(110);

        int row = userMapper.updateById(user);
        System.out.println(row);
    }

    //测试乐观锁
    @Test
    public void testOptimisticLock(){

        //根据id查询数据
        User user = userMapper.selectById(1531602953093341186L);
        //进行修改
        user.setAge(20);
        userMapper.updateById(user);
    }

    //多个id批量查询
    @Test
    public void testSelectdemo1(){

        List<User> users = userMapper.selectBatchIds(Arrays.asList(1,2,3));
        users.forEach(System.out::println);
    }

    //分页查询
    @Test
    public void testPage(){
        //1.创建一个page对象
        Page<User> page = new Page<>(1, 3);
        //2.调用mp分页查询方法 -> 调用mp分页查询过程中，底层封装
        //把分页所有数据封装到page对象里
        userMapper.selectPage(page,null);

        //通过page对象获取分页数据
        System.out.println(page.getCurrent());//当前是第几页
        System.out.println(page.getRecords());//每页数据list集合
        System.out.println(page.getSize());//每页显示记录数
        System.out.println(page.getTotal());//总记录数
        System.out.println(page.getPages());//总页数
        System.out.println(page.hasNext());//是否有下一页
        System.out.println(page.hasPrevious());//是否有上一页
    }

    //逻辑删除
    @Test
    public void testLogicDelete(){

        int result = userMapper.deleteById(1L);
        System.out.println(result);
    }

    //mp实现复杂查询操作
    @Test
    public void testSelectQuery(){
        //插件QueryWrapper对象
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        //通过QueryWrapper设置条件
        // ge 大于等于
        // gt 大于
        // le 小于等于
        // lt 小于
        //查询age >= 30
        wrapper.ge("age",30);
        List<User> users = userMapper.selectList(wrapper);
        System.out.println(users);

        //eq 等于
        //ne 不等于 <> !=


    }

    @Test
    public void testDelete() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .isNull("name")
                .ge("age", 12)
                .isNotNull("email");
        int result = userMapper.delete(queryWrapper);
        System.out.println("delete return count = " + result);
    }
}
