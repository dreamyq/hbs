package cn.codemodel.recruitment.dao;


import cn.codemodel.recruitment.model.entity.Exam;
import cn.codemodel.recruitment.model.entity.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExamTest {
    @Autowired
    private ExamDao examDao;
    @Autowired
    private QuestionDao questionDao;

    @Test
    public void  testQuestion(){
        List<Question> questions = questionDao.selectList(null);
        System.out.println(questions);
    }

    @Test
    public void testSelect(){
        List<Exam> exams = examDao.getExamDesc();
        exams.forEach(System.out::println);
    }
    @Test
    public void testInsert(){
        Exam exam = new Exam();
        exam.setName("lyq");
        exam.setPhone("10086");
        exam.setScore(100);
        System.out.println(examDao.insert(exam));
    }

    @Test
    public void testList(){
        int [] a = new int[]{1,2};
        HashSet hashSet = new HashSet();
        hashSet.add("a");
        hashSet.add("b");
        hashSet.add("a");
        System.out.println(hashSet.size());
        System.out.println(hashSet);
        System.out.println(a.length);
    }

}