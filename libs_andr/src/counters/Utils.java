package counters;

import model.Record;

import java.util.*;

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

    //merge records by month (take only 1 record for the same month).
    public static ArrayList<Record> groupRecords(ArrayList<Record> input) {
        ArrayList<Record> result = new ArrayList<Record>();
        if(input.size() == 0) {
            return result;
        }
        Date date = input.get(0).getDate();
        int month = getValueFromCalendar(date, Calendar.MONTH);
        int year = getValueFromCalendar(date, Calendar.YEAR);
        result.add(input.get(0));
        for(int i = 1; i < input.size(); ++i) {
            Record record = input.get(i);
            int newMonth = getValueFromCalendar(record.getDate(), Calendar.MONTH);
            int newYear = getValueFromCalendar(record.getDate(), Calendar.YEAR);
            if(newMonth != month) {
                month = newMonth;
                result.add(record);
            }
        }
        return result;
    }

    public static List<String> getAvailableCurrencies() {
        Locale[] locales = Locale.getAvailableLocales();

        //
        // We use TreeMap so that the order of the data in the map sorted
        // based on the country name.
        //
        ArrayList< String> currencies = new ArrayList<String>();
        for (Locale locale : locales) {
            try {
                currencies.add(
                        Currency.getInstance(locale).getCurrencyCode());
            } catch (Exception e) {
                // when the locale is not supported
            }
        }
        return currencies;
    }


    public static int getValueFromCalendar(Date date, int calendarField) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(calendarField);
        return month;
    }

    public static int compareDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) {
            return 0 ;
        }
        else if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR) ||
                (cal1.get(Calendar.MONTH) == cal2.get(Calendar.YEAR) &&
                        cal1.get(Calendar.DAY_OF_MONTH) < cal2.get(Calendar.MONTH)) ||
                (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                        cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                        cal1.get(Calendar.DAY_OF_MONTH) < cal2.get(Calendar.DAY_OF_MONTH))) {
            return -1 ;
        }
        else {
            return 1 ;
        }
    }
}
