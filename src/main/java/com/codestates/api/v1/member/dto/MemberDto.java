package com.codestates.api.v1.member.dto;

import com.codestates.api.v1.member.entity.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class MemberDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        @NotBlank
        private String userId;
        @NotBlank
        private String name;
        // (남성: m , 여성: f 두 문자만 허용)
        @NotBlank
        private char gender;
        @NotBlank
        private String companyName;
        // (사업자번호 형식으로 유효성 검사)
        // 10자리 000-00-00000
        @NotBlank
        private String companyRegistrationNumber;
        @NotBlank
        private int companyTypeId;
        @NotBlank
        private int companyLocationId;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        @Setter
        private Long memberId;
        private String userId;
        private String name;
        private char gender;
        private String companyName;
        private String companyRegistrationNumber;
        private int companyTypeId;
        private int companyLocationId;
        private MemberStatus memberStatus;

    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long memberId;
        private String userId;
        private String name;
        private char gender;
        private String companyName;
        private String companyRegistrationNumber;
        private int companyTypeId;
        private int companyLocationId;
        private MemberStatus memberStatus;
    }


}
