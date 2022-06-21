package com.mylove.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mylove.eduservice.entity.EduChapter;
import com.mylove.eduservice.entity.EduVideo;
import com.mylove.eduservice.entity.chapter.ChapterVo;
import com.mylove.eduservice.entity.chapter.VideoVo;
import com.mylove.eduservice.mapper.EduChapterMapper;
import com.mylove.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mylove.eduservice.service.EduVideoService;
import com.mylove.servicebase.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-06-20
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;//注入EduVideoService才能在此类中调用

    //课程大纲列表，根据课程id进行查询
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //1.根据课程id查询该课程的所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);

        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //2.根据课程id查询该课程的所有小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);


        //3.遍历查询章节list集合进行封装
            //创建list集合，用于最终封装数据
        List<ChapterVo> finalList = new ArrayList<>();
        for (int i = 0; i < eduChapterList.size(); i++) {
            //获取每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            //将EduChapter对象的值复制到ChapterVo对象中，为了最终放进finalList集合中返回
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            //把chapterVo放到finalList集合中
            finalList.add(chapterVo);

            //4.遍历查询小节list集合，进行封装 [一个章节对应多个小节，所以放在章节遍历中再遍历小节]
                //创建查询小节list集合，用于存储小节
            List<VideoVo> videoList = new ArrayList<>();

            //遍历小节
            for (int m = 0; m < eduVideoList.size(); m++) {
                //获取每个小节
                EduVideo eduVideo = eduVideoList.get(m);
                //判断该小节是否为本次遍历到的章节 【根据小节chapterId和章节Id是否一样判断】
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    //将EduVideo对象的值复制到VideoVo对象中，为了最终放进videoList集合中
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    //将小节封装到videoList集合中
                    videoList.add(videoVo);
                }
            }

            //遍历完每个章节的所有小节后，加入到该章节的children中
            chapterVo.setChildren(videoList);
        }



        return finalList;
    }

    //删除章节的方法
    @Override
    public Boolean deleteChapter(String chapterId) {

        //根据chapterid章节id查询小节表video，如何查出数据则不能删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        //判断是否查出小节数据
        if(count > 0){//查出小节数据 不能删除
            throw new MyException(20001,"不能删除");
        }else {//查不出小节数据 可以删除
            //删除章节
            int result = baseMapper.deleteById(chapterId);
            return result>0;

        }
    }
}
