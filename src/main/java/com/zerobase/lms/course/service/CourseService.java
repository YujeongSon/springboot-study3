package com.zerobase.lms.course.service;

import com.zerobase.lms.course.dto.CourseDto;
import com.zerobase.lms.course.model.CourseInput;
import com.zerobase.lms.course.model.CourseParam;

import java.util.List;

public interface CourseService {

    // 강좌 등록(관리자용)
    boolean add(CourseInput parameter);

    // 강좌 정보 수정(관리자용)
    boolean set(CourseInput parameter);

    // 강좌 목록(관리자용)
    List<CourseDto> list(CourseParam parameter);

    // 강좌 상세 정보(관리자용)
    CourseDto getById(long id);

    // 강좌 삭제(관리자용)
    boolean delete(String idList);

    // 프론트 강좌 목록
    List<CourseDto> frontList(CourseParam parameter);
}
