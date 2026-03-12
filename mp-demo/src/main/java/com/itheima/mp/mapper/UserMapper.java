package com.itheima.mp.mapper;


import com.itheima.mp.domain.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper{
    List<User> queryUserByIds(@Param("ids") List<Long> ids);
}
