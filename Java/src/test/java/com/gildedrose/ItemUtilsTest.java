package com.gildedrose;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static com.gildedrose.ItemBuilder.*;

class ItemUtilsTest {

    @Test
    void datePassedForNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> ItemUtils.dateHasPassed(null));
    }

    @Test
    void datePassedWhenSellInZero() {
        Item item = agedBrieBuilder()
                .sellIn(0)
                .build();

        assertThat(ItemUtils.dateHasPassed(item), is(true));
    }

    @Test
    void datePassedWhenSellInNegative() {
        Item item = agedBrieBuilder()
                .sellIn(-1)
                .build();

        assertThat(ItemUtils.dateHasPassed(item), is(true));
    }

    @Test
    void dateNotPassedWhenSellInPositive() {
        Item item = agedBrieBuilder()
                .sellIn(1)
                .build();

        assertThat(ItemUtils.dateHasPassed(item), is(false));
    }

    @Test
    void isLegendaryForLegendaryItem() {
        Item item = sulfurasBuilder()
                .build();

        assertThat(ItemUtils.isLegendary(item), is(true));
    }

    @Test
    void isLegendaryForNormalItem() {
        Item item = agedBrieBuilder()
                .build();

        assertThat(ItemUtils.isLegendary(item), is(false));
    }

    @Test
    void isLegendaryForNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> ItemUtils.isLegendary(null));
    }

}
