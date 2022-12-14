package com.zerobase.lms.course.dto;

import com.zerobase.lms.course.entity.Course;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CourseDto {

    Long id;
    long categoryId;
    String imagePath;
    String keyword;
    String subject;
    String summary;
    String contents;
    long price;
    long salePrice;
    LocalDate saleEndDate;
    LocalDateTime regDate;
    LocalDateTime udtDate;

    long totalCount;
    long seq;

    public static CourseDto of(Course course) {

        return CourseDto.builder()
                .id(course.getId())
                .categoryId(course.getCategoryId())
                .imagePath(course.getImagePath())
                .keyword(course.getKeyword())
                .subject(course.getSubject())
                .summary(course.getSummary())
                .contents(course.getContents())
                .price(course.getPrice())
                .salePrice(course.getSalePrice())
                .saleEndDate(course.getSaleEndDate())
                .regDate(course.getRegDate())
                .udtDate(course.getUdtDate())
                .build();
    }

    public static List<CourseDto> of(List<Course> courseList) {

        if (courseList == null) {
            return null;
        }

        List<CourseDto> courseDtoList = new ArrayList<>();
        for (Course x : courseList) {
            courseDtoList.add(CourseDto.of(x));
        }
        return courseDtoList;
    }
}
