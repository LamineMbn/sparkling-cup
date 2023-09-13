package com.sparklingcup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SparklingCupTest {

    @Nested
    class BasicItem {
        @Test
        void should_sell_in_decreases() {
            Item[] items = new Item[] { new Item("+5 Dexterity Vest", 10, 20) };
            SparklingCup sut = new SparklingCup(items);
            sut.updateQuality();
            assertThat(sut.getItems()).extracting("sellIn").containsExactly(9);
        }

        @Test
        void should_quality_decreases() {
            Item[] items = new Item[] { new Item("+5 Dexterity Vest", 10, 20) };
            SparklingCup sut = new SparklingCup(items);
            sut.updateQuality();
            assertThat(sut.getItems()).extracting("quality").containsExactly(19);
        }

        @Test
        void should_not_handle_negative_quality() {
            Item[] items = new Item[] { new Item("+5 Dexterity Vest", 10, -2) };
            SparklingCup sut = new SparklingCup(items);
            sut.updateQuality();
            assertThat(sut.getItems()).extracting("quality").containsExactly(-2);
        }

        @Test
        void should_quality_decreases_twice_faster_after_the_sell_in_date() {
            Item[] items = new Item[] { new Item("+5 Dexterity Vest", -1, 20) };
            SparklingCup sut = new SparklingCup(items);
            sut.updateQuality();
            assertThat(sut.getItems()).extracting("quality").containsExactly(18);
        }
    }

    @Nested
    class AgedBrie {
        @Test
        void should_sell_in_decreases() {
            Item[] items = new Item[] { new Item("Aged Brie", 2, 0) };
            SparklingCup sut = new SparklingCup(items);
            sut.updateQuality();
            assertThat(sut.getItems()).extracting("sellIn").containsExactly(1);
        }

        @Test
        void should_quality_increases() {
            Item[] items = new Item[] { new Item("Aged Brie", 2, 0) };
            SparklingCup sut = new SparklingCup(items);
            sut.updateQuality();
            assertThat(sut.getItems()).extracting("quality").containsExactly(1);
        }

        @Test
        void should_quality_keeps_increasing_after_sell_in_date() {
            Item[] items = new Item[] { new Item("Aged Brie", -1, 2) };
            SparklingCup sut = new SparklingCup(items);
            sut.updateQuality();
            assertThat(sut.getItems()).extracting("quality").containsExactly(4);
        }

        @Test
        void should_quality_never_increases_over_fifty() {
            Item[] items = new Item[] { new Item("Aged Brie", -1, 50) };
            SparklingCup sut = new SparklingCup(items);
            sut.updateQuality();
            assertThat(sut.getItems()).extracting("quality").containsExactly(50);
        }
    }

    @Nested
    class Backstage {

        @Test
        void should_sell_in_decreases() {
            Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
                    new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
                    new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49) };
            SparklingCup sut = new SparklingCup(items);
            sut.updateQuality();
            assertThat(sut.getItems()).extracting("sellIn").containsExactly(14, 9, 4);
        }

        @Test
        void should_quality_increases() {
            Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
                    new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
                    new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49) };
            SparklingCup sut = new SparklingCup(items);
            sut.updateQuality();
            assertThat(sut.getItems()).extracting("quality").containsExactly(21, 50, 50);
        }

        @Test
        void should_quality_increases_by_two_when_we_are_ten_days_or_less_from_the_event() {
            Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20),
                    new Item("Backstage passes to a TAFKAL80ETC concert", 9, 30),
                    new Item("Backstage passes to a TAFKAL80ETC concert", 8, 10) };
            SparklingCup sut = new SparklingCup(items);
            sut.updateQuality();
            assertThat(sut.getItems()).extracting("quality").containsExactly(22, 32, 12);
        }

        @Test
        void should_quality_increases_by_three_when_we_are_five_days_or_less_from_the_event() {
            Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20),
                    new Item("Backstage passes to a TAFKAL80ETC concert", 4, 30),
                    new Item("Backstage passes to a TAFKAL80ETC concert", 1, 10) };
            SparklingCup sut = new SparklingCup(items);
            sut.updateQuality();
            assertThat(sut.getItems()).extracting("quality").containsExactly(23, 33, 13);
        }

        @Test
        void should_quality_drop_to_zero_after_the_event() {
            Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", -1, 20), };
            SparklingCup sut = new SparklingCup(items);
            sut.updateQuality();
            assertThat(sut.getItems()).extracting("quality").containsExactly(0);
        }
    }

    @Nested
    class Sulfuras {

        @Test
        void should_sell_in_never_changes() {
            Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 0, 80),
                    new Item("Sulfuras, Hand of Ragnaros", -1, 80) };
            SparklingCup sut = new SparklingCup(items);
            sut.updateQuality();
            assertThat(sut.getItems()).extracting("sellIn").containsExactly(0, -1);
        }

        @Test
        void should_quality_never_changes() {
            Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 0, 80),
                    new Item("Sulfuras, Hand of Ragnaros", -1, 80) };
            SparklingCup sut = new SparklingCup(items);
            sut.updateQuality();
            assertThat(sut.getItems()).extracting("quality").containsExactly(80, 80);
        }
    }

    @Nested
    class Conjured {
        @Test
        void should_sell_in_decreases() {
            Item[] items = new Item[] { new Item("Conjured Mana Cake", 10, 20) };
            SparklingCup sut = new SparklingCup(items);
            sut.updateQuality();
            assertThat(sut.getItems()).extracting("sellIn").containsExactly(9);
        }

        @Test
        void should_quality_decreases() {
            Item[] items = new Item[] { new Item("Conjured Mana Cake", 10, 20) };
            SparklingCup sut = new SparklingCup(items);
            sut.updateQuality();
            assertThat(sut.getItems()).extracting("quality").containsExactly(18);
        }

        @Test
        void should_not_handle_negative_quality() {
            Item[] items = new Item[] { new Item("Conjured Mana Cake", 10, -2) };
            SparklingCup sut = new SparklingCup(items);
            sut.updateQuality();
            assertThat(sut.getItems()).extracting("quality").containsExactly(-2);
        }

        @Test
        void should_quality_decreases_twice_faster_after_the_sell_in_date() {
            Item[] items = new Item[] { new Item("Conjured Mana Cake", -1, 20) };
            SparklingCup sut = new SparklingCup(items);
            sut.updateQuality();
            assertThat(sut.getItems()).extracting("quality").containsExactly(16);
        }
    }
}
