package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;

/**
 * Created by mara on 3/9/15.
 */
public class Tariff {
    private Currency currency;
    private int categoryId;
    private ArrayList<TariffRange> ranges = new ArrayList<TariffRange>();

    public Tariff(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void addTariffRange(TariffRange range) {
        this.ranges.add(range);
        Collections.sort(this.ranges);
    }

    public ArrayList<TariffRange> getRanges() {
        return ranges;
    }

    public double calculate(int difference) {
        double sum = 0;
        for(TariffRange range : this.ranges) {
            int threshold = range.getMax() - range.getMin();
            //if difference exceed threshold
            int leftover = difference - threshold;
            if(leftover <= 0) {
                sum += difference * range.getPrice();
            }
            else {
                sum += threshold * range.getPrice();
                difference = leftover;
            }
        }
        return sum;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
