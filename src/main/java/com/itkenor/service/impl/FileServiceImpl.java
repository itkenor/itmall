package com.itkenor.service.impl;

import com.google.common.collect.Lists;
import com.itkenor.common.ServerResponse;
import com.itkenor.dao.ProductMapper;
import com.itkenor.service.IFileService;
import com.itkenor.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @PACKAGE_NAME:com.itkenor.service.impl
 * @Auther: itkenor
 * @Date: 2018/4/30 11:53
 * @Description:
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private ProductMapper productMapper;

    @Override
    public String uploadFile(String path, MultipartFile multipartFile){
        String originalFilename = multipartFile.getOriginalFilename();
        String newFileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        File newFile = new File(path +newFileName);
        if(!newFile.exists()){
            newFile.setWritable(true);
            newFile.mkdirs();
        }
        try {
            //将内存中的数据保存到newFile中
            multipartFile.transferTo(newFile);
            //将newFile文件上传到FTP图片服务器
            FTPUtil.uploadFile(Lists.newArrayList(newFile));
        } catch (IOException e) {
            logger.info("文件传输异常"+e);
            return null;
        }
        return newFile.getName();
    }
}
