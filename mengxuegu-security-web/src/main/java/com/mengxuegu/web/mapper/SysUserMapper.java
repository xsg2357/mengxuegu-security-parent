package com.mengxuegu.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengxuegu.web.entities.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {


    IPage<SysUser> selectPage(Page<SysUser> page, @Param("u") SysUser sysUser);

    /**
     * 删除角色-用户关联根据用户ID
     */
    boolean deleteUserRoleByUserId(@Param("id") Long userId);

    /**
     * 保存用户与角色关系数据
     */
    boolean saveUserRole(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);


}
