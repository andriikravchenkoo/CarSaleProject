package com.andriikravchenkoo.carsaleproject.dto;

import com.andriikravchenkoo.carsaleproject.model.entity.User;
import com.andriikravchenkoo.carsaleproject.model.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDto {

    @NotBlank(message = "Firstname is required")
    private String firstname;

    @NotBlank(message = "Lastname is required")
    private String lastname;

    @Email(
            regexp =
                    "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            message = "Email not valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Pattern(regexp = "^(\\+38)?(\\d{10}|\\d{9})$", message = "Phone number not valid")
    private String phoneNumber;

    private Role role;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .firstname(this.getFirstname())
                .lastname(this.getLastname())
                .email(this.getEmail())
                .password(passwordEncoder.encode(this.getPassword()))
                .phoneNumber(this.getPhoneNumber())
                .role(this.getRole())
                .build();
    }
}
