package com.mylove.vod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Zeqing Li Email:lizeqing77@163.com
 * @Description
 * @date 2022-07-01 18:10
 */
public interface VodService {
    //上传视频到阿里云并返回上传视频id
    String uploadVideoAly(MultipartFile file);
}
