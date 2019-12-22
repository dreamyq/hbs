package cn.codemodel.employee.service;

import cn.codemodel.common.model.entity.employee.EmployeeArchive;
import cn.codemodel.employee.dao.ArchiveDao;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * Author:xzengsf
 * Date:2018/10/23 11:33
 * Description:
 */
@Service
public class ArchiveService extends ServiceImpl<ArchiveDao, EmployeeArchive> {
    @Autowired
    private ArchiveDao archiveDao;

    @Override
    public boolean save(EmployeeArchive archive) {
        archive.setCreateTime(new Date());
        return super.save(archive);
    }

    public EmployeeArchive findLast(String companyId, String month) {
        EmployeeArchive archive = archiveDao.findByLast(companyId, month);
        return archive;
    }

    public List<EmployeeArchive> findAll(Integer page, Integer pagesize, String year, String companyId) {
        int index = (page - 1) * pagesize;
        return archiveDao.findAllData(companyId, year + "%", index, pagesize);
    }

    public Long countAll(String year, String companyId) {
        return archiveDao.countAllData(companyId, year + "%");
    }


    public Page<EmployeeArchive> findSearch(Map<String, Object> map, int page, int size) {
        //return archiveDao.findAll(createSpecification(map), PageRequest.of(page-1, size));
        return null;
    }

//    /**
//     * 动态条件构建
//     * @param searchMap
//     * @return
//     */
//    private Specification<EmployeeArchive> createSpecification(Map searchMap) {
//        return new Specification<EmployeeArchive>() {
//            @Override
//            public Predicate toPredicate(Root<EmployeeArchive> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                List<Predicate> predicateList = new ArrayList<Predicate>();
//                // 企业id
//                if (searchMap.get("companyId")!=null && !"".equals(searchMap.get("companyId"))) {
//                    predicateList.add(cb.like(root.get("companyId").as(String.class), (String)searchMap.get("companyId")));
//                }
//                if (searchMap.get("year")!=null && !"".equals(searchMap.get("year"))) {
//                    predicateList.add(cb.like(root.get("mouth").as(String.class), (String)searchMap.get("year")));
//                }
//                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
//            }
//        };
//    }
}
