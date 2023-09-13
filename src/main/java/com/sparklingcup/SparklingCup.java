package com.sparklingcup;

import java.util.Arrays;
import java.util.function.Predicate;

public class SparklingCup {
    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASSES_TO_TAFKAL80ETC_CONCERT = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";
    private final Item[] items;

    public SparklingCup(Item[] items) {
        this.items = items;
    }

    public Item[] getItems() {
        return items;
    }

    public void updateQuality() {
        Arrays.stream(items).filter(excludeSulfuras()).forEach(item -> {
            item.sellIn -= 1;

            if (AGED_BRIE.equals(item.name)) {
                updateAgedBrie(item);
            } else if (BACKSTAGE_PASSES_TO_TAFKAL80ETC_CONCERT.equals(item.name)) {
                updateBackstagePasses(item);
            } else {
                updateBasicItem(item);
            }
        });

    }

    private Predicate<Item> excludeSulfuras() {
        return item -> !SULFURAS_HAND_OF_RAGNAROS.equals(item.name);
    }

    private void updateAgedBrie(Item item) {
        increaseQuality(item);
        if (item.sellIn < 0) {
            increaseQuality(item);
        }
    }

    private void updateBackstagePasses(Item item) {
        increaseQuality(item);
        if (item.sellIn <= 10) {
            increaseQuality(item);
        }

        if (item.sellIn <= 5) {
            increaseQuality(item);
        }
        if (item.sellIn < 0) {
            item.quality = 0;
        }
    }

    private void updateBasicItem(Item item) {
        decreaseQuality(item);
        if (item.sellIn < 0) {
            decreaseQuality(item);
        }
    }

    private void increaseQuality(Item item) {
        if (item.quality < 50) {
            item.quality += 1;
        }
    }

    private void decreaseQuality(Item item) {
        if (item.quality > 0) {
            item.quality -= 1;
        }
    }
}
