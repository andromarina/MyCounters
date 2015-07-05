package model.filters;

import model.Record;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mara on 3/1/15.
 */
public class TimePeriodFilter implements IFilter {
    private int monthsBefore;

    public TimePeriodFilter(int monthsBefore) {
        this.monthsBefore = monthsBefore;
    }

    @Override
    public boolean match(Record record) {
        Date endDate = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -monthsBefore);
        Date startDate = calendar.getTime();
        Date recordDate = record.getDate();
        if(recordDate.after(startDate) && recordDate.before(endDate)) {
            return true;
        }

        return false;
    }

    public int getMonthsBefore() {
        return this.monthsBefore;
    }
}
