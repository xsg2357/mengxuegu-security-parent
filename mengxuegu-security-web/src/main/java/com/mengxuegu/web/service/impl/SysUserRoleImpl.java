package com.mengxuegu.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengxuegu.web.entities.SysRole;
import com.mengxuegu.web.mapper.SysRoleMapper;
import com.mengxuegu.web.service.SysRoleService;
import org.springframework.stereotype.Service;

/**
 * ServiceImpl<M extends BaseMapper<T>, T> 是对 IService接口中方法的实现
 * 第1个泛型 M 指定继承了 BaseMapper接口的子接口
 * 第2个泛型 T 指定实体类
 */
@Service
public class SysUserRoleImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

}
