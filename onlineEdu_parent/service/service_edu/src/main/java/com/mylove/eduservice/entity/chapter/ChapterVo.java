package com.mylove.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zeqing Li Email:lizeqing77@163.com
 * @Description
 * @date 2022-06-21 9:36
 */
@Data
public class ChapterVo {
    private String id;

    private String title;

    //表示小节部分
    private List<VideoVo> children = new ArrayList<>();
}
