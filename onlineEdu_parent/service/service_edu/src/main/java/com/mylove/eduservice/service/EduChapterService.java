package com.mylove.eduservice.service;

import com.mylove.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mylove.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-06-20
 */
public interface EduChapterService extends IService<EduChapter> {
    //课程大纲列表，根据课程id进行查询
    List<ChapterVo> getChapterVideoByCourseId(String courseId);
    //删除的方法
    Boolean deleteChapter(String chapterId);
}
