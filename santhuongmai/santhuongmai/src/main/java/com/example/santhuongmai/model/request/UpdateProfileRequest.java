package com.example.santhuongmai.model.request;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {

    private String username;

    private String firstname;

    private String lastname;

    private String email;

    private String country;

    private String state;

    private String address;

    private String phone;
    private Set<Long> roles = new HashSet<>();
}
