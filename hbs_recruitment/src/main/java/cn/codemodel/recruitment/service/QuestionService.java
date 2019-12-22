package cn.codemodel.recruitment.service;

import cn.codemodel.common.model.entity.response.QuestionResult;
import cn.codemodel.recruitment.dao.ExamDao;
import cn.codemodel.recruitment.dao.QuestionDao;
import cn.codemodel.recruitment.model.entity.Exam;
import cn.codemodel.recruitment.model.entity.Question;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class QuestionService extends ServiceImpl<QuestionDao, Question> {
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private ExamDao examDao;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询需要得到的题
     *
     * @param num 要查询的条数
     * @return
     */
    public List<QuestionResult> getQuestion(String num, String mobile) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        List<Question> questions = questionDao.getRandomQuestionRandom(num);
        StringBuffer solution = new StringBuffer();
        List<QuestionResult> results = new ArrayList<>();
        int serialNum = 1;
        for (Question question : questions) {
            QuestionResult questionResult = new QuestionResult();
            questionResult.setTitle(serialNum + "、" + question.getTitle());
            serialNum++;
            //得到正确的答案
            String[] a1 = question.getAnswer().split("#¥#");
//            System.out.println(a1.length);
            //得到错误的答案
            String[] a2 = question.getError().split("#¥#");
//            System.out.println(a2.length);
            //得到答案的总数
            int length = a1.length + a2.length;
            String[] a3 = new String[length];
            //把打乱顺序后的正确答案的序号赋给solution
            int[] random = getRandom(1, length);
//            for (int i : random) {
//                System.out.print(i);
//            }
            //利用ascii得到字母A
            char option = 65;
//            System.out.println("left is random");
            for (int i = 0; i < length; i++) {
                if (i < a1.length) {
                    a3[random[i] - 1] = a1[i];
                    char da = (char)(64+random[i]);
                    solution.append(da + ",");
                } else {
                    a3[random[i] - 1] = a2[i - a1.length];
                }
            }
            for (int i = 0; i < a3.length; i++) {
                a3[i] = option + "：" + a3[i];
                option++;
                System.out.print(a3[i] + ",");
            }
            System.out.println();
            questionResult.setAnswer(a3);
            results.add(questionResult);
//            System.out.println("left is a3");
        }
        System.out.println("solution is " + solution);
        //打乱题的顺序，并记录题目的答案序号，把答案序号存入redis中，redis的key为该用户的手机号
        redisTemplate.opsForValue().set(mobile, solution,1, TimeUnit.DAYS);
        Object val = redisTemplate.opsForValue().get(mobile);
        System.out.println("val is =============" + val);
        return results;
    }

    public int result(String answer, String moblie,String testname) {
        String val = String.valueOf(redisTemplate.opsForValue().get(moblie));
        String[] v1 = val.split(",");
        String[] v2 = answer.split(",");
        int socre = 0;
        //todo
        //这里可以实现一个通过总题数和总分得到每道题的平均分
        int avg = 100 / v1.length;
        for (int i = 0; i < v1.length; i++) {
            if (v1[i].equals(v2[i])) {
                socre = socre + avg;
            }
        }
        Exam exam = new Exam();
        exam.setName(testname);
        exam.setPhone(moblie);
        exam.setScore(socre);
        examDao.insert(exam);
        redisTemplate.delete(moblie);
        return socre;
    }

    /**
     * 得到一个区间的不重复的随机数
     *
     * @param min
     * @param max
     * @return
     */
    private int[] getRandom(int min, int max) {
        int result[] = new int[max - min + 1];
        Random random = new Random();
        int num = random.nextInt(max - min + 1) + min;
        int temp = result.length;//临时变量，得到数组的长度，用来判定什么时候退出循环
        int i = 0;
        while (true) {
            if (!getHave(result, num)) {
                result[i] = num;
                i++;
                temp--;
            }
            if (temp == 0) {
                break;
            }
            num = random.nextInt(max - min + 1) + min;
        }
        return result;
    }

    private boolean getHave(int[] arr, int target) {
        for (int i : arr) {
            if (i == target) {
                return true;
            }
        }
        return false;
    }

}
