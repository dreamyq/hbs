package cn.codemodel.common.model.entity.response;

import cn.codemodel.common.model.entity.company.Company;
import cn.codemodel.common.model.entity.company.Department;
import lombok.Data;

import java.util.List;

@Data
public class DeptListResult {
    private String companyId;
    private String companyName;
    private String companyManage;
    private List<Department> depts;
    public DeptListResult(Company company , List department){
        this.companyId=company.getId();
        this.companyName=company.getName();
        this.companyManage=company.getLegalRepresentative();//公司联系人
        this.depts=department;
    }
}
