package com.cognizant.courses.controller;

import com.cognizant.Semaphore.Monitor;
import com.cognizant.courses.model.CourseEntity;
import com.cognizant.courses.model.CoursesRepository;
import com.cognizant.courses.service.CourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@Api(value="/api",description="Customer Profile",produces ="application/json")
@RequestMapping("/api")
public class CourseController {

    @Autowired
    CoursesRepository repository;

    @Autowired
    CourseService courseService;

    ConcurrentMap<String, Monitor> RequestMap = new ConcurrentHashMap<>();

    @GetMapping("/pdf.pdf")
    public void pdf(HttpServletRequest req, HttpServletResponse response) {

        response.addHeader("content-type","application/octet-stream");
        try {

            OutputStream os = response.getOutputStream();

            FileInputStream fis = new FileInputStream("pdf.pdf");
                // Construct a 1K buffer to hold bytes on their way to the socket.
                byte[] buffer = new byte[1024];
                int bytes = 0;

                // Copy requested file into the socket's output stream.
                while ((bytes = fis.read(buffer)) != -1) {
                        os.write(buffer);

                   // os.write(buffer, 0, bytes);
                }

        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return;
    }

    @GetMapping("/courses")
    public Iterable<CourseEntity> getAll(HttpServletResponse response) {
    response.addHeader("Access-Control-Allow-Origin", "*");
        return repository.findAll();
    }

    @GetMapping("/courses/{id}")
    public CourseEntity getCourse(@PathVariable String id){
        return repository.findById(id).get();
    }



    @PostMapping("/courses")
    public MessageConstant addCourse(@RequestBody CourseRequest request){
      Monitor monitor =  RequestMap.get(request.getCourseId());
        synchronized (this){
            if(null == RequestMap.get(request.getCourseId()))
                RequestMap.put(request.getCourseId(), new Monitor());
        }
        RequestMap.get(request.getCourseId()).lock();

        MessageConstant messageConstant;
            if(!courseService.validateBody(request)) {
                messageConstant = new MessageConstant("Error");
                return messageConstant;
            }

            repository.save(new CourseEntity(request.getCourseId(),request.getName(),request.getDescription()));
        RequestMap.get(request.getCourseId()).release();
        messageConstant = new MessageConstant("Success");

            return messageConstant;
    }

   @PutMapping("/courses/{id}")
    public String update(@RequestBody CourseRequest request){

       if( null == request.getCourseId() || request.getCourseId().isEmpty() ||
               null == request.getName() || request.getName().isEmpty())
           return "Error";


       CourseEntity course = repository.findById(request.getCourseId() ).orElse(null);

       if(null == course)
            return "Success";

       course.setName(request.getName());
       course.setDescription(request.getDescription());
       repository.save(course);


       return "Success";

   }

    @DeleteMapping("/courses/{id}")
    public String update(@PathVariable String id){

        repository.deleteById(id);

        return "Success";

    }


}
