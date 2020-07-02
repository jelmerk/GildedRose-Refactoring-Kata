package com.gildedrose

import ProductNames._
import RichItem._

class GildedRose(val items: Item*) {

  def updateQuality(): Unit =
    for {
      item <- items
      if !item.isLegendary
    } {
      val qualityChange = computeQualityChange(item)

      item.updateQuality(qualityChange)
      item.decreaseSellIn()
    }

  private def computeQualityChange(item: Item) = {
    val normalItemMultiplier = if (item.name == ConjuredManaCake) 2 else 1

    item.name match {
      case AgedBrie if item.dateHasPassed => 2
      case AgedBrie => 1
      case BackstagePasses if item.sellIn <= 0 => -item.quality
      case BackstagePasses if item.sellIn < 6 => 3
      case BackstagePasses if item.sellIn < 11 => 2
      case BackstagePasses => 1
      case _ if item.dateHasPassed => -2 * normalItemMultiplier
      case _ => -1 * normalItemMultiplier
    }
  }


}