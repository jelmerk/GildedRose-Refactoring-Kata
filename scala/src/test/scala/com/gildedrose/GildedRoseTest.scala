package com.gildedrose

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import com.gildedrose.ProductNames._

class GildedRoseTest extends AnyFunSuite  with Matchers {

  test("sulfuras is not changed") {
    val item = new Item(Sulfuras, sellIn = -1, quality = 80)

    val app = new GildedRose(item)
    app.updateQuality()

    item.sellIn should be (-1)
    item.quality should be (80)
  }

  test("aged brie quality increases the older it gets") {
    val item = new Item(AgedBrie, sellIn = 10, quality = 25)

    val app = new GildedRose(item)
    app.updateQuality()

    item.sellIn should be (9)
    item.quality should be (26)
  }

  test("aged brie quality increases 2 quality when date has passed") {
    val item = new Item(AgedBrie, sellIn = 0, quality = 20)

    val app = new GildedRose(item)
    app.updateQuality()

    item.quality should be (22)
  }

  test("normal items's sellIn and quality is lowered") {
    val item = new Item("normal", sellIn = 10, quality = 15)

    val app = new GildedRose(item)
    app.updateQuality()

    item.sellIn should be (9)
    item.quality should be (14)
  }

  test("normal items degrade twice as fast when date has passed") {
    val item = new Item("normal", sellIn = 0, quality = 12)

    val app = new GildedRose(item)
    app.updateQuality()

    item.sellIn should be (-1)
    item.quality should be (10)
  }

  test("backstage passes increase in quality") {
    val item = new Item(BackstagePasses, sellIn = 11, quality = 5)

    val app = new GildedRose(item)
    app.updateQuality()

    item.sellIn should be (10)
    item.quality should be (6)
  }

  test("backstage passes increase by 2 quality when there are 10 days or less till the event") {
    val item = new Item(BackstagePasses, sellIn = 10, quality = 20)

    val app = new GildedRose(item)
    app.updateQuality()

    item.quality should be (22)
  }

  test("backstage passes increase by 3 quality when there are 5 days or less till the event") {
    val item = new Item(BackstagePasses, sellIn = 5, quality = 20)

    val app = new GildedRose(item)
    app.updateQuality()

    item.quality should be (23)
  }

  test("backstage passes quality drops to zero after concert") {
    val item = new Item(BackstagePasses, sellIn = 0, quality = 20)

    val app = new GildedRose(item)
    app.updateQuality()

    item.quality should be (0)
  }

  test("quality of an item is never more than 50") {
    val item = new Item(AgedBrie, sellIn = 10, quality = 50)

    val app = new GildedRose(item)
    app.updateQuality()

    item.quality should be (50)
  }

  test("quality of an item is never negative") {
    val item = new Item("foo", sellIn = 10, quality = 0)

    val app = new GildedRose(item)
    app.updateQuality()

    item.quality should be (0)
  }

  test("conjured mana cake degrades in quality twice as fast as normal items") {
    val item = new Item(ConjuredManaCake, sellIn = 10, quality = 20)

    val app = new GildedRose(item)
    app.updateQuality()

    item.quality should be (18)
    item.sellIn should be (9)
  }

}