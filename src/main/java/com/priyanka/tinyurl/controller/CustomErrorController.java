package com.priyanka.tinyurl.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    String error(HttpServletRequest request){
        switch((Integer)request.getAttribute("javax.servlet.error.status_code")){
            case 404:
                return "<h1 style=\"text-align:center;\">404 Not Found</h1>";
            default:
                return "<h1>There was an unexpected error</h1>";
        }
    }

    public String getErrorPath(){
        return "/error";
    }
}
