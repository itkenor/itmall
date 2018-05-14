package com.itkenor.service;

import com.itkenor.common.ServerResponse;
import com.itkenor.pojo.Category;

import java.util.List;

/**
 * @PACKAGE_NAME:com.itkenor.service
 * @Auther: itkenor
 * @Date: 2018/4/29 13:41
 * @Description:
 */
public interface ICategoryService {

    ServerResponse<String> addCategory(String categoryName, Integer parent_id);
    ServerResponse<String> updateCategoryName(String categoryName,Integer categoryId);
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
