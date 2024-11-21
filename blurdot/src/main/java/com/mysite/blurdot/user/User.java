package com.mysite.blurdot.user;

import java.time.LocalDateTime;
import java.util.List;

import com.mysite.blurdot.file.File;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30, nullable = false)
    private String userEmail;

    @Column(length = 8, nullable = false)
    private String userName;

    @Column(length = 60, nullable = false)
    private String userPassword;
    
    @Column(length = 11, nullable = false)
    private String userPhonenumber;
    
    @Column(nullable = false, columnDefinition = "TINYINT")
    private Byte userCategory;
    
    @Column(nullable = false, columnDefinition = "TINYINT")
    private Short userAge;
    
    @Column(nullable = false, columnDefinition = "TINYINT")
    private Byte userGender;
    
    @Column(nullable = false, columnDefinition = "TINYINT")
    private Byte userLv;
    
    @Column(nullable = false)
    private LocalDateTime userDate;
    
    @Column(nullable = false, columnDefinition = "SMALLINT")
    private Short user_storage_size;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE) 
    private List<File> fileList;
    
    @Builder
    public User(Integer id,String userName, String userPassword, String userEmail, String userPhonenumber
    		, Byte userCategory, Byte userGender, Short userAge, Byte userLv, LocalDateTime userDate,Short user_storage_size){
    	this.id=id;
        this.userEmail=userEmail;
        this.userName=userName;
        this.userPassword=userPassword;
        this.userPhonenumber=userPhonenumber;
        this.userCategory=userCategory;
        this.userGender=userGender;
        this.userAge=userAge;
        this.userLv=userLv;
        this.user_storage_size=user_storage_size;
        this.userDate = userDate;
    }
}