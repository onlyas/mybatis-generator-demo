package com.onlyas.app.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onlyas.app.dao.UsersMapper;
import com.onlyas.app.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    UsersMapper usersMapper;

    @GetMapping("/list")
    public List<Users> list() {
        return usersMapper.selectByExample(null);
    }

    @GetMapping("/page")
    public PageInfo<Users> page() {
        PageHelper.startPage(1, 10);
        List<Users> list = usersMapper.selectByExample(null);
        PageInfo<Users> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

}
