package com.cognizant.courses.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface CoursesRepository extends CrudRepository<CourseEntity,String> {

}
