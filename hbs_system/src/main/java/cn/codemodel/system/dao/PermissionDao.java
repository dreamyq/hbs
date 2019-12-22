package cn.codemodel.system.dao;

import cn.codemodel.common.model.entity.system.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionDao extends BaseMapper<Permission> {
    List<Permission> findByTypeAndPid(@Param("type") int type, @Param("pid") String pid);
    List<Permission> findPermissionByRoleId(String roleId);
}
