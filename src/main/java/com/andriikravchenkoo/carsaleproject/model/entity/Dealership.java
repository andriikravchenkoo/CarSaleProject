package com.andriikravchenkoo.carsaleproject.model.entity;

import com.andriikravchenkoo.carsaleproject.dto.DealershipPageDto;
import com.andriikravchenkoo.carsaleproject.model.enums.Region;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dealership {

    private Long id;

    private String name;

    private Region region;

    private String address;

    private String phoneNumber;

    private String description;

    private List<Image> images;

    private List<User> sellers;

    private List<Vehicle> vehicles;

    public Dealership(
            String name, Region region, String address, String phoneNumber, String description) {
        this.name = name;
        this.region = region;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.description = description;
    }

    public DealershipPageDto toDto(List<Image> images, Boolean isSeller) {
        return DealershipPageDto.builder()
                .id(this.id)
                .name(this.name)
                .region(this.region)
                .address(this.address)
                .phoneNumber(this.phoneNumber)
                .description(this.description)
                .images(images)
                .isSeller(isSeller)
                .build();
    }
}
