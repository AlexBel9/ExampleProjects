package com.example.test_spring_one.services;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class DefaultController {
    @RequestMapping("/")
    public ModelAndView index(){
        return new ModelAndView("index");
    }
}
