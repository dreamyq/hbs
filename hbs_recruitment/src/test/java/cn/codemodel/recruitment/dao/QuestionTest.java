package cn.codemodel.recruitment.dao;

import cn.codemodel.recruitment.model.entity.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionTest {
    @Autowired
    private QuestionDao questionDao;
    @Test
    public void testRandomSelect(){
        List<Question> question = questionDao.getRandomQuestionRandom("10");
        for (Question question1 : question) {
            System.out.println(question1);
        }

    }
}
