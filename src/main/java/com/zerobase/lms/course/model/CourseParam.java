package com.zerobase.lms.course.model;

import com.zerobase.lms.admin.model.CommonParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseParam extends CommonParam {

    long id;
    long categoryId;
}
