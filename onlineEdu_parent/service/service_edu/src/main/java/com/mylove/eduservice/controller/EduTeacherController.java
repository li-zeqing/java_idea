package com.mylove.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mylove.commonutils.R;
import com.mylove.eduservice.entity.EduTeacher;
import com.mylove.eduservice.entity.vo.TeacherQuery;
import com.mylove.eduservice.service.EduTeacherService;
import com.mylove.servicebase.exceptionhandler.MyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    public R findAll(){
        //调用service的方法实现查询所有的操作
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }

    //逻辑删除
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R removeById(@ApiParam(name = "id", value = "讲师ID", required = true)
                                  @PathVariable String id){

        boolean flag = teacherService.removeById(id);
        if (flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //分页查询讲师方法

    //current代表当前页 limit代表当前页的记录数
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit){

        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);

        /*
        //测试异常处理
        try{
            int i = 10/0;
        }catch (Exception e){
            //执行自定义异常
            throw new MyException(20001,"执行了MyException自定义异常");
        }

         */

        //调用方法实现分页
        //调用方法时，底层封装，把分页所有数据封装到pageTeacher对象里
        teacherService.page(pageTeacher,null);

        //总记录数
        long total = pageTeacher.getTotal();
        //当前页的所有数据
        List<EduTeacher> records = pageTeacher.getRecords();

        return R.ok().data("total",total).data("rows",records);

        /*第二种return写法
        * Map map = new HashMap();
        * map.put("total",total);
        * map.put("rows",records);
        * return R.ok().data(map);
        * */
    }

    //条件查询带分页方法
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);

        //构建查询条件QueryWrapper
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询
        //分别取出teacherQuery中的各条件值
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，如果不为空则拼接条件
        if(!StringUtils.isEmpty(name)){
            //构建条件
            wrapper.like("name",name);//模糊查询
        }
        if(!StringUtils.isEmpty(level)){
            //构建条件
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            //构建条件 ge 大于等于
            wrapper.ge("gmt_create",begin);//注意 传入的是数据库中表的列名称 不是实体类的名称
        }
        if(!StringUtils.isEmpty(end)){
            //构建条件 le 小于等于
            wrapper.le("gmt_create",end);//注意 传入的是数据库中表的列名称 不是实体类的名称
        }


        //调用方法实现分页
        //调用方法时，底层封装，把分页所有数据封装到pageTeacher对象里
        teacherService.page(pageTeacher,wrapper);

        //总记录数
        long total = pageTeacher.getTotal();
        //当前页的所有数据
        List<EduTeacher> records = pageTeacher.getRecords();

        return R.ok().data("total",total).data("rows",records);

    }

    //添加讲师方法
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //根据讲师id进行查询
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    //讲师修改功能
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = teacherService.updateById(eduTeacher);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }

    }

}

