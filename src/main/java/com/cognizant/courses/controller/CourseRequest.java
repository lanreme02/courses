package com.cognizant.courses.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CourseRequest {

    private String courseId;
    private String name;
    private String description;

    public CourseRequest(){}


    @JsonCreator
    public CourseRequest(@JsonProperty("id") String courseId, @JsonProperty("name") String name, @JsonProperty("description") String description){

        this.courseId = courseId;
        this.name = name;
        this.description = description;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
