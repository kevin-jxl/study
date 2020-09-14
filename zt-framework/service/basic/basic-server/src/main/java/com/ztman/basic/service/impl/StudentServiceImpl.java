package com.ztman.basic.service.impl;

import com.ztman.basic.service.StudentService;
import org.springframework.stereotype.Service;


@Service("studentService")
public class StudentServiceImpl implements StudentService {
    @Override
    public String info(String str) {
        return str;
    }
}
