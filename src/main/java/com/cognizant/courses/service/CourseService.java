package com.cognizant.courses.service;

import com.cognizant.courses.controller.CourseRequest;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    public boolean validateBody(CourseRequest request){

        if( null == request.getCourseId() || request.getCourseId().isEmpty() ||
                null == request.getName() || request.getName().isEmpty()
                || null == request.getDescription())
            return false;
        return true;
    }
}
