package com.itheima.mp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.AES;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;


class MpDemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testQueryByIds() {
        List<User> users = userMapper.queryUserByIds(List.of(1L, 2L, 3L));
        users.forEach(System.out::println);
    }


    //新增用户
    @Test
    void testSaveUser() {
        User user = new User();
        user.setUsername("jack");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        //user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    //根据id查询
    @Test
    void testGetById() {
        User user = userMapper.selectById(5L);
        System.out.println(user);
    }

    //根据id批量查询
    @Test
    void testGetByIds() {
        List<User> users = userMapper.selectBatchIds(List.of(1L, 2L, 3L));
        users.forEach(System.out::println);
    }

    //更新
    @Test
    void testUpdateUser() {
        User user = new User();
        user.setId(5L);
        user.setUsername("Loser");
        user.setPassword("123");
        userMapper.updateById(user);
        User selectById = userMapper.selectById(5L);
        System.out.println(selectById);
    }

    //删除
    @Test
    void testDeleteUser() {
        userMapper.deleteById(5L);
        User user = userMapper.selectById(5L);
        System.out.println(user);
    }


    /**
     * 查询出名字中带o的，存款大于等于1000元的人的id、username、info、balance字段
     */
    @Test
    void testQueryWapper1() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "username", "balance", "info").like("username", "o").ge("balance", 1000);

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     *更新用户名为jack的用户余额为2000
     */
    @Test
    void testQueryWapper2() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username", "jack");

        User user = new User();
        user.setBalance(2000);
        userMapper.update(user, updateWrapper);
    }

    /**
     * 更新1 、2 、3的用户的余额，扣200块钱
     * 只能在sql语句中减，因为如果先查再减，并发好几个人减怎么办？
     */
    @Test
    void testQueryWapper3() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>()
                .setSql("balance = balance - 200")
                .in("id", 1L, 2L, 3L);
        userMapper.update(null, updateWrapper);
    }

    /**
     * 查询出名字中带o的，存款大于等于1000元的人的id、username、info、balance字段
     */
    @Test
    void testQueryWapper4() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .select(User::getId, User::getUsername, User::getInfo, User::getBalance)
                .like(User::getUsername, "o")
                .ge(User::getBalance, 1000);

        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * 更新1 、2 、3的用户的余额，扣200块钱
     * 只能在sql语句中减，因为如果先查再减，并发好几个人减怎么办？
     */

    @Test
    void testQueryWapper5() {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<User>().setSql("balance = balance + 3000")
                .in(User::getId, 1L, 2L, 3L);

        userMapper.update(null, wrapper);
    }


    /**
     * 自定义SQL
     */
    @Test
    void testQueryWapper6() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>()
                .like("username", "o")
                .ge("balance", 2000);
        //查询数据
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);

    }

    /**
     * update balance 使用mybatisplus不在service层写sql
     * 更新1 、 2、 4 用户并减去200的balance
     */
    @Test
    void testQueryWapper7() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>().in("id", 1L, 2L, 4L);

        userMapper.updateBalanceByWrapper(200, updateWrapper);

        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().in("id", 1L, 2L, 4L);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    @Test
    void testQueryWapper8(){
        List<Long> ids = List.of(1L, 2L, 4L);
        String city = "北京市";

        QueryWrapper<User> queryWrapper = new QueryWrapper<User>()
                .eq("a.city", city)
                .in("u.id", ids);

        List<User> users = userMapper.queryUsersByWrapper(queryWrapper);
        users.forEach(System.out::println);

        List<User> list = userMapper.queryUserByIdAndAddr(city, ids);
        list.forEach(System.out::println);
    }

    @Test
    void contextLoads() {
        // 生成 16 位随机 AES 密钥
        String randomKey = AES.generateRandomKey();
        System.out.println("randomKey = " + randomKey);

        // 利用密钥对用户名加密
        String username = AES.encrypt("root", randomKey);
        System.out.println("username = " + username);

        // 利用密钥对用户名加密
        String password = AES.encrypt("123456", randomKey);
        System.out.println("password = " + password);
    }

}
