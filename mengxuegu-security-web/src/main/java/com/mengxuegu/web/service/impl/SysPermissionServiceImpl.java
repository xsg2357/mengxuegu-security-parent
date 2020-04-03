package com.mengxuegu.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengxuegu.web.entities.SysPermission;
import com.mengxuegu.web.mapper.SysPermissionMapper;
import com.mengxuegu.web.service.SysPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {
    @Override
    public List<SysPermission> findByUserId(Long userId) {
        if (userId == null) {
            return null;
        }
        List<SysPermission> list = baseMapper.selectPermissionByUserId(userId);
        //用户无任何权限时，list会有一个 `null` 空的SysPermission 对象，把那个null移除
        list.remove(null);
        return list;
    }

    @Override
    public boolean deleteById(Long id) {
        //删除权限资源
        baseMapper.deleteById(id);

        // 删除 parent_id = id的子资源
        LambdaQueryWrapper<SysPermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysPermission::getParentId, id);
        baseMapper.delete(wrapper);
        return false;
    }
}
