package com.andriikravchenkoo.carsaleproject.facade.filter;

import lombok.Builder;
import lombok.Value;

/**
 * Filter for searching announcements in a unified way.
 *
 * This DTO allows the facade/service layer to accept one object
 * instead of multiple, highly-specific method signatures.
 */
@Value
@Builder
public class AnnouncementFilter {

    /**
     * If present, return announcements created by this user.
     */
    Long userId;

    /**
     * If present, return announcements belonging to this dealership.
     */
    Long dealershipId;

    /**
     * If present, filter by vehicle usage (new/used).
     */
    Boolean isUsed;

    /**
     * If present, return announcements that are in favorites for this user.
     */
    Long favoritesForUserId;

    /**
     * Optional free-text query (e.g., brand/model).
     */
    String query;

    // Advanced filters (reserved for future DAO support)
    String brand;
    String model;
    Integer minYear;
    Integer maxYear;
    Integer minPrice;
    Integer maxPrice;

    /**
     * Sorting option (defaults can be set at the call site if null).
     */
    Sort sort;

    public boolean hasAnyCriteria() {
        return userId != null
                || dealershipId != null
                || isUsed != null
                || favoritesForUserId != null
                || (query != null && !query.isBlank())
                || (brand != null && !brand.isBlank())
                || (model != null && !model.isBlank())
                || minYear != null
                || maxYear != null
                || minPrice != null
                || maxPrice != null;
    }

    public enum Sort {
        DATE_DESC,
        DATE_ASC,
        PRICE_DESC,
        PRICE_ASC
    }
}


