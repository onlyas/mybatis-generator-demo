package com.onlyas.app.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onlyas.app.dao.TUsersMapper;
import com.onlyas.app.domain.TUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class TUsersController {

    @Autowired
    TUsersMapper usersMapper;

    @GetMapping("/list")
    public List<TUsers> list() {
        return usersMapper.selectByExample(null);
    }

    @GetMapping("/page")
    public PageInfo<TUsers> page() {
        PageHelper.startPage(1, 10);
        List<TUsers> list = usersMapper.selectByExample(null);
        PageInfo<TUsers> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

}
