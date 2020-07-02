package com.gildedrose

/**
 * Extends the item class with additional functionality.
 */
object RichItem {

  private val MinQuality = 0
  private val MaxQuality = 50
  private val LegendaryQuality = 80

  implicit class ItemEnrichment(item: Item) {

    /**
     * Returns true when the sellIn date has passed.
     *
     * @return true when the sellIn date has passed
     */
    def dateHasPassed: Boolean = item.sellIn <= 0

    /**
     * Returns true when this item is a legendary item.
     *
     * @return true when this is a legendary item
     */
    def isLegendary: Boolean = item.quality == LegendaryQuality

    /**
     * Updates the quality of an item. Quality will never decrease below 0 and will never increase beyond 50
     *
     * @param delta the change, can be either negative of positive
     * @return the item
     */
    def updateQuality(delta: Int): Item = {
      item.quality = math.max(MinQuality, math.min(MaxQuality, item.quality + delta))
      item
    }

    /**
     * Decreases the sellIn of an item by one.
     *
     * @return the item
     */
    def decreaseSellIn(): Item = {
      item.sellIn = item.sellIn - 1
      item
    }

  }
}
