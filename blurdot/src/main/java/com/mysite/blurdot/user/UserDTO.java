package com.mysite.blurdot.user;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

// Getters and Setters
@Getter
@Setter
@ToString
public class UserDTO {
    private String userEmail;
    private String userPassword;
    private String userName;
    private Integer userCategory;
    private String userPhonenumber;
    private Short userAge;
    private Byte userGender;
}

