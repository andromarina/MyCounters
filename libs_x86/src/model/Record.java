package model;


import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mara on 1/2/15.
 */
public class Record implements Comparable<Record> {

    private long id;
    private Date date;
    private int categoryId;
    private int value;
    private int diff = 0;
    private int apartmentId = 1;
    private double price = 0.0;
    private ArrayList<PropertyChangedListener> listeners = new ArrayList<PropertyChangedListener>();

    public Record(long id, Date date, int value, int categoryId) {
        this.id = id;
        this.date = date;
        this.value = value;
        this.categoryId = categoryId;
    }

    public Record(long id, long date, int value, int categoryId) {
        this.id = id;
        this.date = new Date(date);
        this.value = value;
        this.categoryId = categoryId;
    }

    public Record(Date date, int value, int categoryId) {
        this.date = date;
        this.value = value;
        this.categoryId = categoryId;
    }

    public void addUpdateListener(PropertyChangedListener listener) {
        this.listeners.add(listener);
    }

    public long getId() {
        return id;
    }

    public boolean isHead() {
        if(this.diff == 0) {
            return true;
        }
        return false;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public int getValue() {
        return value;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setDiff(int value) {
        this.diff = value;
        notifyAllListeners();
    }

    public void setPrice(double price) {
        this.price = price;
        notifyAllListeners();
    }

    public int getDiff() {
        return this.diff;
    }

    public double getPrice() {
        return price;
    }

    public void setDate(Date recordDate) {
        this.date = recordDate;
        notifyAllListeners();
    }

    public void setValue(int value) {
        this.value = value;
        notifyAllListeners();
    }

    private void notifyAllListeners(){
        for(PropertyChangedListener listener : this.listeners) {
            listener.OnPropertyChanged(this);
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Record id: " + id);
        builder.append(", Value: " + value);
        builder.append(", Date: " + date.toString());
        builder.append(", Category: " + categoryId);
        final String result = builder.toString();
        return result;
    }

    @Override
    public int compareTo(Record another) {
        return another.getDate().compareTo(this.date);
    }

    public Record copyValues(Record input) {
        this.date = input.date;
        this.value = input.value;
        this.categoryId = input.categoryId;
        //this.id = input.id;
        return this;
    }
}
