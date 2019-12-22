package cn.codemodel.recruitment.controller;

import cn.codemodel.common.model.entity.response.QuestionResult;
import cn.codemodel.model.entity.Result;
import cn.codemodel.model.enums.ResultCode;
import cn.codemodel.recruitment.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/recruit")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping("/question")
    public Result getQuestion(@RequestBody Map map) {
        String mobile = (String) map.get("mobile");
        String testname = (String)map.get("testname");
        System.out.println("mobile ======"+mobile);
        List<QuestionResult> question = questionService.getQuestion("10", mobile);
        return new Result(ResultCode.SUCCESS,question);
    }

    @PostMapping("/submit")
    public Result submit(@RequestBody Map map) {
        String answer = (String) map.get("answer");
        String mobile = (String) map.get("mobile");
        String testname = (String)map.get("testname");
        System.out.println("answer====="+answer);
        int score = questionService.result(answer, mobile,testname);
        return new Result(ResultCode.SUCCESS, score);
    }
}
