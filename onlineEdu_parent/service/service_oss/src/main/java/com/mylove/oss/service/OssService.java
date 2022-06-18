package com.mylove.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Zeqing Li Email:lizeqing77@163.com
 * @Description
 * @date 2022-06-18 10:06
 */
public interface OssService {
    //上传文件到oss
    String uploadFileAvatar(MultipartFile file);
}
