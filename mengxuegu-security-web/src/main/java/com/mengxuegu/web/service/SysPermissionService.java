package com.mengxuegu.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mengxuegu.web.entities.SysPermission;

import java.util.List;

public interface SysPermissionService  extends IService<SysPermission> {

    /**
     *
     * @param userId 用户Id
     * @return 用户所拥有的权限
     */
    List<SysPermission> findByUserId(Long userId);

    /**
     * 通过权限资源删除权限资源
     */
    boolean deleteById(Long id);


}
