package model.filters;

import model.Record;

import java.util.Calendar;

/**
 * Created by mara on 3/1/15.
 */
public class YearFilter implements IFilter {
    private int year;

    public YearFilter(int year) {
        this.year = year;
    }

    @Override
    public boolean match(Record record) {
        final Calendar c = Calendar.getInstance();
        c.setTime(record.getDate());
        if(c.get(c.YEAR) == year) {
            return true;
        }
        return false;
    }
}
