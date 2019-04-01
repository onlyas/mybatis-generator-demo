package com.onlyas.app.service;

import com.github.pagehelper.PageInfo;
import com.onlyas.app.domain.TUsers;

import java.util.List;

public interface TUsersService {
    List<TUsers> list();
    PageInfo<TUsers> page();
}
