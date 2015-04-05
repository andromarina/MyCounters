package model;

import java.util.ArrayList;

/**
 * Created by mara on 3/1/15.
 */
public class TimePeriodsUtils  {
    public final static String SIX_MONTHES = "6 months"; // Resources.getSystem().getString(R.string.months);
    public final static String TWELVE_MONTHES = "12 months"; //+ Resources.getSystem().getString(R.string.months);
    public final static String TWENTY_FOUR_MONTHES = "24 months";// + Resources.getSystem().getString(R.string.months);
    public final static String ALL_RECORDS = "All records";//Resources.getSystem().getString(R.string.all_records);

    public static ArrayList<String> getAllTimePeriods() {
        ArrayList<String> result = new ArrayList<String>();
        result.add(SIX_MONTHES);
        result.add(TWELVE_MONTHES);
        result.add(TWENTY_FOUR_MONTHES);
        result.add(ALL_RECORDS);
        return result;
    }

}
