package cn.codemodel.recruitment.dao;

import cn.codemodel.recruitment.model.entity.Exam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface ExamDao extends BaseMapper<Exam> {
    public List<Exam> getExamDesc();
}
