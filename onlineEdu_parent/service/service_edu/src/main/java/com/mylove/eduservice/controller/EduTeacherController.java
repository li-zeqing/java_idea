package com.mylove.eduservice.controller;


import com.mylove.eduservice.entity.EduTeacher;
import com.mylove.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-06-03
 */
@Api(description="讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    //访问地址  http://localhost:8001/eduservice/teacher/findAll

    //把service注入
    @Autowired
    private EduTeacherService teacherService;

    //1. 查询讲师表所有的数据
    //rest风格
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public List<EduTeacher> findAll(){
        //调用service的方法实现查询所有的操作
        List<EduTeacher> list = teacherService.list(null);
        return list;
    }

    //逻辑删除
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public boolean removeById(@ApiParam(name = "id", value = "讲师ID", required = true)
                                  @PathVariable String id){

        return teacherService.removeById(id);
    }
}

