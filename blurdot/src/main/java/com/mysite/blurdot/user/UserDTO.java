package com.mysite.blurdot.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// Getters and Setters
@Getter
@Setter
@ToString
public class UserDTO {

    @NotEmpty(message="* 이메일은 필수항목입니다.")
    @Size(min=6, max=30, message="* 6~30자로 입력해주세요!")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "* Email 형식이 아닙니다.")
    private String userEmail;

    @NotEmpty(message="* 비밀번호는 필수항목입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,16}$", message = "* 비밀번호는 8~16자의 영문 대소문자, 숫자로 이루어져야 합니다.")
    private String userPassword1;

    @NotEmpty(message="* 비밀번호 확인은 필수항목입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,16}$", message = "* 비밀번호 확인은 8~16자의 영문 대소문자, 숫자로 이루어져야 합니다.")
    private String userPassword2;

    @NotEmpty(message="* 닉네임은 필수항목입니다.")
    @Size(min=2, max=30, message="* 2~30자 이내로 입력해주세요!")
    private String userName;
    
    @Min(value = 1, message="* 주로 작업하는 영상 카테고리를 정해주세요.")
    private Integer categoryId;

    @NotEmpty(message="* 휴대폰번호는 필수항목입니다.")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "* 휴대폰 번호 양식에 맞지 않습니다.")
    private String userPhonenumber;

    @NotNull(message="* 나이는 필수항목입니다.")
    @Size(min=8, max=8, message = "* 입력 값은 YYYYMMDD 형식이어야 합니다.")
    private String userAge;  // Changed to String or LocalDate depending on your preference

    private Integer userGender;
    private Integer userStorageSize;
    private Integer isVerified;
}
