package com.zerobase.lms.course.repository;

import com.zerobase.lms.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
