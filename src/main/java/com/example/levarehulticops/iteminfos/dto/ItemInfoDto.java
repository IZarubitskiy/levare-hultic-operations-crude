package com.example.levarehulticops.iteminfos.dto;

import com.example.levarehulticops.iteminfos.entity.ItemType;

/**
 * DTO for catalog entry of new-request items.
 * Contains only essential fields for display.
 */
public record ItemInfoDto(
        /** Unique part number (catalog key) */
        String partNumber,

        /** Description of the item */
        String description,

        /** Type of the item (e.g., Pump, Valve, Seal) */
        ItemType itemType
) {
}
