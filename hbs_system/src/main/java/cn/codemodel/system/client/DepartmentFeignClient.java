package cn.codemodel.system.client;

import cn.codemodel.common.model.entity.company.Department;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ihrm-company")
public interface DepartmentFeignClient {
    @RequestMapping(value = "/company/departments/search/", method = RequestMethod.POST)
    public Department findById(@RequestParam(value = "code") String code,
                               @RequestParam(value = "companyId") String companyId)
            throws Exception;
}
