package com.itheima.mp.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.mp.domain.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<User> queryUserByIds(@Param("ids") List<Long> ids);

    List<User> findByCustom(@Param("ew") QueryWrapper<User> wrapper);

    //使用mybatisPlus 不再service层中出现sql语句
    @Update("update user set balance = balance - #{amount} ${ew.customSqlSegment}")
    void updateBalanceByWrapper(@Param("amount") int amount, @Param("ew") UpdateWrapper<User> wrapper);

    // 导入 org.apache.ibatis.annotations.Param
    List<User> queryUserByIdAndAddr(@Param("city") String city, @Param("ids") List<Long> ids);

    List<User> queryUsersByWrapper(@Param("ew") QueryWrapper<User> wrapper);
}
