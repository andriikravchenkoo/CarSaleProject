package com.andriikravchenkoo.carsaleproject.model.entity;

import com.andriikravchenkoo.carsaleproject.dto.UserProfilePageDto;
import com.andriikravchenkoo.carsaleproject.model.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private String phoneNumber;

    private Role role;

    private Image image;

    private Dealership dealership;

    private List<Announcement> announcements;

    public User(
            String firstname,
            String lastname,
            String email,
            String password,
            String phoneNumber,
            Role role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserProfilePageDto toDto() {
        return UserProfilePageDto.builder()
                .id(this.id)
                .firstname(this.firstname)
                .lastname(this.lastname)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .role(this.role)
                .image(this.image)
                .build();
    }
}
