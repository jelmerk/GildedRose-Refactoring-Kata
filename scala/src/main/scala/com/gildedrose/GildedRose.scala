package com.gildedrose

import ProductNames._
import RichItem._

/**
 * Inventory management system of the Gilded Rose.
 *
 * @param items the inventory
 */
class GildedRose(val items: Item*) {

  /**
   * Invoking this method simulates the passing of one day. This will trigger an update of the inventory.
   */
  def updateQuality(): Unit =
    for {
      item <- items
      if !item.isLegendary
    } {
      val change = item.name match {
        case AgedBrie if item.dateHasPassed => 2
        case AgedBrie => 1
        case BackstagePasses if item.sellIn <= 0 => -item.quality
        case BackstagePasses if item.sellIn < 6 => 3
        case BackstagePasses if item.sellIn < 11 => 2
        case BackstagePasses => 1
        case _ if item.dateHasPassed => -2
        case _ => -1
      }

      val multiplier = if (item.name == ConjuredManaCake) 2 else 1

      item.updateQuality(change * multiplier)
      item.decreaseSellIn()
    }

}