package cn.codemodel.company.controller;

import cn.codemodel.common.model.entity.company.Company;
import cn.codemodel.company.service.CompanyService;
import cn.codemodel.model.entity.Result;
import cn.codemodel.model.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
@CrossOrigin
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    /**
     * add company
     */
    @PostMapping
    public Result add(@RequestBody Company company) {
        companyService.add(company);
        return Result.SUCCESS();
    }

    /**
     * update company by id
     */
    @PutMapping("/{id}")
    public Result update(@PathVariable(name = "id") String id, @RequestBody Company company) {
        company.setId(id);
        int effNum = companyService.updateById(company);
        if (effNum > 0) {
            return Result.SUCCESS();
        } else {
            return Result.FAIL();
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable(name = "id") String id) {
        int effectNum = companyService.deleteById(id);
        if (effectNum > 0) {
            return Result.SUCCESS();
        } else {
            return Result.FAIL();
        }
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable(name = "id") String id) throws Exception {
        Company company = companyService.findById(id);
        return new Result(ResultCode.SUCCESS);
    }
    @GetMapping
    public Result findeAll(){
        List<Company> companies = companyService.findAll();
        return new Result(ResultCode.SUCCESS,companies);
    }

}
