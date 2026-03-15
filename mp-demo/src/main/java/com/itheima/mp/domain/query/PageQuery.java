package com.itheima.mp.domain.query;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class PageQuery {
    private Integer pageNo = 1;
    private Integer pageSize = 5;
    private String sortBy;
    private Boolean isAsc = false;

    //把当前query构造成MybatiPlus的Page
    public <T> Page<T> toMapPage(String defaultSortBy, boolean isAsc) {
        //1.分页条件
        Page<T> p = Page.of(pageNo, pageSize);
        //2.排序条件
        if (StrUtil.isNotBlank(sortBy)){
            //有sortBy
            p.addOrder(new OrderItem(sortBy, isAsc));
        }else{
            //无sortBy的时候，要有个默认的sortBy
            p.addOrder(new OrderItem(defaultSortBy, isAsc));
        }

        return p;
    }

    //还可以优化一下，比如要排序的字段不是一个而是多个
    public <T> Page<T> toMapPage(OrderItem ...defaultOrderItems) {
        //1.分页条件
        Page<T> p = Page.of(pageNo, pageSize);
        //2.排序条件
        if (StrUtil.isNotBlank(sortBy)){
            //有sortBy
            p.addOrder(new OrderItem(sortBy, isAsc));
            return p;
        }
            //无sortBy
        //3.默认排序
        for (OrderItem order : defaultOrderItems) {
            p.addOrder(order);
        }
        return p;
    }

    //不传而默认的情况
    public <T> Page<T> toMapPageDefaultSortByUpdate() {
        //1.分页条件
        Page<T> p = Page.of(pageNo, pageSize);
        //2.排序条件
        if (StrUtil.isNotBlank(sortBy)){
            p.addOrder(new OrderItem(sortBy, isAsc));
        }else{
            p.addOrder(new OrderItem("update_time", false));
        }
        return p;
    }



}
