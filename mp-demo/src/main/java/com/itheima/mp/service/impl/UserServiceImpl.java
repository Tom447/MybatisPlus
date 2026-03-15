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
        Page<User> p = Page.of(query.getPageNo(), query.getPageSize());
        //2.排序条件
        if (!StrUtil.isNotBlank(query.getSortBy())){
            p.addOrder(new OrderItem(query.getSortBy(), query.getIsAsc()));
        }else{
            p.addOrder(new OrderItem("update_ime", query.getIsAsc()));
        }
        //3.查询
        //由于本身是在userservice中所以直接page即可
        page(p);
        //4.获取结构
        List<User> users = p.getRecords();
        if (CollUtil.isEmpty(users)){
            //没有数据，直接返回
            return PageVO.of(p.getTotal(), p.getPages(), Collections.emptyList());
        }else{
            //4.2 把user转化为VO  集合之间的拷贝使用的BeanUtil
            List<UserVO> userVOList = BeanUtil.copyToList(users, UserVO.class);
            //4.3 封装返回
            return PageVO.of(p.getTotal(), p.getPages(), userVOList);
        }
    }
}
