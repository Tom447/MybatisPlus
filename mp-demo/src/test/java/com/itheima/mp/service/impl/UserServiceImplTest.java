package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.po.UserInfo;
import com.itheima.mp.enums.UserStauts;
import com.itheima.mp.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private IUserService userService;

    @Test
    void testSaveUser(){
        User user = new User();
        user.setUsername("小马3");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(20000);
        //user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        userService.save(user);

        User one = userService.getOne(new QueryWrapper<User>().like("username", "小马3"));
        System.out.println(one);

    }

    @Test
    void testSelectUser(){
        List<User> list = userService.list(new QueryWrapper<User>().like("username", "o"));
        list.forEach(System.out::println);
    }

    @Test
    void testSaveOneByOne() {
        long b = System.currentTimeMillis();
        for (int i = 1; i <= 100000; i++) {
            userService.save(buildUser(i));
        }
        long e = System.currentTimeMillis();
        System.out.println("耗时：" + (e - b));
    }

    @Test
    void testSaveBatch() {
        // 准备10万条数据
        List<User> list = new ArrayList<>(1000);
        long b = System.currentTimeMillis();
        for (int i = 1; i <= 100000; i++) {
            list.add(buildUser(i));
            // 每1000条批量插入一次
            if (i % 1000 == 0) {
                userService.saveBatch(list);
                list.clear();
            }
        }
        long e = System.currentTimeMillis();
        System.out.println("耗时：" + (e - b));
    }

    private User buildUser(int i) {
        User user = new User();
        user.setUsername("user_" + i);
        user.setPassword("123");
        user.setPhone("" + (18688190000L + i));
        user.setBalance(2000);
        //user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(user.getCreateTime());
        return user;
    }


    /**
     * 需求：基于IService中的lambdaQuery()实现一个方法，满足下列需求：
     * ① 查询名字为Rose的用户
     * ② 查询名字中包含‘o’的用户
     * ③ 统计名字中包含‘o’的用户的数量
     * ④ 定义一个方法，接收参数为username、status、minBalance、maxBalance，参数可以为空。
     *     　a. 如果username参数不为空，则采用模糊查询；
     *     　b. 如果status参数不为空，则采用精确匹配；
     *     　c. 如果minBalance参数不为空，则余额必须大于minBalance
     *     　d. 如果maxBalance参数不为空，则余额必须小于maxBalance
     */


    public List<User> queryUsers(String username, Integer status, Long minBalance, Long maxBalance) {
        return userService.lambdaQuery()
                .like(username != null, User::getUsername, username)
                .eq(status !=null, User::getStatus, status)
                .gt(minBalance != null, User::getBalance, minBalance)
                .lt(maxBalance != null, User::getBalance, maxBalance)
                .list();

    }
    @Test
    void testQueryLamabda0(){
        User user = userService.lambdaQuery().eq(User::getUsername, "Rose")
                .one();
        System.out.println("user = " + user);


        List<User> list = userService.lambdaQuery().like(User::getUsername, "o").list();
        list.forEach(System.out::println);

        Long count = userService.lambdaQuery().like(User::getUsername, "o").count();
        System.out.println("count = " + count);
    }

    @Test
    void testQueryLamabda1(){
        List<User> list = queryUsers("o", 1, null, null);
        list.forEach(System.out::println);
    }

    /**
     * 需求：基于IService中的lambdaUpdate()方法实现一个更新方法，满足下列需求：
     *     ① 参数为balance、id、username
     *     ② id或username至少一个不为空，根据id或username精确匹配用户
     *     ③ 将匹配到的用户余额修改为balance
     *     ④ 如果balance为0，则将用户status修改为冻结状态（2）
     */
    @Test
    void testQueryLamabda2(){
        updateBalance(0L, 1L, null);
    }

    private void updateBalance(Long balance, Long id, String username){
        /*
        * update user set balance = ？，status = 2 WHERE id = ?  AND  username = ？
        * */
        if (id == null && username == null){
            throw new RuntimeException("更新条件不能为空");
        }
        userService.lambdaUpdate()
                .set(User::getBalance, balance)
                .set(balance == 0, User::getStatus, 2)
                .eq(id != null, User::getId, id)
                .eq(username != null, User::getUsername, username)
                .update();
    }

    //枚举测试
    @Test
    void testEnumSaveUser() {
        User user = new User();
        user.setUsername("any");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        //user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setStatus(UserStauts.NORMAL);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userService.save(user);

        User result = userService.getOne(new QueryWrapper<>(new User()).eq("username", "any"));
        System.out.println(result);
    }

    @Test
    void testEnumsGetOneById(){
        User user = userService.getById(1L);
        System.out.println(user);
    }

    @Test
    void testNewSaveUser(){
      /*  User user = new User();
        user.setUsername("小马7");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(20000);
        UserInfo userInfo = UserInfo.of(24, "英文老师", "female");
        //user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setInfo(userInfo);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        userService.save(user);*/

        User one = userService.getOne(new QueryWrapper<User>().like("username", "小马7"));
        System.out.println(one);
    }


}