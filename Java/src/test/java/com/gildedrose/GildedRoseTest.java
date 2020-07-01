package com.gildedrose;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static com.gildedrose.ItemBuilder.*;
import static com.gildedrose.LegendaryItemBuilder.*;
import static com.gildedrose.ItemUtils.LEGENDARY_ITEM_QUALITY;

class GildedRoseTest {

    @Test
    void cannotInitializeGildedRoseWithNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new GildedRose((Item[]) null));
    }

    @Test
    void sulfurasIsNotChanged() {
        Item sulfuras = sulfurasBuilder()
                .build();

        GildedRose app = new GildedRose(sulfuras);
        app.updateQuality();

        assertThat(sulfuras.quality, is(LEGENDARY_ITEM_QUALITY));
        assertThat(sulfuras.sellIn, is(LEGENDARY_ITEM_SELL_IN));
    }

    @Test
    void agedBrieQualityIncreasesTheOlderItGets() {
        Item agedBrie = agedBrieBuilder()
                .sellIn(10)
                .quality(25)
                .build();

        GildedRose app = new GildedRose(agedBrie);
        app.updateQuality();

        assertThat(agedBrie.sellIn, is(9));
        assertThat(agedBrie.quality, is(26));
    }

    @Test
    void agedBrieIncreasesBy2QualityWhenDateHasPassed() {
        Item agedBrie = agedBrieBuilder()
                .sellIn(0)
                .quality(20)
                .build();

        GildedRose app = new GildedRose(agedBrie);
        app.updateQuality();

        assertThat(agedBrie.quality, is(22));
    }

    @Test
    void normalItemsDegradeTwiceAsFastWhenDateHasPassed() {
        Item commonItem = normalItemBuilder("foo")
                .sellIn(0)
                .quality(12)
                .build();

        GildedRose app = new GildedRose(commonItem);
        app.updateQuality();

        assertThat(commonItem.quality, is(10));
        assertThat(commonItem.sellIn, is(-1));
    }

    @Test
    void normalItemsSellInAndQualityIsLowered() {
        Item commonItem = normalItemBuilder("bar")
                .sellIn(10)
                .quality(15)
                .build();

        GildedRose app = new GildedRose(commonItem);
        app.updateQuality();

        assertThat(commonItem.quality, is(14));
        assertThat(commonItem.sellIn, is(9));
    }

    @Test
    void backstagePassesIncreaseInQuality() {
        Item backstagePasses = backstagePassesBuilder()
                .quality(5)
                .sellIn(11)
                .build();

        GildedRose app = new GildedRose(backstagePasses);
        app.updateQuality();

        assertThat(backstagePasses.quality, is(6));
        assertThat(backstagePasses.sellIn, is(10));
    }

    @Test
    void backstagePassesIncreaseBy2QualityWhenThereAre10DaysOrLess() {
        Item backstagePasses = backstagePassesBuilder()
                .quality(20)
                .sellIn(10)
                .build();

        GildedRose app = new GildedRose(backstagePasses);
        app.updateQuality();

        assertThat(backstagePasses.quality, is(22));
    }

    @Test
    void backstagePassesIncreaseBy3QualityWhenThereAre5DaysOrLess() {
        Item backstagePasses = backstagePassesBuilder()
                .quality(20)
                .sellIn(5)
                .build();

        GildedRose app = new GildedRose(backstagePasses);
        app.updateQuality();

        assertThat(backstagePasses.quality, is(23));
    }

    @Test
    void backstagePassesQualityDropsTo0AfterConcert() {
        Item backstagePasses = backstagePassesBuilder()
                .quality(20)
                .sellIn(0)
                .build();

        GildedRose app = new GildedRose(backstagePasses);
        app.updateQuality();

        assertThat(backstagePasses.quality, is(0));
    }

    @Test
    void conjuredManaCakeDegradesInQualityTwiceAsFastAsNormalItems() {
        Item conjuredManaCake = conjuredManaCakeBuilder()
                .quality(20)
                .sellIn(10)
                .build();

        GildedRose app = new GildedRose(conjuredManaCake);
        app.updateQuality();

        assertThat(conjuredManaCake.quality, is(18));
        assertThat(conjuredManaCake.sellIn, is(9));
    }

    @Test
    void qualityOfAnItemIsNeverMoreThan50() {
        Item agedBrie = agedBrieBuilder()
                .quality(50)
                .build();

        GildedRose app = new GildedRose(agedBrie);
        app.updateQuality();

        assertThat(agedBrie.quality, is(50));
    }

    @Test
    void qualityOfAnItemIsNeverNegative() {
        Item commonItem = normalItemBuilder("baz")
                .quality(0)
                .build();

        GildedRose app = new GildedRose(commonItem);
        app.updateQuality();

        assertThat(commonItem.quality, is(0));
    }

}
