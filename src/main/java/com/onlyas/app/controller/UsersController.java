package com.onlyas.app.controller;

import com.onlyas.app.dao.UsersMapper;
import com.onlyas.app.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    UsersMapper usersMapper;

    @PostMapping("/list")
    public List<Users> list(){
        return usersMapper.selectByExample(null);
    }

}
