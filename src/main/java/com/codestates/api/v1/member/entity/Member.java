package com.codestates.api.v1.member.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, updatable = false, unique = true)
    private String userId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 1, nullable = false, unique = true)
    private char gender;

    @Column(length = 100, nullable = false)
    private String companyName;

    @Column(length = 10, nullable = false)
    private String companyRegistrationNumber;

    @Column(nullable = false)
    private int companyTypeId;

    @Column(nullable = false)
    private int companyLocationId;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

}
