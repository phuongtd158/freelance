package com.example.santhuongmai.model.request;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    private String username;

    private String email;

    private String password;

    private Set<String> role;

    private String firstname;

    private String lastname;

    private String country;

    private String town;

    private String state;

    private String ward;

    private String address;

    private String phone;
    private Set<Long> roles = new HashSet<>();
    
}
