package com.andriikravchenkoo.carsaleproject.model.entity;

import com.andriikravchenkoo.carsaleproject.model.enums.FormatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    private Long id;

    private String name;

    private FormatType formatType;

    private Byte[] data;

    public Image(String name, FormatType formatType, Byte[] data) {
        this.name = name;
        this.formatType = formatType;
        this.data = data;
    }
}
