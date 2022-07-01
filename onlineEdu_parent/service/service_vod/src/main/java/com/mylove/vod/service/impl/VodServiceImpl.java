package com.mylove.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.mylove.vod.Utils.ConstantVodUtils;
import com.mylove.vod.service.VodService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author Zeqing Li Email:lizeqing77@163.com
 * @Description
 * @date 2022-07-01 18:10
 */
@Service
public class VodServiceImpl implements VodService {

    //上传视频到阿里云并返回上传视频id
    @Override
    public String uploadVideoAly(MultipartFile file) {

        try {
            //accessKeyId, accessKeySecret 阿里云的公钥和秘钥
            //fileName :上传文件原始名称 例如：123.mp4
            String fileName = file.getOriginalFilename();
            //title :上传之后显示的名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            //inputStream :上传文件输入流
            InputStream inputStream = file.getInputStream();

            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);


            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }

            return videoId;
        }catch (Exception e){

            return null;
        }


    }
}
