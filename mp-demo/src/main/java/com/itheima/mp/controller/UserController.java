package com.itheima.mp.controller;


import com.itheima.mp.domain.query.PageQuery;
import com.itheima.mp.domain.vo.PageVO;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.service.IAddressService;
import com.itheima.mp.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;
    private final IAddressService addressService;

    @GetMapping("/pages")
    public PageVO<UserVO> queryUserByPage(PageQuery query) {
        return userService.queryUserByPage(query);
    }
}
