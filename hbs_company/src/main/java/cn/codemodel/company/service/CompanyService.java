package cn.codemodel.company.service;

import cn.codemodel.common.model.entity.company.Company;
import cn.codemodel.company.dao.CompanyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CompanyService {
    @Autowired
    private CompanyDao companyDao;

    public void add(Company company){
        company.setCreateTime(new Date());
        company.setState(1);
        company.setAuditState("0");
        company.setBalance(0d);
        companyDao.insert(company);
    }
    public int updateById(Company company){
          return companyDao.updateById(company);
    }
    public Company findById(String id){
        return companyDao.selectById(id);
    }
    public int deleteById(String id){
        return companyDao.deleteById(id);
    }
    public List<Company> findAll(){
        return companyDao.selectList(null);
    }

}
