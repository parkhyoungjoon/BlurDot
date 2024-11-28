package com.mysite.blurdot.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.mysite.blurdot.file.StorageFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30, nullable = false, unique = true)
    private String userEmail;

    @Column(length = 8, nullable = false, unique = true)
    private String userName;

    @Column(length = 60, nullable = false)
    private String userPassword;
    
    @Column(length = 11, nullable = false)
    private String userPhonenumber;
    
    @Column(nullable = false)
    private LocalDate userAge;
    
    @Column(nullable = false, columnDefinition = "TINYINT")
    private Integer userGender;
    
    @Column(nullable = false)
    private LocalDateTime userDate;
    
    @Column(nullable = false, columnDefinition = "MEDIUMINT")
    private Integer user_storage_size;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE) 
    private List<StorageFile> fileList;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    @Enumerated(EnumType.STRING)  // enum 값을 문자열로 저장
    private UserRole role;  // 권한 필드 추가
    
    private Boolean isVerified;
    
    @Builder
    public SiteUser(Integer id,String userName, String userPassword, String userEmail, String userPhonenumber
    		, Category category_id, Integer userGender, LocalDate userAge, LocalDateTime userDate, Boolean isVerified,
    		Integer user_storage_size){
    	this.id=id;
        this.userEmail=userEmail;
        this.userName=userName;
        this.userPassword=userPassword;
        this.userPhonenumber=userPhonenumber;
        this.userGender=userGender;
        this.userAge=userAge;
        this.user_storage_size=user_storage_size;
        this.userDate = userDate;
        this.isVerified = isVerified;
    }
}