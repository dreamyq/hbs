package cn.codemodel.system.controller;

import cn.codemodel.common.model.entity.system.Role;
import cn.codemodel.controller.BaseController;
import cn.codemodel.model.entity.PageResult;
import cn.codemodel.model.entity.Result;
import cn.codemodel.model.enums.ResultCode;
import cn.codemodel.system.service.RoleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/system")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    /**
     * 分配权限
     */
    @PutMapping("/role/assignPrem")
    public Result assignPrem(@RequestBody Map<String,Object> map) {
        //1.获取被分配的角色的id
        String roleId = (String) map.get("id");
        //2.获取到权限的id列表
        List<String> permIds = (List<String>) map.get("permIds");
        //3.调用service完成权限分配
        roleService.assignPerms(roleId,permIds);
        return new Result(ResultCode.SUCCESS);
    }
    //添加角色
    @PostMapping("/role")
    public Result add(@RequestBody Role role) throws Exception {
        role.setCompanyId(companyId);
        roleService.save(role);
        return Result.SUCCESS();
    }

    //更新角色
    @RequestMapping(value = "/role/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(name = "id") String id, @RequestBody Role role)
            throws Exception {
        roleService.update(role);
        return Result.SUCCESS();
    }

    //删除角色
    @RequestMapping(value = "/role/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(name = "id") String id) throws Exception {
        roleService.removeById(id);
        return Result.SUCCESS();
    }

    /**
     * 根据ID获取角色信息
     */
    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(name = "id") String id) throws Exception {
        Role role = roleService.findById(id);
        return new Result(ResultCode.SUCCESS, role);
    }

    /**
     * 分页查询角色
     */
    @PostMapping("/role/search/{page}/{size}")
    public Result findByPage(@PathVariable("page") int page, @PathVariable("size") int size, @RequestBody Role role) throws Exception {
        role.setCompanyId(companyId);
        Page<Role> searchPage = roleService.findSearch(role, page, size);
        PageResult<Role> pr = new PageResult(searchPage.getTotal(), searchPage.getRecords());
        System.out.println(pr);
        return new Result(ResultCode.SUCCESS, pr);
    }
    @GetMapping("/role/list")
    public Result findAll(){
        return new Result(ResultCode.SUCCESS,roleService.getAll());
    }
}
