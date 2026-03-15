package com.itheima.mp.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.PageQuery;
import com.itheima.mp.domain.vo.PageVO;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import cn.hutool.core.util.StrUtil;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public List<User> queryUsers(String username, Integer status, Long minBalance, Long maxBalance) {
//        Db.lambdaQuery(Address.class).list();
//        getBaseMapper().updateBalanceByWrapper();
        return lambdaQuery().like(User::getUsername, "o").list();
    }

    @Override
    public PageVO<UserVO> queryUserByPage(PageQuery query) {
        //1.分页条件
//        Page<User> p = query.toMapPage("update_time", true);
        Page<User> p = query.toMapPageDefaultSortByUpdate();
        //3.查询
        //由于本身是在userservice中所以直接page即可
        page(p);
        //4.获取结构
        return new PageVO<>(p, user -> {
            UserVO vo = BeanUtil.copyProperties(user, UserVO.class);
            String username = vo.getUsername();
            vo.setUsername(username.substring(0, username.length() - 2) + "*");
            return vo;
        });
    }
}
