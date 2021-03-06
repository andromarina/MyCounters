package model;

/**
 * Created by mara on 3/29/15.
 */
public class TariffRange implements Comparable<TariffRange> {
    private Integer min;
    private Integer max;
    private double price;
    private int id;

    public TariffRange(int id, int min, int max, double price) {
        this.id = id;
        this.min = min;
        this.max = max;
        this.price = price;
    }

    public TariffRange(int min, int max, double price) {
        this.min = min;
        this.max = max;
        this.price = price;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public double getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(TariffRange another) {
        return this.min.compareTo(another.getMin());
    }
}
