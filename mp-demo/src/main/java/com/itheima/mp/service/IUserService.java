package com.itheima.mp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.mp.domain.po.User;

import java.util.List;

public interface IUserService extends IService<User> {
    List<User> queryUsers(String username, Integer status, Long minBalance, Long maxBalance);
}
