package com.cognizant.courses;

import com.cognizant.courses.model.CourseEntity;
import com.cognizant.courses.model.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {

    @Autowired
    CoursesRepository repository;

    @GetMapping("/courses")
    public Iterable<CourseEntity> getAll(){
        return repository.findAll();
    }


    @PostMapping("/courses")
    public String addCourse(@RequestBody CourseRequest request){
            if( null == request.getCourseId() || request.getCourseId().isEmpty() ||
                    null == request.getName() || request.getName().isEmpty())
                 return MessageConstant.ERROR;

            repository.save(new CourseEntity(request.getCourseId(),request.getName(),request.getDescription()));
            return MessageConstant.SUCCESS;
    }

   @PutMapping("/courses/{id}")
    public void update(@RequestBody CourseRequest request){


   }

}
