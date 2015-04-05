package counters;

import model.Record;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mara on 2/18/15.
 */
public class Utils {

    public static ArrayList<Integer> getYears() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(c.YEAR);
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(int i = year - 1; i < year + 5; ++i) {
            result.add(i);
        }
        return result;
    }


    public static ArrayList<Record> removeRecordsForTheSameMonth(ArrayList<Record> input) {
        ArrayList<Record> result = new ArrayList<Record>();
        if(input.size() == 0) {
            return result;
        }
        Date date = input.get(0).getDate();
        int month = getMonth(date);
        result.add(input.get(0));
        for(Record record : input) {
            int newMonth = getMonth(record.getDate());
            if(newMonth != month) {
                month = newMonth;
                result.add(record);
            }
        }
        return result;
    }

    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return month;
    }
}
