package com.zerobase.lms.admin.service;

import com.zerobase.lms.admin.dto.CategoryDto;
import com.zerobase.lms.admin.entity.Category;
import com.zerobase.lms.admin.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> list() {

        List<Category> categoryList = categoryRepository.findAll();

        return CategoryDto.of(categoryList);
    }

    @Override
    public boolean add(String categoryName) {

        // 카테고리명 중복 체크!!

        Category category = Category.builder()
                            .categoryName(categoryName)
                            .usingYn(true)
                            .sortValue(0)
                            .build();

        categoryRepository.save(category);

        return true;
    }

    @Override
    public boolean update(CategoryDto parameter) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
