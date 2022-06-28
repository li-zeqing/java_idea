package com.mylove.eduservice.service.impl;

import com.mylove.eduservice.entity.EduCourse;
import com.mylove.eduservice.entity.EduCourseDescription;
import com.mylove.eduservice.entity.vo.CourseInfoVo;
import com.mylove.eduservice.entity.vo.CoursePublishVo;
import com.mylove.eduservice.mapper.EduCourseMapper;
import com.mylove.eduservice.service.EduChapterService;
import com.mylove.eduservice.service.EduCourseDescriptionService;
import com.mylove.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mylove.eduservice.service.EduVideoService;
import com.mylove.servicebase.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-06-20
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //课程描述注入
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    //课程章节注入
    @Autowired
    private EduChapterService chapterService;

    //课程小节注入
    @Autowired
    private EduVideoService eduVideoService;

    //添加课程基本信息的方法
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1.向课程表添加课程基本信息 edu_course
            //CourseInfoVo对象转换为EduCourse对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0){
            //添加失败
            throw new MyException(20001,"添加课程信息失败");
        }

        //添加成功后 获取课程id 使得课程简介id与课程id一样
        String cid = eduCourse.getId();

        //2.向课程简介表添加课程简介 edu_course_description
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        //设置课程简介id为课程id
        courseDescription.setId(cid);

        courseDescriptionService.save(courseDescription);

        return cid;
    }

    //根据课程id查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        //1.查询课程表

        EduCourse eduCourse = baseMapper.selectById(courseId);

        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //2.查询课程简介表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {

        //1.修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0){//修改失败
            throw new MyException(20001,"修改课程表失败");
        }

        //2.修改课程简介表

        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);

    }

    //根据课程id查询课程确认信息
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用Mapper
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    //删除课程
    @Override
    public void removeCourse(String courseId) {
        //1.根据课程id删除课程小节
        eduVideoService.removeVideoByCourseId(courseId);

        //2.根据课程id删除课程章节
        chapterService.removeChapterByCourseId(courseId);

        //3.根据课程id删除课程简介
        courseDescriptionService.removeById(courseId);

        //4.根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if (result == 0){
            throw new MyException(20001,"课程基本信息删除失败");
        }
    }
}
