package cn.codemodel.company.service;

import cn.codemodel.common.model.entity.company.Department;
import cn.codemodel.company.dao.DepartmentDao;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DepartmentService extends ServiceImpl<DepartmentDao, Department> {
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public boolean save(Department entity) {
        entity.setCreateTime(new Date());
        return super.save(entity);
    }

    @Override
    public boolean updateById(Department entity) {
        Department department = departmentDao.selectById(entity.getId());
        department.setName(entity.getName());
        department.setPid(entity.getPid());
        department.setManagerId(entity.getManagerId());
        department.setIntroduce(entity.getIntroduce());
        department.setManager(entity.getManager());
        return super.updateById(department);
    }
    public Department findeById(String id){
        return departmentDao.selectById(id);
    }
    public List<Department> findAll(String companyId){
        QueryWrapper<Department> query = new QueryWrapper<>();
        query.eq("company_id",companyId);
        return departmentDao.selectList(query);
    }
}
