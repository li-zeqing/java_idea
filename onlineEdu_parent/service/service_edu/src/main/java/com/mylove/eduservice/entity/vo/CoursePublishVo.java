package com.mylove.eduservice.entity.vo;

import lombok.Data;

/**
 * @author Zeqing Li Email:lizeqing77@163.com
 * @Description
 * @date 2022-06-22 9:35
 */
@Data
public class CoursePublishVo {
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
