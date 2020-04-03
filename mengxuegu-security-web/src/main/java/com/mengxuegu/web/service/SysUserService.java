package com.mengxuegu.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mengxuegu.web.entities.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 * 实现 IService<T> 接口，提供了常用更复杂的对 T 数据表的操作，
 * 比如：支持 Lambda 表达式，批量删除、自动新增或更新操作
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * * 通过用户名查询
     * * @param username 用户名
     * * @return 用户信息
     * 
     */
    SysUser findByUsername(String username);


    /**
     * 通过手机号查询用户信息
     */
    SysUser findByMobile(String mobile);

    IPage<SysUser> selectPage(Page<SysUser> page, SysUser sysUser);

    SysUser findById(Long id);

    /**
     * 通过id假删除用户数据，把 is_enabled = 0
     */
    boolean deleteById(Long id);
    
    
}
