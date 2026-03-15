package com.itheima.mp.domain.vo;

import com.itheima.mp.domain.po.UserInfo;
import com.itheima.mp.enums.UserStauts;
import lombok.Data;

@Data
public class UserVO {
    private Long id;
    private String username;
    private UserInfo info;
    private UserStauts status;
    private Integer balance;
}
