package com.mengxuegu.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengxuegu.web.entities.SysPermission;
import com.mengxuegu.web.entities.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysRoleMapper  extends BaseMapper<SysRole> {

    /**
     * 页查询：只要将 Page<T> 分页对象作为1个参数传入，MyBatis-Plus 会自动对查询语句加上分页处理
     *  注意：Page<T> 必须是第1个参数，其他业务参数要加上 @Param 注解指定参数别名
     */
    IPage<SysRole> selectPage(Page<SysRole> page, @Param("p") SysRole sysRole);


    /**
     * 根据角色id 删除角色与资源权限关联数据
     * @param roleId 角色id
     */
    boolean deleteRolePermissionByRoleId(@Param("roleId") Long roleId);
    /**
     * 保存角色与资源权限关系数据
     * @param roleId 角色id
     * @param perIds 权限资源id集合
     */
    boolean saveRolePermission(@Param("roleId") Long roleId, @Param("perIds") List<Long> perIds);


    /**
     * 根据用户ID获取拥有角色
     *
     * @param userId 用户ID
     * @return 角色集合
     */
    List<SysRole> findByUserId(@Param("userId") Long userId);


}
