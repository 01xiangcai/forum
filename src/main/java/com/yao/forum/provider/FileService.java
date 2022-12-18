package com.yao.forum.provider;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.yao.forum.dto.FileDTO;
import com.yao.forum.exception.CustomizeErrorCode;
import com.yao.forum.exception.CustomizeException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileService {
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.AccessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.file.AccessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketname;


    public FileDTO upload(MultipartFile file) {

        String fileName = file.getOriginalFilename();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        FileDTO fileDTO = new FileDTO();
        try {
            //上传文件流
            InputStream inputStream = file.getInputStream();
            fileName = UUID.randomUUID().toString() + fileName;
            String path = new DateTime().toString("yyyy-MM-dd");
            fileName = path + "/" + fileName;

            ossClient.putObject(bucketname,fileName,inputStream);
            //关闭OSSClient
            ossClient.shutdown();

            String url = "https://" + bucketname + "." + endpoint + '/' + fileName;
            fileDTO.setUrl(url);
            fileDTO.setSuccess(1);
            fileDTO.setMessage("上传成功");
            return fileDTO;
        } catch (IOException e) {
            e.printStackTrace();
            fileDTO.setSuccess(0);
            fileDTO.setMessage("上传失败");
            return fileDTO;
        }

    }


}
