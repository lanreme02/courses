package com.cognizant.courses;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

    @PostMapping("/courses")
    public String addCourse(@RequestBody CourseRequest request){
            if(request.getCourseId().isEmpty() || request.getName().isEmpty())
                 return MessageConstant.ERROR;

            return null;
    }

}
