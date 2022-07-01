package com.mylove.vod.controller;

import com.mylove.commonutils.R;
import com.mylove.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Zeqing Li Email:lizeqing77@163.com
 * @Description
 * @date 2022-07-01 18:08
 */
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin //解决跨域问题
public class VodController {

    @Autowired
    private VodService vodService;

    //1.上传视频到阿里云的方法
    public R uploadAlyVideo(MultipartFile file){
        //上传视频到阿里云并返回上传视频id
        String videoId = vodService.uploadVideoAly(file);

        return R.ok().data("videoId",videoId);
    }

}
