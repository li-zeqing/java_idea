package com.mylove.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.mylove.oss.service.OssService;
import com.mylove.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author Zeqing Li Email:lizeqing77@163.com
 * @Description
 * @date 2022-06-18 10:07
 */
@Service
public class OssServiceImpl implements OssService {
    //上传文件到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        //工具类获取值
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try{

            //创建Oss实例
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String fileName = file.getOriginalFilename();

            //文件名称唯一化  在文件名称中加入随机唯一值
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid+fileName;

            //上传文件按日期进行分类
            //获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd");

            //拼接成 2022/06/18/hjhdf01.jpg
            fileName = datePath + "/" + fileName;

            //调用oss方法实现上传
            /*
            * 第一个参数：Bucket名称
            * 第二个参数：上传到oss文件路径/文件名称  例如/aa/bb/1.jpg
            * 第三个参数：上传文件输入流
            * */
            ossClient.putObject(bucketName,fileName,inputStream);

            //关闭OssClient
            ossClient.shutdown();

            //拼接 上传到oss的路径 并返回
            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
