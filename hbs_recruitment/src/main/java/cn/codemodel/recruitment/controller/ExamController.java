package cn.codemodel.recruitment.controller;

import cn.codemodel.model.entity.Result;
import cn.codemodel.model.enums.ResultCode;
import cn.codemodel.recruitment.model.entity.Exam;
import cn.codemodel.recruitment.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/recruit")
public class ExamController {
    @Autowired
    private ExamService examService;

    @GetMapping("/score")
    public Result getScore() {
        List<Exam> examData = examService.findAllDesc();
        return new Result(ResultCode.SUCCESS, examData);
    }
}
