package com.mengxuegu.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mengxuegu.web.entities.SysPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysPermissionMapper extends BaseMapper<SysPermission> {


    /**
     * 根据用户ID获取拥有资源权限
     * @param userId 用户Id
     */
    List<SysPermission> selectPermissionByUserId(@Param("userId")Long userId);


    /**
     *  根据角色Id获取角色资源
     * @param roleId 角色Id
     */
    List<SysPermission> findByRoleId(@Param("roleId")Long roleId);


}
