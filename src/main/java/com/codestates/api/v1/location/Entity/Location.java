package com.codestates.api.v1.location.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;
    @Column(length = 100, nullable = false)
    private String locationName;
    @Column(nullable = false, updatable = false, unique = true)
    private Long locationNameId;

    public Location(long locationCode, String locationName) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.locationNameId = locationNameId;
    }
}
