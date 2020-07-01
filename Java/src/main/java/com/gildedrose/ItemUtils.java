package com.gildedrose;

/**
 * Helpers for dealing with items.
 */
class ItemUtils {

    static final int LEGENDARY_ITEM_QUALITY = 80;

    // private constructor to prevent initialization.
    private ItemUtils() {
    }

    /**
     * Returns true when the products sell date has passed.
     *
     * @param item the item
     * @return true when the products sell date has passed
     *
     * @throws IllegalArgumentException when item is null
     */
    static boolean dateHasPassed(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        return item.sellIn <= 0;
    }

    /**
     * Returns true if this is a legendary item.
     *
     * @param item the item
     * @return true if this is a legendary item
     *
     * @throws IllegalArgumentException when item is null
     */
    static boolean isLegendary(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }

        return item.quality == LEGENDARY_ITEM_QUALITY;
    }

}
