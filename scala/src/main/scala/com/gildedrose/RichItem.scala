package com.gildedrose

object RichItem {

  private val MinQuality: Int = 0
  private val MaxQuality: Int = 50
  private val LegendaryQuality = 80

  implicit class ItemEnrichment(item: Item) {
    def dateHasPassed: Boolean = item.sellIn <= 0

    def isLegendary: Boolean = item.quality == LegendaryQuality

    def updateQuality(delta: Int): Item = {
      item.quality = math.max(MinQuality, math.min(MaxQuality, item.quality + delta))
      item
    }

    def decreaseSellIn(): Item = {
      item.sellIn = item.sellIn - 1
      item
    }

  }
}
