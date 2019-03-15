package com.cognizant.courses;


import com.cognizant.courses.model.CourseEntity;
import com.cognizant.courses.model.CoursesRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    CoursesRepository repository;

    @Before
    public void clear(){
        repository.deleteAll();
    }

    @Test
    public void nullRequestBodyReturnsErrorMessage() throws Exception{

        String content = mvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content, is("Error"));
    }



    @Test
    public void emptyRequestBodyReturnsErrorMessage() throws Exception{

        String content = mvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\" :  \"\", \"name\" : \"\", \"description\": \"\"}"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content, is("Error"));
    }

    @Test
    public void successMessageReturnedWhenRequestIsValid() throws Exception{

        String content = mvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\" :  \"1\", \"name\" : \"Algorithm\", \"description\": \"Analysis\"}"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(content, is("Success"));
    }

    @Test
    public void getAllReturnsAllCourses() throws Exception{

        CourseEntity  course1 = new CourseEntity("20","data struc","Algorithm analysis");
        repository.save(course1);

        CourseEntity  course2 = new CourseEntity("222","Architecture","software & hardware");
        repository.save(course2);


        String content = mvc.perform(get("/courses").accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();

        List<CourseEntity> returnedCourses = mapper.readValue(content, new TypeReference<List<CourseEntity>>(){});

        assertThat(returnedCourses, containsInAnyOrder(course1,course2));
    }


    @Test
    public void getByIdReturnsCorrect() throws Exception{

        CourseEntity  course1 = new CourseEntity("20","data struc","Algorithm analysis");
        repository.save(course1);


        String content = mvc.perform(get("/courses/" + course1.getCourseId())
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        CourseEntity returnedCourses = mapper.readValue(content, new TypeReference<CourseEntity>(){});
        assertThat(returnedCourses, is(course1));
    }

    @Test
    public void updateWithValidIDReturnSuccess() throws Exception{

        CourseEntity  originalCourse = new CourseEntity("20","data struc","Algorithm analysis");
        repository.save(originalCourse);

        CourseEntity  updatedCourse = new CourseEntity("20","Math","calc 2");

        String content = mvc.perform(put("/courses/" + originalCourse.getCourseId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"id\" :" + "\""+ originalCourse.getCourseId() + "\""
                        + ", \"name\" :" + "\"" + updatedCourse.getName() + "\""
                        + ", \"description\" :" + "\""+  updatedCourse.getDescription() + "\"" +"}"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        CourseEntity retrievedCourse = repository.findById(originalCourse.getCourseId()).orElse(null);

        assertThat(updatedCourse, is(retrievedCourse));
    }


    @Test
    public void deleteWithValidIDReturnSuccess() throws Exception{

        CourseEntity  originalCourse = new CourseEntity("20","data struc","Algorithm analysis");
        repository.save(originalCourse);

        String content = mvc.perform(delete("/courses/" + originalCourse.getCourseId()))
                .andReturn()
                .getResponse()
                .getContentAsString();
        CourseEntity retrievedCourse = repository.findById(originalCourse.getCourseId()).orElse(null);

        assertThat(null, is(retrievedCourse));
    }
}
