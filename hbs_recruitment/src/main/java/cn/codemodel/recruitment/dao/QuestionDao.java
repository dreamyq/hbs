package cn.codemodel.recruitment.dao;

import cn.codemodel.recruitment.model.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionDao extends BaseMapper<Question> {
    List<Question> getRandomQuestionRandom(String num);
}
