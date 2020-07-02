package com.gildedrose

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers._

import com.gildedrose.ProductNames._
import com.gildedrose.RichItem._

class RichItemTest extends AnyFunSuite {

  test("items of level 80 are considered legendary") {
    val item = new Item(Sulfuras, sellIn = -1, quality = 80)
    item.isLegendary should be (true)
  }

  test("items that are not level 80 are not considered legendary") {
    val item = new Item("foo", sellIn = -1, quality = 10)
    item.isLegendary should be (false)
  }

  test("date has passed when sellIn is zero") {
    val item = new Item("foo", sellIn = 0, quality = 10)
    item.dateHasPassed should be (true)
  }

  test("date has passed when sellIn is negative") {
    val item = new Item("foo", sellIn = -1, quality = 10)
    item.dateHasPassed should be (true)
  }

  test("date has passed when sellIn is positive") {
    val item = new Item("foo", sellIn = 1, quality = 10)
    item.dateHasPassed should be (false)
  }

  test("decreaseSellIn descreases sellIn by 1") {
    val item = new Item("foo", sellIn = 1, quality = 10)
    item.decreaseSellIn()

    item.sellIn should be (0)
  }

  test("updateQuality increases quality by specified delta") {
    val item = new Item("foo", sellIn = 1, quality = 10)
    item.updateQuality(5)

    item.quality should be (15)
  }

  test("updateQuality will never assign a negative quality") {
    val item = new Item("foo", sellIn = 1, quality = 10)
    item.updateQuality(-100)

    item.quality should be (0)
  }

  test("updateQuality will never assign a quality higher than 50") {
    val item = new Item("foo", sellIn = 1, quality = 10)
    item.updateQuality(100)

    item.quality should be (50)
  }

}
