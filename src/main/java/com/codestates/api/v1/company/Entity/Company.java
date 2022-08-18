package com.codestates.api.v1.company.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;
    @Column(nullable = false, updatable = false, unique = true)
    private String companyRegistrationNumber;
    @Column(length =  100, nullable = false)
    private Long companyType;
    @Column(length = 100, nullable = false)
    private String companyName;
    @Column(length = 100, nullable = false)
    private String companyLocation;



    public Company( Long companyCode, String companyName, String companyLocation) {
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.companyType = companyType;
        this.companyName = companyName;
        this.companyLocation = companyLocation;

    }
}
