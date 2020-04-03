package com.mengxuegu.web.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengxuegu.web.entities.SysPermission;
import com.mengxuegu.web.entities.SysRole;
import com.mengxuegu.web.mapper.SysPermissionMapper;
import com.mengxuegu.web.mapper.SysRoleMapper;
import com.mengxuegu.web.service.SysRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ServiceImpl<M extends BaseMapper<T>, T> 是对 IService接口中方法的实现
 * 第1个泛型 M 指定继承了 BaseMapper接口的子接口
 * 第2个泛型 T 指定实体类
 */
@Service
public class SysUserRoleImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    SysPermissionMapper sysPermissionMapper;


    @Override
    public IPage<SysRole> selectPage(Page<SysRole> page, SysRole sysRole) {
        return baseMapper.selectPage(page, sysRole);
    }


    @Override
    public SysRole findById(Long id) {
        if (id == null) {
            return new SysRole();
        }
        // 查询角色信息
        SysRole sysRole = baseMapper.selectById(id);
        // 查询角色拥有资源权限
        List<SysPermission> permissionList = sysPermissionMapper.selectPermissionByUserId(sysRole.getId());
        sysRole.setPerList(permissionList);

        return sysRole;

    }

    @Transactional // 进行事务管理
    @Override
    public boolean deleteById(Long id) {
        // 通过角色id删除角色表数据
        baseMapper.deleteById(id);
        // 通过角色id删除角色权限资源关系表数据
        baseMapper.deleteRolePermissionByRoleId(id);
        return true;
    }

    @Transactional // 进行事务管理
    @Override
    public boolean saveOrUpdate(SysRole sysRole) {
        // 更新时间
        sysRole.setUpdateDate(new Date());
        //新增或更新数据
        boolean flag = super.saveOrUpdate(sysRole);
        // 有数据操作，更新角色资源权限关系
        if (flag) {
            // 先将当前角色对应角色权限资源关系表中的数据删除
            baseMapper.deleteRolePermissionByRoleId(sysRole.getId());
            // 不为空,则将新选择的权限资源保存到角色权限资源关系表中
            if (CollectionUtils.isNotEmpty(sysRole.getPerIds())) {
//                List<Long> ids = new ArrayList<>();
//                List<String> perIds = sysRole.getPerIds();
//                for (String perId : perIds) {
//                    ids.add(Long.parseLong(perId));
//                }
                baseMapper.saveRolePermission(sysRole.getId(), sysRole.getPerIds());
            }
        }
        return flag;
    }
}
