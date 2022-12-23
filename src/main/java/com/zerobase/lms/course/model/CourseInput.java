package com.zerobase.lms.course.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseInput {

    long id;
    long categoryId;
    String subject;
    String keyword;
    String summary;
    String contents;
    long price;
    long salePrice;
    String saleEndDtText;

    // 삭제 관련
    String idList;
}
