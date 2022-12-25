package com.zerobase.lms.admin.dto;

import com.zerobase.lms.admin.entity.Category;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CategoryDto {

    Long id;
    String categoryName;
    int sortValue;
    boolean usingYn;

    int courseCount;

    public static List<CategoryDto> of(List<Category> categoryList) {
        if (categoryList != null) {
            List<CategoryDto> categoryDtoList = new ArrayList<>();
            for (Category x : categoryList) {
                categoryDtoList.add(of(x));
            }
            return categoryDtoList;
        }

        return null;
    }

    public static CategoryDto of(Category category) {

        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .sortValue(category.getSortValue())
                .usingYn(category.isUsingYn())
                .build();
    }
}
