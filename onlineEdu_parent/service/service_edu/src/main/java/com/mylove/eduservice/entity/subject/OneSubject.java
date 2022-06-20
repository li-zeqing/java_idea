package com.mylove.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zeqing Li Email:lizeqing77@163.com
 * @Description 一级分类的实体类
 * @date 2022-06-20 10:22
 */
@Data
public class OneSubject {
    private String id;

    private String title;

    //一个一级分类 有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
