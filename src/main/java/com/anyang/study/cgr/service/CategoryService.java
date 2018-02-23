package com.anyang.study.cgr.service;

import com.anyang.study.cgr.repo.CategoryVO;

import java.util.List;

public interface CategoryService {
    public String insertCategory(CategoryVO categoryVO) throws Exception;
    public void updateCategory(CategoryVO categoryVO) throws Exception;
    public List<?> selectCategoryList() throws Exception;
    public void deleteCategory(CategoryVO categoryVO) throws Exception;
}
