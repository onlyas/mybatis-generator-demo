package com.onlyas.app.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onlyas.app.dao.TUsersMapper;
import com.onlyas.app.domain.TUsers;
import com.onlyas.app.service.TUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class TUsersController {

    @Autowired
    TUsersService tUsersService;

    @GetMapping("/list")
    public List<TUsers> list() {
        return tUsersService.list();
    }

    @GetMapping("/page")
    public PageInfo<TUsers> page() {
        return tUsersService.page();
    }

}
