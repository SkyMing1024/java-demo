package com.sky.filedemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("/file")
public class FileController {

    @GetMapping("/test")
    public String test(String name){
        Map<String, Object> map = new HashMap<>();
        return "hello: "+name;
    }
}
