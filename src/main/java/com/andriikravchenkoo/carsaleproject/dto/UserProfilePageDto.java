package com.andriikravchenkoo.carsaleproject.dto;

import com.andriikravchenkoo.carsaleproject.model.entity.Image;
import com.andriikravchenkoo.carsaleproject.model.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfilePageDto {

    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private String phoneNumber;

    private Role role;

    private Image image;

    public UserProfilePageDto(
            String firstname,
            String lastname,
            String email,
            String phoneNumber,
            Role role,
            Image image) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.image = image;
    }
}
