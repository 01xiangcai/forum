package com.yao.forum.Controller;

import com.yao.forum.dto.FileDTO;
import com.yao.forum.provider.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;


@Controller
public class FileController {

    @Autowired
    FileService fileService;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upLoad(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        FileDTO fileDTO = fileService.upload(file);
        return fileDTO;
    }
}
