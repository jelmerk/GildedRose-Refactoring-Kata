package com.gildedrose;

import java.util.Arrays;
import java.util.List;

import static com.gildedrose.ProductNames.*;
import static com.gildedrose.ItemUtils.dateHasPassed;
import static java.util.function.Predicate.not;

/**
 * Inventory management system of the Gilded Rose.
 */
class GildedRose {

    private static final int MIN_QUALITY = 0;
    private static final int MAX_QUALITY = 50;

    private static final List<QualityChangeStrategy> STRATEGIES = Arrays.asList(
        new AgedBrieStrategy(),
        new BackstagePassesStrategy(),
        new ConjuredManaCakeStrategy(),
        new NormalItemStrategy() // must be in last position
    );

    Item[] items;

    /**
     * Constructs a new GildedRose instance.
     *
     * @param items the inventory
     *
     * @throws IllegalArgumentException when items is null
     */
    public GildedRose(Item... items) {
        if (items == null) {
            throw new IllegalArgumentException("Items cannot be null.");
        }
        this.items = items;
    }

    /**
     * Invoking this method simulates the passing of one day. This will trigger an update of the inventory.
     */
    public void updateQuality() {
        Arrays.stream(items).filter(not(ItemUtils::isLegendary)).forEach(item -> {
            QualityChangeStrategy strategy = STRATEGIES.stream()
                    .filter(s -> s.supports(item.name))
                    .findFirst()
                    .orElseThrow();

            item.quality = Math.max(MIN_QUALITY, Math.min(MAX_QUALITY, item.quality + strategy.computeQualityChange(item)));
            item.sellIn = item.sellIn - 1;
        });
    }

    /**
     * Implementations of this interface encapsulate the logic for updating the quality and sellIn of items.
     */
    interface QualityChangeStrategy {

        /**
         * True if this strategy can process the item with the passed in name.
         *
         * @param name name of the item
         * @return true if this strategy can process the item with the passed in name
         */
        boolean supports(String name);

        /**
         * Computes the change to the quality attribute for the item.
         *
         * @param item the item
         * @return the computed change to the quality attribute of the item.
         */
        int computeQualityChange(Item item);

    }

    /**
     * Implementation of {@link QualityChangeStrategy} for aged brie.
     *
     * Aged Brie increases in Quality the older it gets.
     * Increases quality by 2 after day has passed
     */
    static class AgedBrieStrategy implements QualityChangeStrategy {

        @Override
        public boolean supports(String name) {
            return AGED_BRIE.equals(name);
        }

        @Override
        public int computeQualityChange(Item item) {
            return dateHasPassed(item) ? 2 : 1;
        }

    }

    /**
     * Implementation of {@link QualityChangeStrategy} for backstage passes.
     *
     * Backstage passes", like aged brie, increase in Quality as its SellIn value approaches;
     * 	Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
     * 	Quality drops to 0 after the concert
     */
    static class BackstagePassesStrategy implements QualityChangeStrategy {

        @Override
        public boolean supports(String name) {
            return BACKSTAGE_PASSES.equals(name);
        }

        @Override
        public int computeQualityChange(Item item) {
            int change;

            if (item.sellIn <= 0) {
                change = -item.quality;
            } else if (item.sellIn < 6) {
                change = 3;
            } else if (item.sellIn < 11) {
                change = 2;
            } else {
                change = 1;
            }

            return change;
        }

    }

    /**
     * Implementation of {@link QualityChangeStrategy} for conjured mana cakes.
     *
     * "Conjured" items degrade in Quality twice as fast as normal items
     */
    static class ConjuredManaCakeStrategy implements QualityChangeStrategy {

        private final NormalItemStrategy normalItemStrategy = new NormalItemStrategy();

        @Override
        public boolean supports(String name) {
            return CONJURED_MANA_CAKE.equals(name);
        }

        @Override
        public int computeQualityChange(Item item) {
            return normalItemStrategy.computeQualityChange(item) * 2;
        }

    }

    /**
     * Implementation of {@link QualityChangeStrategy} for normal items.
     *
     * Lowers the quality and sellIn by one one each day.
     * Decreases quality by 2 after day has passed
     */
    static class NormalItemStrategy implements QualityChangeStrategy {

        @Override
        public boolean supports(String name) {
            return true;
        }

        @Override
        public int computeQualityChange(Item item) {
            return dateHasPassed(item) ? -2 : -1;
        }

    }

}