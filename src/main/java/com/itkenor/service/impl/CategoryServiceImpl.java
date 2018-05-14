package com.itkenor.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.itkenor.common.ServerResponse;
import com.itkenor.dao.CategoryMapper;
import com.itkenor.dao.UserMapper;
import com.itkenor.pojo.Category;
import com.itkenor.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @PACKAGE_NAME:com.itkenor.service.impl
 * @Auther: itkenor
 * @Date: 2018/4/29 13:41
 * @Description:
 */

@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 添加商品
     * @param categoryName
     * @param parent_id
     * @return
     */
    @Override
    public ServerResponse<String> addCategory(String categoryName,Integer parent_id){
        if(StringUtils.isBlank(categoryName) || parent_id==null){
            return ServerResponse.createByError("添加商品信息错误，请重新添加");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parent_id);
        category.setStatus(true);
        int resultCount = categoryMapper.insert(category);
        if(resultCount==0){
            return ServerResponse.createByError("添加商品失败");
        }
        return ServerResponse.createBySuccessMessage("添加商品成功");
    }

    /**
     * 更新商品名称
     * @param categoryName
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse<String> updateCategoryName(String categoryName,Integer categoryId){
        if(StringUtils.isBlank(categoryName) || categoryId==null){
            return ServerResponse.createByError("添加商品信息错误，请重新添加");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setId(categoryId);
        int resultCount = categoryMapper.updateByPrimaryKeySelective(category);
        if(resultCount==0){
            return ServerResponse.createByError("更新商品名称失败");
        }
        return ServerResponse.createBySuccessMessage("更新商品名成功");
    }

    /**
     * 查找该分类下的子分类
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId) {
        List<Category> categoryList = categoryMapper.selectChildrenCategoryByParentID(categoryId);
        // 判断集合是否为空以及集合内是否有数据
        if(CollectionUtils.isEmpty(categoryList)){
            //无需返回数据，直接打印日志
            logger.info("找不到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    /**
     * 给定一个商品的分类id
     * 根据该id递归查询出其下所有子类的商品的分类id
     * 将查询出的所有商品的分类存储到一个可为空的List集合中
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId){
        //Sets.newHashSet返回一个可变的空的HashSet实例
        //当Category对象不存在是，set可以为空
        //该实例用于存储Category对象
        // return new HashSet<E>();
        Set<Category> categorySet = Sets.newHashSet();
        //调用递归函数
        findChildCategory(categorySet,categoryId);
        //将set集合中的所有category对象的分类id抽取出来
        //并将他们封装到可为空的List集合中
        List<Integer> categoryList = Lists.newArrayList();
        if(categoryId !=null){
            for (Category categoryItem : categorySet){
                categoryList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    /**
     * 根据一个分类的id，递归查询该分类下的所有子类，以及子类的子类（递归）
     * 根据查询到的所有分类id，查找出所有Category
     * 将所有category封装到set集合中
     * @param categorySet
     * @param categoryId
     * @return
     */
    public Set<Category> findChildCategory(Set<Category> categorySet,Integer categoryId){
        //根据商品分类的id查询category对象
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        //若category对象不为空，则将其存储到set集合中
        if(category != null){
            categorySet.add(category);
        }
        //查找子节点，递归一定要有退出条件
        //查询该分类下的所有子类，并返回一个Category集合
        List<Category> categoryList = categoryMapper.selectChildrenCategoryByParentID(categoryId);
        //遍历该集合
        for(Category categoryItem : categoryList){
            //递归
            findChildCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
    }
}
