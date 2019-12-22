package cn.codemodel.system.service;

import cn.codemodel.common.model.entity.system.Permission;
import cn.codemodel.common.model.entity.system.Role;
import cn.codemodel.system.dao.PermissionDao;
import cn.codemodel.system.dao.RoleDao;
import cn.codemodel.util.PermissionConstants;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService extends ServiceImpl<RoleDao, Role> {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    /**
     * 修改角色
     * @param role
     */
    public void update(Role role){
        Role target = roleDao.selectById(role.getId());
        target.setDescription(role.getDescription());
        target.setName(role.getName());
        roleDao.insert(role);
    }

    /**
     * 根据条件查询角色
     * @param role
     * @param page
     * @param size
     * @return
     */
    public Page<Role> findSearch(Role role, int page, int size) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_id", role.getCompanyId());
        Page<Role> page1 = new Page<>(page, size);
        IPage<Role> userIPage = roleDao.selectPage(page1, queryWrapper);
        return (Page<Role>) userIPage;
    }
    /**
     * 分配权限
     */
    public void assignPerms(String roleId, List<String> permIds) {
//        //1.获取分配的角色对象
//        Role role = roleDao.selectById(roleId);
//        //2.构造角色的权限集合
//        List<Permission> perms = new ArrayList<>();
//        for (String permId : permIds) {
//            Permission permission = permissionDao.selectById(permId);
//            //需要根据父id和类型查询API权限列表
//            List<Permission> apiList = permissionDao.findByTypeAndPid(PermissionConstants.PY_API, permission.getId());
//            perms.addAll(apiList);//自定赋予API权限
//            perms.add(permission);//当前菜单或按钮的权限
//        }
//        System.out.println(perms.size());
//        //3.设置角色和权限的关系
//        role.setPermissions(perms);
//        //4.更新角色
//        roleDao.insert(role);
        roleDao.deleteRoleAndPermission(roleId);
        roleDao.addPermissionForRole(roleId,permIds);
    }

    public List<Role> getAll() {
        return roleDao.selectList(null);

    }

    public Role findById(String id) {
       return roleDao.findById(id);
    }
}
