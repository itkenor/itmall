package com.itkenor.service;

import com.itkenor.common.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @PACKAGE_NAME:com.itkenor.service
 * @Auther: itkenor
 * @Date: 2018/4/30 11:53
 * @Description:
 */
public interface IFileService {
    String uploadFile(String path, MultipartFile multipartFile);
}
