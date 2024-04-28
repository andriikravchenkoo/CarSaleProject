package com.andriikravchenkoo.carsaleproject.dto;

import com.andriikravchenkoo.carsaleproject.model.entity.Image;
import com.andriikravchenkoo.carsaleproject.model.enums.Region;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DealershipPageDto {

    private Long id;

    private String name;

    private Region region;

    private String address;

    private String phoneNumber;

    private String description;

    private List<Image> images;

    private Long countVehicles;

    public DealershipPageDto(
            String name,
            Region region,
            String address,
            String phoneNumber,
            String description,
            List<Image> images,
            Long countVehicles) {
        this.name = name;
        this.region = region;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.images = images;
        this.countVehicles = countVehicles;
    }
}
