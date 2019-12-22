package cn.codemodel.system.dao;

import cn.codemodel.common.model.entity.system.Permission;
import cn.codemodel.common.model.entity.system.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDao extends BaseMapper<Role> {
    public List<Permission> getRolesByUserId(String userId);
    public Role findById(@Param("roleId") String roleId);
    public int deleteRoleAndPermission(@Param("roleId")  String roleId);
    public int addPermissionForRole(@Param("roleId") String roleId,@Param("permissions") List<String> permissions);
}
