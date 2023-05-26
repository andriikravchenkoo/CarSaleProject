package com.andriikravchenkoo.carsaleproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Favorites {

    private Long id;

    private Long userId;

    private Long announcementId;

    public Favorites(Long userId, Long announcementId) {
        this.userId = userId;
        this.announcementId = announcementId;
    }
}
