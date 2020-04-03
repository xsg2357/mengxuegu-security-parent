package com.mengxuegu.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mengxuegu.web.entities.SysRole;

/**
 * 实现 IService<T> 接口，提供了常用更复杂的对 T 数据表的操作，
 * 比如：支持 Lambda 表达式，批量删除、自动新增或更新操作
 */
public interface SysRoleService extends IService<SysRole> {

    IPage<SysRole> selectPage(Page<SysRole> page, SysRole sysRole);

    SysRole findById(Long id);

    /**
       * 通过角色id删除角色表和角色权限资源关系表数据
       * @param id 角色id
       * @return
       */
    boolean deleteById(Long id);
}
