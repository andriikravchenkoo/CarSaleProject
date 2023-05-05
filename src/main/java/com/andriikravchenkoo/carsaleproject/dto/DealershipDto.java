package com.andriikravchenkoo.carsaleproject.dto;

import com.andriikravchenkoo.carsaleproject.model.enums.Region;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
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
}
