package com.zerobase.lms.course.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CourseDto {

    long id;
    String imagePath;
    String keyword;
    String subject;
    String summary;
    String contents;
    long price;
    long salePrice;
    LocalDateTime saleEndDate;
    LocalDateTime regDate;
    LocalDateTime udtDate;

    long totalCount;
    long seq;
}
