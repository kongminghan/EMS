package com.example.minghan.ems;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MingHan on 6/5/2016.
 */
public class User {
    public String name;
    public String emp_no;
    public int imageId;
    public String email;
    public String gender;
    public String  ic;
    public String username;
    public String date;
    public ArrayList<User> user;


    public User(String name, String emp_no,String email,String gender, String ic, String username, String date, int imageId){
        this.name = name;
        this.imageId = imageId;
        this.emp_no = emp_no;
        this.email = email;
        this.gender = gender;
        this.ic = ic;
        this.username = username;
        this.date = date;
    }
    public User(){
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setImage(int imageId){
        this.imageId = imageId;
    }

    public void setEmp(String emp_no){
        this.emp_no = emp_no;
    }

}
