package cn.codemodel.recruitment.service;

import cn.codemodel.common.model.entity.response.QuestionResult;
import cn.codemodel.recruitment.model.entity.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QuestionTest {
    @Autowired
    private QuestionService questionService;

    @Test
    public void testGetAll(){
        List<QuestionResult> question = questionService.getQuestion("30", "10086");
        System.out.println(question);
    }

    @Test
    public void testAscii(){
        //65
        char num = 65;
        System.out.println(num+"、"+"nihao");
    }
    @Test
    public void testRandom(){
//        int b=(int)(Math.random()*10);//生成[0,9]之间的随机整数。
//        System.out.println(b);
        long startTime = System.currentTimeMillis();
//        int[] random = questionService.getRandom(1, 3);
//        for (int i : random) {
//            System.out.print(i);
//        }
        System.out.println();
        long endTime = System.currentTimeMillis();
        System.out.println((endTime-startTime)+"ns");
    }

    @Test
    public void testAns(){
        int result = questionService.result("A,A,A,A,A,A,A,B,B,D", "1111","1111");
        System.out.println(result);
    }
}
