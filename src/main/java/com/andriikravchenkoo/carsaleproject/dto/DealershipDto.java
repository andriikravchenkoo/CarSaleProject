package com.andriikravchenkoo.carsaleproject.dto;

import com.andriikravchenkoo.carsaleproject.model.entity.Dealership;
import com.andriikravchenkoo.carsaleproject.model.enums.Region;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealershipDto {

    @NotBlank(message = "Name is required")
    private String name;

    private Region region;

    @NotBlank(message = "Address is required")
    private String address;

    @Pattern(regexp = "^(\\+38)?(\\d{10}|\\d{9})$", message = "Phone number not valid")
    private String phoneNumber;

    @NotBlank(message = "Description is required")
    private String description;

    public Dealership toEntity() {
        return Dealership.builder()
                .name(this.getName())
                .region(this.getRegion())
                .address(this.getAddress())
                .phoneNumber(this.getPhoneNumber())
                .description(this.getDescription())
                .build();
    }
}
