package cn.codemodel.controller;

import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String companyId;
    protected String companyName;
    protected Claims claims;

    @ModelAttribute
    public void setReqAndResp(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        Object obj = request.getAttribute("user_claims");
        System.out.println("BaseController======"+obj);
        if (obj != null) {
            this.claims = (Claims) obj;
            this.companyId = (String) claims.get("companyId");
            System.out.println("BaseController===="+companyId);
            this.companyName = (String) claims.get("companyName");
        }
    }



}
