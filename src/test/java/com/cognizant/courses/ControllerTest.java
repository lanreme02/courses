package com.cognizant.courses;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    MockMvc mvc;

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

}
