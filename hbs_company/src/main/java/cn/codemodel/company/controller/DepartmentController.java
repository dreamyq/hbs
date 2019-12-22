package cn.codemodel.company.controller;

import cn.codemodel.common.model.entity.company.Company;
import cn.codemodel.common.model.entity.company.Department;
import cn.codemodel.common.model.entity.response.DeptListResult;
import cn.codemodel.company.service.CompanyService;
import cn.codemodel.company.service.DepartmentService;
import cn.codemodel.controller.BaseController;
import cn.codemodel.model.entity.Result;
import cn.codemodel.model.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/company")
public class DepartmentController extends BaseController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CompanyService companyService;

    /**
     * 添加部门
      * @param department
     * @return
     */
    @PostMapping("/departments")
    public Result add(@RequestBody Department department){
        department.setCompanyId(companyId);
        boolean eff = departmentService.save(department);
        if (eff){
            return Result.SUCCESS();
        }else {
            return Result.FAIL();
        }
    }
    @PutMapping("/departments/{id}")
    public Result update(@PathVariable(name = "id")String id,@RequestBody Department department){
        department.setCompanyId(companyId);
        department.setId(id);
        boolean eff = departmentService.updateById(department);
        if (eff){
            return Result.SUCCESS();
        }else {
            return Result.FAIL();
        }
    }
    @DeleteMapping("/departments/{id}")
    public Result delete(@PathVariable(name = "id")String id){
        boolean eff = departmentService.removeById(id);
        if (eff){
            return Result.SUCCESS();
        }else {
            return Result.FAIL();
        }
    }
    @GetMapping("/departments/{id}")
    public Result findById(@PathVariable(name = "id")String id){
        Department department = departmentService.findeById(id);
        return new Result(ResultCode.SUCCESS,department);
    }
    @GetMapping("/departments")
    public Result findAll(){
        Company company = companyService.findById(companyId);
        List<Department> depatment = departmentService.findAll(companyId);
        return new Result(ResultCode.SUCCESS,new DeptListResult(company,depatment));
    }


}
