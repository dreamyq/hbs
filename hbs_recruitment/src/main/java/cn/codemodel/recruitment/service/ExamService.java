package cn.codemodel.recruitment.service;

import cn.codemodel.recruitment.dao.ExamDao;
import cn.codemodel.recruitment.model.entity.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {
    @Autowired
    private ExamDao examDao;

    public List<Exam> findAllDesc() {
        return examDao.getExamDesc();
    }
}
