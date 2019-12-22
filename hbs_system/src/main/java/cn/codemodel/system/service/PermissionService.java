package cn.codemodel.system.service;

import cn.codemodel.common.model.entity.system.Permission;
import cn.codemodel.common.model.entity.system.PermissionApi;
import cn.codemodel.common.model.entity.system.PermissionMenu;
import cn.codemodel.common.model.entity.system.PermissionPoint;
import cn.codemodel.model.enums.ResultCode;
import cn.codemodel.model.exception.CommonException;
import cn.codemodel.system.dao.PermissionApiDao;
import cn.codemodel.system.dao.PermissionDao;
import cn.codemodel.system.dao.PermissionMenuDao;
import cn.codemodel.system.dao.PermissionPointDao;
import cn.codemodel.util.BeanMapUtils;
import cn.codemodel.util.PermissionConstants;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PermissionService {

    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private PermissionMenuDao permissionMenuDao;
    @Autowired
    private PermissionPointDao permissionPointDao;
    @Autowired
    private PermissionApiDao permissionApiDao;

    public void savePermission(Map<String, Object> map) throws Exception {
        //1.通过map构造permission对象
        Permission perm = BeanMapUtils.mapToBean(map, Permission.class);
        //2.根据类型构造不同的资源对象(菜单，按钮，api)
        int type = perm.getType();
        switch (type) {
            case PermissionConstants.PY_MENU:
                PermissionMenu menu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                permissionMenuDao.insert(menu);
                break;
            case PermissionConstants.PY_POINT:
                PermissionPoint point =
                        BeanMapUtils.mapToBean(map, PermissionPoint.class);
                permissionPointDao.insert(point);
                break;
            case PermissionConstants.PY_API:
                PermissionApi api = BeanMapUtils.mapToBean(map, PermissionApi.class);
                permissionApiDao.insert(api);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
        permissionDao.insert(perm);
    }

    /**
     * 2.更新权限
     */
    public void update(Map<String, Object> map) throws Exception {
        Permission perm = BeanMapUtils.mapToBean(map, Permission.class);
        Permission permission = permissionDao.selectById(perm.getId());
        permission.setName(perm.getName());
        permission.setCode(perm.getCode());
        permission.setDescription(perm.getDescription());
        permission.setEnVisible(perm.getEnVisible());
        //2.根据类型构造不同的资源
        int type = perm.getType();
        switch (type) {
            case PermissionConstants.PY_MENU:
                PermissionMenu menu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                menu.setId(perm.getId());
                permissionMenuDao.insert(menu);
                break;
            case PermissionConstants.PY_POINT:
                PermissionPoint point = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                point.setId(perm.getId());
                permissionPointDao.insert(point);
                break;
            case PermissionConstants.PY_API:
                PermissionApi api = BeanMapUtils.mapToBean(map, PermissionApi.class);
                api.setId(perm.getId());
                permissionApiDao.insert(api);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
        permissionDao.insert(perm);
    }

    /**
     * 3.根据id查询
     * //1.查询权限
     * //2.根据权限的类型查询资源
     * //3.构造map集合
     */
    public Map<String, Object> findById(String id) throws Exception {
        Permission perm = permissionDao.selectById(id);
        int type = perm.getType();
        Object object = null;
        if (type == PermissionConstants.PY_MENU) {
            object = permissionMenuDao.selectById(id);
        } else if (type == PermissionConstants.PY_POINT) {
            object = permissionPointDao.selectById(id);
        } else if (type == PermissionConstants.PY_API) {
            object = permissionApiDao.selectById(id);
        } else {
            throw new CommonException(ResultCode.FAIL);
        }
        Map<String, Object> map = BeanMapUtils.beanToMap(object);
        map.put("name", perm.getName());
        map.put("type", perm.getType());
        map.put("code", perm.getCode());
        map.put("description", perm.getDescription());
        map.put("pid", perm.getPid());
        map.put("enVisible", perm.getEnVisible());
        return map;
    }

    /**
     * 4.查询全部
     * type : 查询全部权限列表type:0:菜单 + 按钮(权限点) 1:菜单2:按钮(权限点)3:API接口
     * enVisible : 0:查询所有saas平台的最高权限，1:查询企业的权限 * pid :父id
     */
    public List<Permission> findAll(Map<String, Object> map) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        if (null!=map.get("enVisible")){
            queryWrapper.eq("en_visible",map.get("enVisible"));
        }
        return permissionDao.selectList(queryWrapper);
    }

    /**
     * 根据id删除
     * 1.删除权限
     * 2.删除权限对应的资源 *
     */
    public void deleteById(String id) throws Exception {
        //1.通过传递的权限id查询权限
        Permission permission = permissionDao.selectById(id);
        permissionDao.deleteById(id);
        int type = permission.getType();
        switch (type) {
            case PermissionConstants.PY_MENU:
                permissionMenuDao.deleteById(id);
                break;
            case PermissionConstants.PY_POINT:
                permissionPointDao.deleteById(id);
                break;
            case PermissionConstants.PY_API:
                permissionApiDao.deleteById(id);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }

    }
}
