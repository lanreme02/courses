package com.cognizant.courses.controller;

public class MessageConstant {

   // public static final String ERROR = "Error";
    //public static final String SUCCESS = "Success";


    private String status;

    public MessageConstant(){}

    public MessageConstant(String status){

        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
