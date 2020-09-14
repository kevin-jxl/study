package com.ztman.admin.service.impl;

import com.ztman.admin.api.entity.Student;
import com.ztman.admin.mapper.StudentMapper;
import com.ztman.admin.service.StudentService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("studentService")
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
