package com.mengxuegu.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengxuegu.web.entities.SysRole;
import com.mengxuegu.web.entities.SysUser;
import com.mengxuegu.web.mapper.SysRoleMapper;
import com.mengxuegu.web.mapper.SysUserMapper;
import com.mengxuegu.web.service.SysUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * ServiceImpl<M extends BaseMapper<T>, T> 是对 IService接口中方法的实现
 * 第1个泛型 M 指定继承了 BaseMapper接口的子接口
 * 第2个泛型 T 指定实体类
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    /**
     * 默认密码 1234
     */
    private static final String PASSOWRD_DEFAULT = "$2a$10$rDkPvvAFV8kqwvKJzwlRv.i.q.wz1w1pz0SFsHn/55jNeZFQv/eCm";

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Override
    public SysUser findByUsername(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        // baseMapper 对应的是 SysUserMapper 实例
        return baseMapper.selectOne(queryWrapper);
    }


    @Override
    public SysUser findByMobile(String mobile) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<SysUser> selectPage(Page<SysUser> page, SysUser sysUser) {
        return baseMapper.selectPage(page, sysUser);
    }

    @Override
    public SysUser findById(Long id) {
        if (id == null) {
            return new SysUser();
        }
        // 查询用户信息
        SysUser sysUser = baseMapper.selectById(id);
        // 查询用户拥有角色
        List<SysRole> roleList = sysRoleMapper.findByUserId(id);
        sysUser.setRoleList(roleList);
        return sysUser;
    }

    @Override
    public boolean deleteById(Long id) {
        // 查询出用户信息
        SysUser sysUser = baseMapper.selectById(id);
        sysUser.setUpdateDate(new Date());
        // 是否可用设置为false表示删除
        sysUser.setEnabled(false);
        baseMapper.updateById(sysUser);
        return true;
    }

    @Transactional // 进行事务管理
    @Override
    public boolean saveOrUpdate(SysUser sysUser) {
        if (sysUser.getId() == null) {
            //新增 设置默认密码 1234
            sysUser.setPassword(PASSOWRD_DEFAULT);
        }
        // 更新时间
        sysUser.setUpdateDate(new Date());
        //新增或更新用户数据
        boolean flag = super.saveOrUpdate(sysUser);
        // 有数据操作，更新用户角色关系
        if (flag) {
            // 将用户角色关系表中对应数据删除
            baseMapper.deleteUserRoleByUserId(sysUser.getId());
            // 不为空则保存用户角色关系数据
            if (CollectionUtils.isNotEmpty(sysUser.getRoleIds())) {
                baseMapper.saveUserRole(sysUser.getId(), sysUser.getRoleIds());
            }
        }
        return flag;
    }
}
