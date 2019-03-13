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

    @GetMapping("/courses/{id}")
    public CourseEntity getAll(@PathVariable String id){
        return repository.findById(id).get();
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
    public String update(@RequestBody CourseRequest request){

       if( null == request.getCourseId() || request.getCourseId().isEmpty() ||
               null == request.getName() || request.getName().isEmpty())
           return MessageConstant.ERROR;


       CourseEntity course = repository.findById(request.getCourseId() ).orElse(null);

       if(null == course)
            return MessageConstant.ERROR;

       course.setName(request.getName());
       course.setDescription(request.getDescription());
       repository.save(course);


       return MessageConstant.SUCCESS;

   }

    @DeleteMapping("/courses/{id}")
    public String update(@PathVariable String id){

        repository.deleteById(id);

        return MessageConstant.SUCCESS;

    }


}
