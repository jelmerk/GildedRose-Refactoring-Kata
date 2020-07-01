package com.gildedrose;

import static com.gildedrose.ItemUtils.LEGENDARY_ITEM_QUALITY;
import static com.gildedrose.ProductNames.*;

abstract class ItemBuilderBase {

    String name;
    int sellIn;
    int quality;

    Item build() {
        return new Item(name, sellIn, quality);
    }
}

class LegendaryItemBuilder extends ItemBuilderBase {

    static final int LEGENDARY_ITEM_SELL_IN = -1;

    LegendaryItemBuilder(String name) {
        this.name = name;
        this.quality = LEGENDARY_ITEM_QUALITY;
        this.sellIn = -1;
    }

}

class SimpleItemBuilder extends ItemBuilderBase {

    SimpleItemBuilder(String name) {
        this.name = name;
    }

    SimpleItemBuilder sellIn(int sellIn) {
        this.sellIn = sellIn;
        return this;
    }

    SimpleItemBuilder quality(int quality) {
        this.quality = quality;
        return this;
    }
}

class ItemBuilder {

    static SimpleItemBuilder normalItemBuilder(String name) {
        return new SimpleItemBuilder(name);
    }

    static LegendaryItemBuilder sulfurasBuilder() {
        return new LegendaryItemBuilder(SULFURAS);
    }

    static SimpleItemBuilder agedBrieBuilder() {
        return new SimpleItemBuilder(AGED_BRIE);
    }

    static SimpleItemBuilder backstagePassesBuilder() {
        return new SimpleItemBuilder(BACKSTAGE_PASSES);
    }

    static SimpleItemBuilder conjuredManaCakeBuilder() {
        return new SimpleItemBuilder(CONJURED_MANA_CAKE);
    }
}
